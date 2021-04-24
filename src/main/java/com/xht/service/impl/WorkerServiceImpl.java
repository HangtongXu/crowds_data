package com.xht.service.impl;

import ch.hsr.geohash.GeoHash;
import com.xht.mapper.WorkerMapper;
import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import com.xht.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Autowired
    WorkerMapper workerMapper;

    @Override
    public List<Worker> getWorkerByGeoHash(Mission mission,int code) {
        GeoHash geoHash=GeoHash.withCharacterPrecision(mission.getLatitude(),mission.getLongtitude(),code);
        List<Worker> res=new LinkedList<>();
        for(GeoHash geo:geoHash.getAdjacent()){
            res.addAll(workerMapper.getWorkerByGeoHash(geo.toBase32()));
        }
        return res;
    }

    @Override
    @Transactional
    public int updateWorkerMaxTaskById(List<Worker> workers) {
        int back=workerMapper.updateWorkerMaxTaskById(workers);

        return  back;
    }

    @Override
    public int updateWorkerMaxTask(int wid) {
        return workerMapper.updateWorkerMaxTask(wid);
    }

    @Override
    public Worker getWorkerByUId(int id) {
        return workerMapper.getWorkerByUId(id);
    }

    @Override
    public int updateWorkerLocation(int uid, float lng, float lat,String geohash) {
        return workerMapper.updateWorkerLocation(uid,lng,lat,geohash);
    }
}
