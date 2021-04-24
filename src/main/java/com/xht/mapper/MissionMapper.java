package com.xht.mapper;

import com.xht.pojo.Mission;
import com.xht.pojo.WorkerAuction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MissionMapper {
    public List<Mission> getMission(int uid);
    public List<Mission> getMissionByState(@Param("uid") int uid,@Param("state")int state);
    public int addMission(Mission mission);
    public Mission getMissionById(int id);
    public int updateMission(Mission mission);
    public int deleteMission(int id);

    public List<Mission> getMissionByGeoHash(String geohash);

    public List<Mission> getDistributeMission();

    public int updateMissionStateBatch(List<Mission> missions);
    public int updateMissionState(@Param("state") int state, @Param("id") int id);
    public int updateMissionMembers(@Param("members") int members,@Param("id") int id);

    public int checkMissionState(@Param("id" ) int id);

    public int updatepriority(@Param("id") int id);

    public List<WorkerAuction> getAuctionMission(@Param("geohash") String geohash,@Param("wid") int wid);

    public List<Mission> getOwnAuction(@Param("uid") int uid);

    public int getAuctionMember(@Param("mid") int mid);
}

