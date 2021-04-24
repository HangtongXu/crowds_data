package com.xht.controller;

import ch.hsr.geohash.GeoHash;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import com.xht.pojo.Mission;
import com.xht.pojo.PageHelpers;
import com.xht.pojo.Worker;
import com.xht.pojo.WorkerAuction;
import com.xht.service.AuctionService;
import com.xht.service.MissionService;
import com.xht.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuctionController {
    @Autowired
    AuctionService auctionService;
    @Autowired
    WorkerService workerService;
    @Autowired
    MissionService missionService;


    @PostMapping("/getAuctionMission")
    public PageInfo<WorkerAuction> getAvliableAuctionMission(@RequestBody PageHelpers pageHelpers){
//        System.out.println(pageHelpers);
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        //由于是预定任务，所以采用的geohash编码长度较小，可以搜索较大区域内的任务
//        System.out.println(worker);
        GeoHash geoHash=GeoHash.withCharacterPrecision(worker.getLatitude(),worker.getLongtitude(),4);
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<WorkerAuction> workerAuctions=missionService.getAuctionMission(geoHash.toBase32(),worker.getId());
        PageInfo<WorkerAuction> pageInfo=new PageInfo<>(workerAuctions);
        return pageInfo;
    }

    @GetMapping("/getWorkerMaxTask")
    public Worker getWorkerMaxTask(int uid){
        Worker worker=workerService.getWorkerByUId(uid);
        return worker;
    }

    @PostMapping("/addAuction")
    public String addAuction(@RequestBody WorkerAuction workerAuction){
        try {
            auctionService.addAuction(workerAuction);
        }
        catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    @PostMapping("/getMission/mauction")
    public PageInfo<WorkerAuction> getWorkerAuction(@RequestBody PageHelpers pageHelpers){
        Worker worker=workerService.getWorkerByUId(pageHelpers.getId());
        PageHelper.startPage(pageHelpers.getPageNum(),pageHelpers.getPageSize());
        List<WorkerAuction> missions=auctionService.getWorkerAuction(worker.getId());
//        System.out.println(pageHelpers);
//        System.out.println(missions);
        PageInfo<WorkerAuction> pageInfo=new PageInfo<>(missions);
        return pageInfo;
    }
}
