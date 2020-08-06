package com.cesske.mps.controller.user;

//import com.cesske.mps.model.entity.user.User;
import com.alibaba.fastjson.JSON;
import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.constants.CommonConst;
import com.cesske.mps.model.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户相关接口
 */
@Api(value = "用户", description = "用户相关接口")
@RestController
@RequestMapping(value = CommonConst.API_PATH_VERSION_1+"/user")
public class UserController {
    @Autowired
    private IMsToolClient iMsToolClient;

    @ApiOperation(value = "获取用户详情", notes = "根据id获取用户详情", httpMethod = "GET")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findByRid() {
        return "";
    }

    @ApiOperation(value = "用户", notes = "根据id获取用户详情", httpMethod = "GET")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String findAll() {
        return "";
    }

    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    @RequestMapping(value = "/edit", method = RequestMethod.POST,headers = "content-type=multipart/form-data")
    public String edit(@RequestParam("file") MultipartFile file) throws Exception {
        iMsToolClient.sendEmail("dfdfd","333333333333333333");
        ServiceResponse serviceResponse = iMsToolClient.upload(file);
        if (!serviceResponse.isSuccess()) {
            System.out.println(serviceResponse);
//            String logStr = CommonUtils.genLogString(" 上传头像时出错 " + " param " + userOrder.toString() + " return " + serviceResponse.toString() + " json_data " + JSON.toJSONString(serviceResponse), "order_save_conf_error", null);
//            log.error(logStr);
            throw new Exception("上传头像时出错:" + JSON.toJSONString(serviceResponse));
        }
        return "";
    }
}
