package com.xht.service.impl;

import com.xht.mapper.MissionDetailsMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.MissionDetails;
import com.xht.service.MissionDetailsService;
import com.xht.service.MissionService;
import com.xht.service.OrderService;
import com.xht.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissionDetailsServiceImpl implements MissionDetailsService {

    @Autowired
    MissionDetailsMapper missionDetailsMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MissionService missionService;
    @Autowired
    private WorkerService workerService;

    @Override
    public List<MissionDetails> getMissionDetailsById(int id) {
        return missionDetailsMapper.getMissionDetailsById(id);
    }

    @Override
    public int addMissionDetails(int mid, int wid) {
        return missionDetailsMapper.addMissionDetails(mid,wid);
    }

    @Override
    public int deleteMissionDetailsById(int mid, int wid) {
        return missionDetailsMapper.deleteMissionDetailsById(mid,wid);
    }

    @Override
    public int uploadMissionFile(String path, int mid, int wid) {
        //更新任务状态
        missionDetailsMapper.updateMissionState(3,mid,wid);
        //更新订单状态
        Mission mission=missionService.getMissionById(mid);
        orderService.updateOrderState(2,mission.getUid(),wid);
        //更新工作者可接受任务数量
        workerService.updateWorkerMaxTask(wid);
        //检测任务是否完成，判断是否更改任务状态
        System.out.println(missionDetailsMapper.checkMissionState(mid));
        if (missionDetailsMapper.checkMissionState(mid)<1 && missionDetailsMapper.checkMissionContains(mid)) {
            missionDetailsMapper.changeMissionDone(mid);
        }
        return missionDetailsMapper.uploadMissionFile(path,mid,wid);
    }

    @Override
    public int updateMissionDetailsState(int state, int mid, int wid) {
        return missionDetailsMapper.updateMissionState(state,mid,wid);
    }
}
