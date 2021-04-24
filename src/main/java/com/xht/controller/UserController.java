package com.xht.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xht.pojo.UserInfo;
import com.xht.pojo.UserMessage;
import com.xht.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private HashMap<String,UserInfo> userInfos=new HashMap<>();

    @RequestMapping(value = "/re_one",method= RequestMethod.POST)
    public String userRegister(@RequestBody UserInfo userInfo){
        if(userService.checkUsername(userInfo.getUsername())>0){
            return "error";
        }
        userInfos.put(userInfo.getUsername(),userInfo);
        return "success";
    }
    @RequestMapping(value = "/re_back",method= RequestMethod.POST)
    public String deleteUser(@RequestBody String username){
        userInfos.remove(username);
        return "success";
    }
    @RequestMapping(value = "/re_two",method= RequestMethod.POST)
    public String completeMessage(@RequestBody Map<String,Object> data){
//        System.out.println(userMessage);
//        System.out.println(userInfos.get("1"));
        System.out.println(data);
        System.out.println(data.get("userInfo").getClass());
        UserInfo userInfo= JSONObject.parseObject(JSON.toJSONString(data.get("userInfo")),UserInfo.class);
        UserMessage userMessage=JSONObject.parseObject(JSON.toJSONString(data.get("userMessage")),UserMessage.class);
        System.out.println(userInfo);
        System.out.println(userMessage);
        System.out.println(userInfos.get(userInfo.getUsername()));
        if(userService.addUser(userInfos.get(userInfo.getUsername()))<1){
            return "error";
        }
        userMessage.setUid(userService.getUser(userInfo.getUsername()).getId());
        if(userService.addUserMessage(userMessage,userInfos.get(userInfo.getUsername()).getType())>0){
            return "success";
        }
        else {
            return "error";
        }
    }


    @RequestMapping(value = "/getUserInfo",method =RequestMethod.POST)
    public UserMessage getUserInfo(@RequestBody UserInfo userInfo){
        UserInfo user=userService.getUser(userInfo.getUsername());
        UserMessage userMessage=userService.getUserMessage(user);
        return userMessage;
    }

    @RequestMapping(value = "/updateUserMessage",method = RequestMethod.POST)
    public String updateUserMessage(@RequestBody UserMessage userMessage){
        UserInfo user=userService.getUser(userMessage.getName());
        userMessage.setUid(user.getId());
        int back=userService.updateUsermesage(userMessage,userMessage.getId(),user);
        System.out.println(userMessage);
        System.out.println(user.getType()==0?0:1);
        System.out.println(back);
        if(back>0){
            return "success";
        }
        else {
            return "error";
        }
    }
}
