package com.xht.pojo;



import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Mission {
    private int id;
    private int uid;
    private String name;
    private int demand;
    private double money;
    private int form;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String message;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(locale="zh", pattern="yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    private int state;
    private float longtitude;
    private float latitude;
    private int members;
    private String geohash;
    private String address;



}
