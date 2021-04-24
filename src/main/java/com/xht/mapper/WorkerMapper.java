package com.xht.mapper;

import com.xht.pojo.Worker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface WorkerMapper {

    public List<Worker> getWorkerByGeoHash(String geohash);
    public int updateWorkerMaxTaskById(List<Worker> workers);
    public int updateWorkerMaxTask(@Param("wid") int wid);
    public Worker getWorkerByUId(@Param("id") int id);
    public int updateWorkerLocation(@Param("uid")int uid,@Param("lng")float lng,@Param("lat")float lat,@Param("geohash")String geohash);
}
