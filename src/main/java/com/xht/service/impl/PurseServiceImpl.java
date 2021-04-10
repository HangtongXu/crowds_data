package com.xht.service.impl;

import com.xht.mapper.PurseMapper;
import com.xht.pojo.Purse;
import com.xht.service.PurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurseServiceImpl implements PurseService
{

    @Autowired
    PurseMapper purseMapper;


    @Override
    public Purse getPurseById(int uid) {
        return purseMapper.getPurseById(uid);
    }

    @Override
    public boolean checkMoney(int uid, int money) {
        return purseMapper.checkMoney(uid,money);
    }

    @Override
    public int pay(int uid, int money) {
        return purseMapper.pay(uid,money);
    }


    @Override
    public int earn(int uid, int money) {
        if(!purseMapper.checkPurseExist(uid)){
            purseMapper.createPurse(uid);
        }
        return purseMapper.earn(uid,money);
    }
}
