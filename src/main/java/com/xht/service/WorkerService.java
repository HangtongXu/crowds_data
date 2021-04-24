package com.xht.service;

import com.xht.pojo.Mission;
import com.xht.pojo.Worker;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface WorkerService {

    public List<Worker> getWorkerByGeoHash(Mission mission,int code);

    public int updateWorkerMaxTaskById(List<Worker> workers);

    public int updateWorkerMaxTask(int wid);
    public Worker getWorkerByUId(int id);

    public int updateWorkerLocation(int uid,float lng,float lat,String geohash);
}
