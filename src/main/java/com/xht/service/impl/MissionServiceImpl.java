package com.xht.service.impl;

import ch.hsr.geohash.GeoHash;
import com.xht.mapper.MissionMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import com.xht.service.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionMapper missionMapper;

    @Override
    public List<Mission> getMission(int uid) {
        return missionMapper.getMission(uid);
    }

    @Override
    public List<Mission> getMissionByState(int uid, int state) {
        return missionMapper.getMissionByState(uid,state);
    }

    @Override
    public List<Mission> getDistributeMission() {
        return missionMapper.getDistributeMission();
    }

    @Override
    public int addMission(Mission mission) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(mission.getLatitude(), mission.getLongtitude(), 5);
        mission.setGeohash(geoHash.toBase32());
        return missionMapper.addMission(mission);
    }

    @Override
    public int updateMissiob(Mission mission) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(mission.getLatitude(), mission.getLongtitude(), 5);
        mission.setGeohash(geoHash.toBase32());
        if (missionMapper.getMissionById(mission.getId()).getState() != 0) {
            return 0;
        }

        return missionMapper.updateMission(mission);
    }

    @Override
    public int deleteMission(int id) {
        if (missionMapper.getMissionById(id).getState() != 0) {
            return 0;
        } else {
            return missionMapper.deleteMission(id);
        }
    }

    @Override
    public List<Mission> getMissionByGeoHash(Worker worker,int code) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(worker.getLatitude(), worker.getLongtitude(), code);
        List<Mission> res=new LinkedList<>();
        for(GeoHash geo:geoHash.getAdjacent()){
            res.addAll(missionMapper.getMissionByGeoHash(geo.toBase32()));
        }
        System.out.println(res);
        return res;
    }

    @Override
    public int updateMissionState(List<Mission> missions) {
        return missionMapper.updateMissionStateBatch(missions);
    }

    @Override
    public int updateMissionState(int state, int id) {
        return missionMapper.updateMissionState(state,id);
    }

    @Override
    public int updateMissionMembers(int members, int id) {
        return missionMapper.updateMissionMembers(members,id);
    }

    @Override
    public boolean checkMissionState(int id) {
        if(missionMapper.checkMissionState(id)<1){
            return false;
        }
        return true;
    }

    @Override
    public Mission getMissionById(int id) {
        return missionMapper.getMissionById(id);
    }

    @Override
    public int updatepriority(int id) {
        return missionMapper.updatepriority(id);
    }

}
