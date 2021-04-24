package com.xht.utils;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.xht.mapper.OrderMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.MissionDetails;
import com.xht.pojo.Worker;
import com.xht.pojo.WorkerAuction;
import com.xht.service.AuctionService;
import com.xht.service.MissionDetailsService;
import com.xht.service.MissionService;
import com.xht.service.OrderService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
@Data
public class AuctionDistribute {
    private LinkedList<Mission> auctionMisssions=new LinkedList<>();
    private HashMap<Integer,LinkedList<WorkerAuction>> workerMaps=new HashMap<>();
    private double maxMoney=0;
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private MissionDetailsService missionDetailsService;
    @Autowired
    private OrderService orderService;
    public AuctionDistribute(){
        this.auctionMisssions=new LinkedList<>();
        this.workerMaps=new HashMap<>();
        this.maxMoney=0;
    }

    //设定请求者的最低赏金，根据最后一个能完成请求的请求者的赏金上限决定,不删除最后一个请求任务
    public void decideAskerPrice(){
        List<Mission> originalMission=auctionService.getWaitAuctionMission();
        if(originalMission.size()<1){
            return;
        }
//        System.out.println("original mission  size:---"+originalMission.size());
        for(Mission mission:originalMission){
            if(auctionService.checkAuctionWorker(mission.getId(),mission.getMoney())){
                this.auctionMisssions.add(mission);
            }
            else{
                //此阶段无满足条件工作者参与竞拍，视为流拍，即竞拍失败,更新任务及相关参与竞拍工作者的相关状态为失败
                auctionService.updateAuctionStateMission(-1,mission.getId());
            }
        }
//        System.out.println("auction mission  size:---"+auctionMisssions.size());
        Mission mission=this.auctionMisssions.getLast();
        this.maxMoney=mission.getMoney();
    }

    //设定每一个任务相应工作者的赏金，依据为申请成功的工作者中的最低报价
    public void decideWorkerPrice(){
        for(Mission mission:this.auctionMisssions){
            LinkedList<WorkerAuction> workerAuctions=auctionService.getAuctionWorker(mission.getId(),this.maxMoney);
            int num=Math.min(mission.getMembers(),workerAuctions.size())-1;
            //删除超出任务执行者上限的任务，优先删除出价较高工作者
            while(num+1<workerAuctions.size()){
                workerAuctions.removeLast();
            }
            if(workerAuctions.isEmpty()){
                return;
            }
            //根据选出的工作者中的最高出价决定工作者的最终赏金
            int money=workerAuctions.getLast().getMoney();
            //更新工作者的最终实际赏金
            for(WorkerAuction workerAuction:workerAuctions){
                workerAuction.setMoney(money);
            }
            //保存
            this.workerMaps.put(mission.getId(),workerAuctions);
        }
    }

    public void updateState(){
        //更新任务的赏金为设定的任务赏金
        for(Mission mission:auctionMisssions){
            mission.setMoney(this.maxMoney);
        }
        //更新工作者竞价订单状态以及竞价
        //先将此次相关任务的所有竞价订单视为失败，然后针对成功订单单独修改
        auctionService.updateWorkerAuctionStateBatch(workerMaps.keySet());
        for(LinkedList linkedList:this.workerMaps.values()){
            auctionService.updateWorkerAuction(linkedList);
        }
        //创建订单
        for(Mission mission:this.auctionMisssions){
            for(WorkerAuction auction:this.workerMaps.get(mission.getId())){
                orderService.addAuctionOrder(auction.getWid(),mission.getUid(),(int)this.maxMoney);
                missionDetailsService.addMissionDetails(mission.getId(),auction.getWid());
            }
        }
        //更新任务状态为竞拍结束,以及赏金金额
        System.out.println("update count:"+auctionService.updateAuctionStateBatch(this.auctionMisssions));;
        this.auctionMisssions.clear();
        this.workerMaps.clear();
    }
}
