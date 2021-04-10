package com.xht.mapper;

import com.xht.pojo.Mission;
import com.xht.pojo.MissionDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CatchMissionMapper {

    public List<Mission> getAvliableMission(@Param("geohash") String geohash,@Param("wid")int wid);
    public List<MissionDetails> getWaitDoneMission(@Param("id") int id);
    public List<MissionDetails> getUnPayMission(@Param("id") int id);
    public List<MissionDetails> getDoneMission(@Param("id") int id);
    public int catchMissionById(int id);
}
