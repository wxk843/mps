package com.cesske.mps.service.user;

import com.cesske.mps.bean.*;
import com.cesske.mps.model.ServiceResponse;
import com.cesske.mps.model.entity.user.User;

public interface ILoginService {
    ServiceResponse login(LoginBean loginBean);

    ServiceResponse logout(String superId, String token, String traceId);

    ServiceResponse register(RegisterBean loginBean);

    ServiceResponse checkCode(String traceId, String phone, String code, int type);

    ServiceResponse upPassword(PasswordUpdateBean passwordModifyBean);

    ServiceResponse modifyPassword(PasswordModifyBean passwordModifyBean);

    ServiceResponse checkToken(String token, Long timestamp, String traceId);

    ServiceResponse checkRegisterAndSendCode(RegisterCheckSendCodeBean checkSendCodeBean);
    //    ServiceResponse checkRegister(String traceId, String phone);
    User getUserProfile(String superId, String logFormater);
}
