package com.xht.service;

import com.xht.pojo.MissionDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MissionDetailsService {

    public List<MissionDetails> getMissionDetailsById(int id);

    public int addMissionDetails(int mid,int wid);

    public int deleteMissionDetailsById(int mid,int wid);

    public int uploadMissionFile(String path,int mid,int wid);

    public int updateMissionDetailsState(int state,int mid,int wid);
}
