package com.xht.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class MissionDetails {
    private  int id;
    private int mid;
    private int wid;
    private Worker worker;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    private int state;
    private Mission mission;
    private String filePath;
}
