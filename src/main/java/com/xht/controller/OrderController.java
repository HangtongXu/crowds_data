package com.xht.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xht.pojo.Order;
import com.xht.pojo.PageHelpers;
import com.xht.service.MissionDetailsService;
import com.xht.service.OrderService;
import com.xht.service.PurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    private PurseService purseService;
    @Autowired
    private MissionDetailsService missionDetailsService;

    @PostMapping("/order_all")
    public PageInfo<Order> getOrderByUid(@RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Order> orders=orderService.getOrderByUid(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;
    }

    @PostMapping("/order_ack")
    public PageInfo<Order> getOrderWaitAck(@RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Order> orders=orderService.getOrderWairAck(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;
    }

    @PostMapping("/order_pay")
    public PageInfo<Order> getOrderUnPay(@RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Order> orders=orderService.getOrderUnPay(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;
    }

    @PostMapping("/order_his")
    public PageInfo<Order> getOrderHis(@RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Order> orders=orderService.getOrderHis(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Order> pageInfo=new PageInfo<>(orders);
        return pageInfo;
    }

    @GetMapping("/userAckPay")
    public String UserAck(@RequestParam("uid")int uid,@RequestParam("wid")int wid,@RequestParam("money")int money){
        if(!purseService.checkMoney(uid,money)){
            return "forbidden";
        }
        try {
            purseService.pay(uid,money);
            orderService.updateOrderState(3,uid,wid);
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @GetMapping("/workerAck")
    public String workerAck(@RequestParam("mid")int mid,@RequestParam("uid")int uid,@RequestParam("uuid")int uuid,@RequestParam("wid")int wid,@RequestParam("money")int money){
        try {
            purseService.earn(uid,money);
            orderService.updateOrderState(5,uuid,wid);
            missionDetailsService.updateMissionDetailsState(5,mid,wid);
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
}
