package com.xht.mapper;

import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import com.xht.pojo.WorkerAuction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Mapper
public interface AuctionMapper {

    public int addAuction(WorkerAuction workerAuctions);

    public int updateAuctionState(@Param("state")int state,@Param("wid")int wid,@Param("mid") int mid);
    public int updateAuctionStateMission(@Param("state")int state,@Param("mid") int mid);

    public List<WorkerAuction> getAuctionByMissionId(@Param("mid")int mid);

    public List<WorkerAuction> getAuctionByWorkerId(@Param("wid")int wid);

    public WorkerAuction getAuctionById(@Param("id")int id);

    public List<Mission> getWaitAuctionMission();

    public LinkedList<WorkerAuction> getAuctionWorker(@Param("mid") int mid, @Param("money") double money);

    public int checkAuctionWorker(@Param("mid")int mid,@Param("money") double money);

    public int updateAuctionStateBatch(LinkedList<Mission> missions);

    public int updateWorkerAuction(LinkedList<WorkerAuction> workerAuctions);

    public List<WorkerAuction> getWorkerAuction(@Param("wid")int wid);

    public int updateAuctionMission(@Param("state")int state,@Param("mid") int mid);

    public int updateWorkerAuctionStateBatch(Set ids);

    public WorkerAuction getAuctionByWidAndMid(@Param("wid")int wid,@Param("mid")int mid);
}
