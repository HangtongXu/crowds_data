package com.xht.service.impl;

import com.xht.mapper.AuctionMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.WorkerAuction;
import com.xht.service.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service

public class AuctionServiceImpl implements AuctionService {

    @Autowired
    AuctionMapper auctionMapper;

    @Override
    public int addAuction(WorkerAuction workerAuctions) {
        return auctionMapper.addAuction(workerAuctions);
    }

    @Override
    public int updateAuctionState(int state, int wid, int mid) {
        return auctionMapper.updateAuctionState(state,wid,mid);
    }

    @Override
    public int updateAuctionStateMission(int state, int mid) {
        auctionMapper.updateAuctionMission(state,mid);
        auctionMapper.updateAuctionStateMission(state,mid);
        return 0;
    }

    @Override
    public List<WorkerAuction> getAuctionByMissionId(int mid) {
        return auctionMapper.getAuctionByMissionId(mid);
    }

    @Override
    public List<WorkerAuction> getAuctionByWorkerId(int wid) {
        return auctionMapper.getAuctionByWorkerId(wid);
    }

    @Override
    public WorkerAuction getAuctionById(int id) {
        return auctionMapper.getAuctionById(id);
    }

    @Override
    public List<Mission> getWaitAuctionMission() {
        return auctionMapper.getWaitAuctionMission();
    }

    @Override
    public LinkedList<WorkerAuction> getAuctionWorker(int mid, double money) {
        return auctionMapper.getAuctionWorker(mid,money);
    }

    @Override
    public boolean checkAuctionWorker(int mid, double money) {
        return auctionMapper.checkAuctionWorker(mid,money)>=1;
    }

    @Override
    public int updateAuctionStateBatch(LinkedList<Mission> missions) {
        return  auctionMapper.updateAuctionStateBatch(missions);
    }

    @Override
    public int updateWorkerAuction(LinkedList<WorkerAuction> workerAuctions) {
        return auctionMapper.updateWorkerAuction(workerAuctions);
    }

    @Override
    public List<WorkerAuction> getWorkerAuction(int wid) {
        return auctionMapper.getWorkerAuction(wid);
    }

    @Override
    public int updateWorkerAuctionStateBatch(Set ids) {
        return auctionMapper.updateWorkerAuctionStateBatch(ids);
    }

    @Override
    public WorkerAuction getAuctionByWidAndMid(int wid, int mid) {
        return auctionMapper.getAuctionByWidAndMid(wid,mid);
    }
}
