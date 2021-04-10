package com.xht.service;

import com.xht.mapper.MissionMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MissionService {
    public List<Mission> getMission(int uid);
    public List<Mission> getMissionByState(int uid,int state);
    public List<Mission> getDistributeMission();
    public int addMission(Mission mission);
    public int updateMissiob(Mission mission);
    public int deleteMission(int id);
    public List<Mission> getMissionByGeoHash(Worker worker,int code);

    public int updateMissionState(List<Mission> missions);
    public int updateMissionState(int state,int id);
    public int updateMissionMembers(int members,int id);

    public boolean checkMissionState(int id);
    public Mission getMissionById(int id);

    public int updatepriority(int id);
}
