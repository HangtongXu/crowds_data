package com.xht.service.impl;

import com.xht.mapper.OrderMapper;
import com.xht.pojo.Order;
import com.xht.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Override
    public int addOrder(int wid, int uid, int money) {
        return orderMapper.addOrder(wid,uid,money);
    }

    @Override
    public int addAuctionOrder(int wid, int uid, int money) {
        return orderMapper.addAuctionOrder(wid,uid,money);
    }

    @Override
    public List<Order> getOrderByUid(int id) {
        return orderMapper.getOrderByUid(id);
    }

    @Override
    public int updateOrderState(int state, int uid, int wid) {
        return orderMapper.updateOrderState(state,uid,wid);
    }

    @Override
    public List<Order> getOrderWairAck(int id) {
        return orderMapper.getOrderWaitAck(id);
    }

    @Override
    public List<Order> getOrderUnPay(int id) {
        return orderMapper.getOrderUnPay(id);
    }

    @Override
    public List<Order> getOrderHis(int id) {
        return orderMapper.getOrderHis(id);
    }
}
