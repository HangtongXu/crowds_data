package com.xht.service;

import com.xht.pojo.Mission;
import com.xht.pojo.WorkerAuction;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public interface AuctionService {
    public int addAuction(WorkerAuction workerAuctions);

    public int updateAuctionState(int state, int wid, int mid);
    public int updateAuctionStateMission(int state,  int mid);

    public List<WorkerAuction> getAuctionByMissionId(int mid);

    public List<WorkerAuction> getAuctionByWorkerId(int wid);

    public WorkerAuction getAuctionById(int id);

    public List<Mission> getWaitAuctionMission();

    public LinkedList<WorkerAuction> getAuctionWorker(int mid, double money);

    public boolean checkAuctionWorker(int mid,double money);

    public int updateAuctionStateBatch(LinkedList<Mission> missions);

    public int updateWorkerAuction(LinkedList<WorkerAuction> workerAuctions);

    public List<WorkerAuction> getWorkerAuction(int wid);

    public int updateWorkerAuctionStateBatch(Set ids);

    public WorkerAuction getAuctionByWidAndMid(int wid,int mid);
}
