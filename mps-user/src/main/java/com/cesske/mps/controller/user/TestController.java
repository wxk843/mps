package com.cesske.mps.controller.user;

import com.cesske.mps.client.IMsToolClient;
import com.cesske.mps.model.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 测试相关接口
 */
@Api(value = "Test", description = "测试相关接口")
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private IMsToolClient iMsToolClient;

    @ApiOperation(value = "test-sendmail", notes = " (邮件通知)", httpMethod = "POST")
    @RequestMapping(value = "/mail/send", method = RequestMethod.POST)
    public ServiceResponse saveOrUpdateTDe007Tag(
            @RequestParam(value = "traceId", required = false) String traceId) {

        ServiceResponse r = ServiceResponse.createSuccessResponse(traceId,"");

        iMsToolClient.sendEmail("经销商上下线通知 ["+"]修改标签："+"",
                "\n网点类型: "+
                        "\n经销商代码: " +
                        "\n经销商简称: " +
                        "\n>> 上线/修改"+
                        "\n\n --------- \n");

        return r;
    }


}
