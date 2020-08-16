package com.cesske.mps.mapper;

import com.cesske.mps.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> findAll();

    Order findById(Long id);
}
