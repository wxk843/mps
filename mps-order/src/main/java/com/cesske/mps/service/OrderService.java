package com.cesske.mps.service;

import com.cesske.mps.entity.Order;
import java.util.List;

public interface OrderService {
    List<Order> findAll();

    Order findById(Long id);
}
