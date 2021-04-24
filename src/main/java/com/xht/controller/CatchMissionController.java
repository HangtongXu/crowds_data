package com.xht.controller;

import ch.hsr.geohash.GeoHash;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.xht.pojo.*;
import com.xht.service.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CatchMissionController {

    @Autowired
    private CatchMissionService catchMissionService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MissionDetailsService missionDetailsService;
    @Autowired
    private AuctionService auctionService;

    @PostMapping("/getAvliableWork")
    public PageInfo<Mission> getAvliableMission(@RequestBody PageHelpers pageHelpers){
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        GeoHash geoHash=GeoHash.withCharacterPrecision(worker.getLatitude(),worker.getLongtitude(),4);
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        System.out.println(geoHash.toBase32()+" "+worker.getId());
        List<Mission> missions=catchMissionService.getAvliableMission(geoHash.toBase32(),pageHelpers.getId());
        PageInfo<Mission> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }
    @PostMapping("/getWaitDoneWork")
    public PageInfo<MissionDetails> getWaitDoneMission(@RequestBody PageHelpers pageHelpers){
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<MissionDetails> missions=catchMissionService.getWaitDoneMission(worker.getId());
        for(MissionDetails missionDetails:missions){
            if(missionDetails.getMission().getDistribute()==1){
                missionDetails.getMission().setMoney(auctionService.getAuctionByWidAndMid(missionDetails.getWid(),missionDetails.getMid()).getMoney());
            }
        }
        PageInfo<MissionDetails> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }
    @PostMapping("/getUnPayWork")
    public PageInfo<MissionDetails> getUnPayMission(@RequestBody PageHelpers pageHelpers){
        System.out.println(pageHelpers);
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<MissionDetails> missions=catchMissionService.getUnPayMission(worker.getId());
        for(MissionDetails missionDetails:missions){
            if(missionDetails.getMission().getDistribute()==1){
                missionDetails.getMission().setMoney(auctionService.getAuctionByWidAndMid(missionDetails.getWid(),missionDetails.getMid()).getMoney());
            }
        }
        PageInfo<MissionDetails> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }
    @PostMapping("/getDoneWork")
    public PageInfo<MissionDetails> getDoneMission(@RequestBody PageHelpers pageHelpers){
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<MissionDetails> missions=catchMissionService.getDoneMission(worker.getId());
        for(MissionDetails missionDetails:missions){
            if(missionDetails.getMission().getDistribute()==1){
                missionDetails.getMission().setMoney(auctionService.getAuctionByWidAndMid(missionDetails.getWid(),missionDetails.getMid()).getMoney());
            }
        }
        PageInfo<MissionDetails> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }

    @PostMapping("/catchMission")
    public String catchMission(@RequestBody CatchWork catchWork){
        Worker worker=workerService.getWorkerByUId(catchWork.getWid());
        if(!missionService.checkMissionState(catchWork.getMid())){
            return "1";
        }
        if(worker.getMaxtask()<1){
            return "2";
        }
        try {
            missionDetailsService.addMissionDetails(catchWork.getMid(),catchWork.getWid());
            Mission mission=missionService.getMissionById(catchWork.getMid());
            orderService.addOrder(catchWork.getWid(),mission.getUid(),(int)mission.getMoney());
            missionService.updateMissionMembers(mission.getMembers()-1,mission.getId());
            List<Worker> workers=new ArrayList<>();
            worker.setMaxtask(worker.getMaxtask()-1);
            workers.add(worker);
            workerService.updateWorkerMaxTaskById(workers);
        }
        catch (Exception e){
            e.printStackTrace();
            return "3";
        }
        return "success";
    }
}
