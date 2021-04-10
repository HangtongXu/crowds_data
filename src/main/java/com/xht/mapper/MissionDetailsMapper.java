package com.xht.mapper;

import com.xht.pojo.MissionDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MissionDetailsMapper {
    public List<MissionDetails> getMissionDetailsById(@Param("id") int id);

    public int addMissionDetails(@Param("mid") int mid,@Param("wid") int wid);

    public int deleteMissionDetailsById(@Param("mid") int mid,@Param("wid") int wid);

    public int uploadMissionFile(@Param("filePath")String filePath,@Param("mid")int mid,@Param("wid")int wid);

    public int updateMissionState(@Param("state")int state,@Param("mid")int mid,@Param("wid")int wid);

    public boolean checkMissionContains(@Param("mid") int mid);

    public int checkMissionState(@Param("mid")int mid);

    public int changeMissionDone(@Param("mid")int mid);
}
