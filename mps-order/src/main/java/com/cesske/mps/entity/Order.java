package com.cesske.mps.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long rid;//编号
    private String orderNo;//编号

    public Order() {
    }

    public Order(Long rid, String orderNo) {
        this.rid = rid;
        this.orderNo = orderNo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
