package com.xht.service;

import com.xht.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    public int addOrder(int wid,int uid,int money);
    public int addAuctionOrder(int wid,int uid,int money);

    public List<Order> getOrderByUid(int id);

    public int updateOrderState(int state,int uid,int wid);

    public List<Order> getOrderWairAck(int id);

    public List<Order> getOrderUnPay(int id);

    public List<Order> getOrderHis(int id);
}
