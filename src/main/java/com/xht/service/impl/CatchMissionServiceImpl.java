package com.xht.service.impl;

import com.xht.mapper.CatchMissionMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.MissionDetails;
import com.xht.service.CatchMissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatchMissionServiceImpl implements CatchMissionService {

    @Autowired
    private CatchMissionMapper catchMissionMapper;

    @Override
    public List<Mission> getAvliableMission(String geohash,int wid) {
        return catchMissionMapper.getAvliableMission(geohash,wid);
    }

    @Override
    public List<MissionDetails> getWaitDoneMission(int id) {
        return catchMissionMapper.getWaitDoneMission(id);
    }

    @Override
    public List<MissionDetails> getUnPayMission(int id) {
        return catchMissionMapper.getUnPayMission(id);
    }

    @Override
    public List<MissionDetails> getDoneMission(int id) {
        return catchMissionMapper.getDoneMission(id);
    }

    @Override
    public int catchMissionById(int id) {
        return catchMissionMapper.catchMissionById(id);
    }
}
