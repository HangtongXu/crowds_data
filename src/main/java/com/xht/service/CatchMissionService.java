package com.xht.service;

import com.xht.pojo.Mission;
import com.xht.pojo.MissionDetails;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatchMissionService {
    public List<Mission> getAvliableMission(String geohash,int wid);

    public List<MissionDetails> getWaitDoneMission(@Param("id") int id);
    public List<MissionDetails> getUnPayMission(@Param("id") int id);
    public List<MissionDetails> getDoneMission(@Param("id") int id);

    public int catchMissionById(int id);
}
