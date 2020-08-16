package com.cesske.mps.service.impl;

import com.cesske.mps.entity.Order;
import com.cesske.mps.mapper.OrderMapper;
import com.cesske.mps.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> findAll() {
        System.out.println("===================================");
        return orderMapper.findAll();
    }

    @Override
    public Order findById(Long id){
        return orderMapper.findById(id);
    }

}
