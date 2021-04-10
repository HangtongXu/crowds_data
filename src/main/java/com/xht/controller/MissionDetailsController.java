package com.xht.controller;

import com.xht.pojo.MissionDetails;
import com.xht.service.MissionDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MissionDetailsController {
    @Autowired
    MissionDetailsService missionDetailsService;

    @GetMapping("/getMissionDetails")
    @ResponseBody
    public List<MissionDetails> getMissionDetails(int id){
//        System.out.println("in");
//        System.out.println(missionDetailsService.getMissionDetailsById(id));
        return missionDetailsService.getMissionDetailsById(id);
    }
}
