package com.cesske.mps.controller.email;

import com.alibaba.fastjson.JSON;
import com.cesske.mps.constants.CommonConst;
import com.cesske.mps.model.ServiceResponse;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <发送邮件接口>
 */
@Slf4j
@RestController
@Api(value = "发送邮件", description = "发送邮件")
@RequestMapping(value = CommonConst.API_PATH_VERSION_1+"/")
public class EmailController {
    @ApiOperation(value = "接收消息并发送邮件", notes = "接收消息并发送邮件", httpMethod = "POST")
    @RequestMapping(value = "/message/email", method = RequestMethod.POST)
    public ServiceResponse sendEmail(@Validated @RequestBody String emailMsg){
        //字符串转对象
//        Cmq cmq = JSON.parseObject(emailMsg, Cmq.class);
//        String msgBody = cmq.getMsgBody();
//        //字符串转对象
//        Email email = JSON.parseObject(msgBody, Email.class);
//
//        mailUtil.sendSysMail(email.getMailSubject(),email.getMailContent());

        return ServiceResponse.createSuccessResponse("","发送成功");
    }

    @ApiOperation(value = "发送邮件", notes = "发送邮件", httpMethod = "POST")
    @RequestMapping(value = "/send/email", method = RequestMethod.POST)
    public ServiceResponse sendEmail(
            @RequestParam(value = "mailSubject", required = true) String mailSubject,
            @RequestParam(value = "mailContent", required = true) String mailContent){

//        if(Strings.isNullOrEmpty(mailSubject) && Strings.isNullOrEmpty(mailContent)){
//            return ServiceResponse.createFailResponse("", ResultCodeConst.PROMPT_ERROR,"参数不能为空");
//        }
//
//        return emailService.sendEmail(mailSubject,mailContent,profile);
        return ServiceResponse.createSuccessResponse("","发送成功");

    }
}
