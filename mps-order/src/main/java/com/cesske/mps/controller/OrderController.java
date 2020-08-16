package com.cesske.mps.controller;

import com.cesske.mps.constants.CommonConst;
import com.cesske.mps.entity.Order;
import com.cesske.mps.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(value = "用户订单", description = "用户订单相关接口")
@RestController
@RequestMapping(value = CommonConst.API_PATH_VERSION_1+"/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户订单列表", notes = "用户订单列表", httpMethod = "GET")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Order> findAll(){
        return orderService.findAll();
    }

    @ApiOperation(value = "订单详情", notes = "订单详情", httpMethod = "GET")
    @RequestMapping(value = "/find/{r_id}", method = RequestMethod.GET)
    public Order findById(@ApiParam(value = "对象id", required = true)
                              @PathVariable("r_id")
                                      Long r_id){
        return orderService.findById(r_id);
    }
}
