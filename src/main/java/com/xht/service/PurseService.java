package com.xht.service;

import com.xht.pojo.Purse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface PurseService {

    public Purse getPurseById(int uid);

    public boolean checkMoney(int uid,  int money);
    public int pay( int uid,int money);
    public int earn( int uid,int money);
}
