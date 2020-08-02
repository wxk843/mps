package com.cesske.mps.util.email;

import com.cesske.mps.cache.CommonRedisKey;
import com.cesske.mps.cache.RedisUtil;
import com.cesske.mps.constants.MailConst;
import com.cesske.mps.utils.CommonUtils;
import com.google.common.collect.Maps;
import com.lorne.core.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@EnableAsync
public class MailUtil {
    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    RedisUtil<List<String>> redisUtil;

    @Async
    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) throws ServiceException {
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("subject", subject);
        logMap.put("content", content);
        logMap.put("deliver", deliver);
        logMap.put("receiver", receiver);
        logMap.put("carbonCopy", carbonCopy);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info(CommonUtils.genLogString("发送邮件成功","send_email_succeed",logMap));
        } catch (MailException e) {
            logMap.put("error", e);
            log.error(CommonUtils.genLogString("发送邮件出错","send_email_lose",logMap));
            throw new ServiceException(e.getMessage());
        }
    }

    @Async
    public void sendSysMail(String subject,String content){
        Map<String, Object> logMap = Maps.newHashMap();
        logMap.put("subject", subject);
        logMap.put("content", content);
        try {
            List<String> devList = redisUtil.get(String.format("%smail:dev-list", CommonRedisKey.PREFIX));
            List<String> csList = redisUtil.get(String.format("%smail:cs-list", CommonRedisKey.PREFIX));
            logMap.put("devListInRedis", devList);
            logMap.put("csListInRedis", csList);
            content = "运行环境："+profile+"\n\n\n"+content;
            if(devList == null || devList.size() == 0) {
                devList = MailConst.MAIL_DEV_LIST;
            }
            if(csList == null || csList.size() == 0) {
                csList = MailConst.MAIL_CS_LIST;
            }
            log.info(CommonUtils.genLogString("发送邮件","email_send_start", logMap));
            sendSimpleEmail(
                    MailConst.MAIL_DELIVER,
                    devList.toArray(new String[0]),
                    csList.toArray(new String[0]),
                    subject,
                    content);
        } catch (Exception e) {
            logMap.put("error", e);
            log.error(CommonUtils.genLogString("发送邮件失败","email_not_send", logMap));
        }
    }

    /*private String[] transListToArray(List list){
        String[] strings = new String[list.size()];
        return (String[]) list.toArray(strings);
    }*/

}
