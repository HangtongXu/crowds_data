package com.xht.controller;

import com.xht.pojo.UserInfo;
import com.xht.service.UserService;
import com.xht.service.WorkerService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private WorkerService workerService;

    @RequestMapping(value = "/login",method= RequestMethod.POST)
    @ResponseBody
    public UserInfo login(@RequestBody UserInfo userInfo){
        UserInfo userInfo1=userService.getUser(userInfo.getUsername());
        if(userInfo1 == null){
            return null;
        }
        if(DigestUtils.md5Hex(userInfo.getPassword()).equals(userInfo1.getPassword())){
            userInfo1.setPassword("");
            return userInfo1;
        }
        else {
            return null;
        }
    }

    @GetMapping("/updateUserLocation")
    @ResponseBody
    public void updateUserLocation(@RequestParam("uid")int uid,@RequestParam("lng")float lng,@RequestParam("lat")float lat){
        workerService.updateWorkerLocation(uid,lng,lat);
        System.out.println("位置已更新");
    }
}
