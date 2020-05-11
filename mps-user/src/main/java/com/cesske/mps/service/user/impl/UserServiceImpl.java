package com.cesske.mps.service.user.impl;

import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.constants.RegexpConstant;
import com.cesske.mps.constants.ResultCodeConst;
import com.cesske.mps.enmus.ResultMsg;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.model.entity.user.User;
import com.cesske.mps.repository.UserRepository;
import com.cesske.mps.service.user.ILoginService;
import com.cesske.mps.service.user.IUserService;
import com.cesske.mps.utils.CommonUtils;
import com.cesske.mps.utils.RegExpValidatorUtils;
import com.cesske.mps.utils.TraceId;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户相关业务
 * @author
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IMsToolClient msToolClient;
    @Autowired
    private ILoginService loginService;
    @Value("${permission.ischeck}")
    private String ischeck;
    @Autowired
    private TraceId traceId;
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findOne(User user) {
        Preconditions.checkArgument(user != null , "id is null");
        return userRepository.findOne(Example.of(user));
    }

    @Override
    public User findOne(Long uid) {
        Preconditions.checkArgument(uid != null , "id is null");
        return userRepository.findOne(uid);
    }

    @Override
    public User findOneByMobile(String mobile) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(mobile), "mobile is empty");
        return userRepository.findByMobileAndStatusEquals(mobile, 1);
    }


    @Override
    public User saveOrUpdate(User user) {
        Preconditions.checkNotNull(user, "Object is null");
        return userRepository.save(user);
        }

    @Override
    public void delete(Long r_id) {
        Preconditions.checkArgument(r_id!=null || r_id!=0, "r_id is null or empty");
        User user = userRepository.findByRidEquals(r_id);
        if (user != null) {
            userRepository.delete(user);

        }
    }

    @Override
    public ServiceResponse getOpenId(String traceId, String wmId, String code, String option) {
        //User user = findOneByWmId(wmId);
        User user = new User();
        if(user == null) {
            return ServiceResponse.createFailResponse(traceId, ResultMsg.PROMPT_ERROR.getIndex(), "用户不存在");
        }
        if(StringUtils.isNotEmpty(option) && Objects.equal(option, "update")) {
            return getAndSaveOpenId(traceId, code, user);
        } else if(StringUtils.isNotEmpty(user.getOpenid())) {
                // 用户表里已经有 openid，直接返回
                return ServiceResponse.createSuccessResponse(traceId, user.getOpenid());
        }
        return getAndSaveOpenId(traceId, code, user);
    }

    private ServiceResponse getAndSaveOpenId(String traceId, String code, User user) {
        ServiceResponse<String> serviceResponse = msToolClient.wechatOauth(code);
        if(serviceResponse.isSuccess()) {
            // 将获取到的 openid 存入数据库
            user.setOpenid(serviceResponse.getData());
            saveOrUpdate(user);
            return ServiceResponse.createSuccessResponse(traceId, serviceResponse.getData());
        } else {
            return ServiceResponse.createFailResponse(traceId, ResultMsg.PROMPT_ERROR.getIndex(), serviceResponse.getStateDesc());
        }
    }

    @Override
    public List<User> saveAll(List<User> userList) {
        return userRepository.save(userList);
    }

}
