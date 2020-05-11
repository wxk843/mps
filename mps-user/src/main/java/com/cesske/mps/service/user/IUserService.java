
package com.cesske.mps.service.user;

//import com.cesske.mps.bean.RecommendRelationBean;
//import com.cesske.mps.client.ScrmResponse;
import com.cesske.mps.model.ServiceResponse;
//import com.cesske.mps.model.bean.sysauth.UserFilterBean;
//import com.cesske.mps.model.bean.user.UserProfileUpdateBean;
import com.cesske.mps.model.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * User 业务接口类
 */
public interface IUserService {

    List<User> findAll();

    User findOne(User user);

    User findOne(Long uid);

    User findOneByMobile(String mobile);

    User saveOrUpdate(User user);

    void delete(Long r_id);

    ServiceResponse getOpenId(String traceId, String wmId, String code, String option);

    List<User> saveAll(List<User> userList);

}
