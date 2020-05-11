package com.cesske.mps.controller.user;

//import com.cesske.mps.model.entity.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 用户相关接口
 */
@Api(value = "用户", description = "用户相关接口")
@RestController
@RequestMapping("/user")
public class UserController {
    @ApiOperation(value = "获取用户详情", notes = "根据id获取用户详情", httpMethod = "GET")
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String findByRid(  ) {
        return "";
    }
}
