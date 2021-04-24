package com.xht.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xht.pojo.Mission;
import com.xht.pojo.PageHelpers;
import com.xht.pojo.Worker;
import com.xht.service.MissionService;
import com.xht.service.WorkerService;
import com.xht.utils.AlgorithmOptimalSet;
import com.xht.utils.TaskDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
public class MissionController {

    @Autowired
    private MissionService missionService;
    @Autowired
    private WorkerService workerService;
    @RequestMapping("/addwork")
    public String addMission(@RequestBody Mission mission){
        System.out.println(mission);
        int back=missionService.addMission(mission);
        if (back>0){
            return "success";
        }
        else {
            return "error";
        }
    }

    @PostMapping("/getMission/all")
    public PageInfo<Mission> getMissionAll(@RequestBody PageHelpers pageHelpers){

        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Mission> missions=missionService.getMission(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Mission> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }

    @PostMapping("/updateMission")
    public String updateMission(@RequestBody Mission mission){
        if(missionService.updateMissiob(mission)>0){
            return "success";
        }
        return "error";
    }

    @GetMapping("/deleteMission")
    public String deleteMission(Integer id){
        if(missionService.deleteMission(id)>0){
            return "success";
        }
        else {
            return "error";
        }
    }

    @PostMapping("/getMission/doing")
    public PageInfo<Mission> getMissionDoing( @RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Mission> missions=missionService.getMissionByState(pageHelpers.getId(),pageHelpers.getState());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Mission> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }
    @PostMapping("/getMission/done")
    public PageInfo<Mission> getMissionDone( @RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Mission> missions=missionService.getMissionByState(pageHelpers.getId(),pageHelpers.getState());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Mission> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }

    @PostMapping("/getMission/auction")
    public PageInfo<Mission> getOwnAuction(@RequestBody PageHelpers pageHelpers){
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<Mission> missions=missionService.getOwnAuction(pageHelpers.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<Mission> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }


    @GetMapping("/getMission/auctionMember")
    public int getAuctionMembers(int id){
        return missionService.getAuctionMember(id);
    }
}
