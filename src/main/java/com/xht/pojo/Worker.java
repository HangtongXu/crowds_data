package com.xht.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class Worker {
    private int id;
    private int uid;
    private String name;
    private int sex;
    private String idcard;
    private int age;
    private String address;
    private String email;
    private String phone;
    private String message;
    private double longtitude;
    private double latitude;
    private double speed;
    private double direction;
    private String geohash;
    private int maxtask;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;

}
