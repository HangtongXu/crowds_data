package com.xht.service.impl;

import com.xht.mapper.UserMapper;
import com.xht.pojo.UserInfo;
import com.xht.pojo.UserMessage;
import com.xht.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;



    @Override
    public int addUserMessage(UserMessage userMessage,int type) {
        System.out.println(type);
        switch (type){
            case 0:
                return userMapper.addUserMessage(userMessage);
            case 1:
                System.out.println(111);
                return userMapper.addWorkerMessage(userMessage);
            default:
                return 0;
        }
    }

    @Override
    public int addUser(UserInfo userInfo)
    {
        userInfo.setPassword(DigestUtils.md5Hex(userInfo.getPassword()));
        return userMapper.addUser(userInfo);
    }

    @Override
    public UserMessage getUserMessage(UserInfo userInfo) {
        switch (userInfo.getType()){
            case 0:
                return userMapper.getUserMessage(userInfo.getId());
            case 1:
                return userMapper.getWorkerMessage(userInfo.getId());
            default:
                return null;
        }
    }

    @Override
    public UserInfo getUser(String username) {
        return userMapper.getUser(username);
    }

    @Override
    public int checkUsername(String username) {
        if(userMapper.checkUsername(username) == null){
            return 0;
        }
        else{
            return 1;
        }
    }

    @Override
    public int updateUsermesage(UserMessage userMessage, int index,UserInfo userInfo) {
        int back=0;
        switch (index){
            case 1:
                back= userInfo.getType()==0?userMapper.update_address(userMessage):userMapper.update_worker_address(userMessage);
                break;
            case 2:
                back= userInfo.getType()==0?userMapper.update_mail(userMessage):userMapper.update_worker_mail(userMessage);
                break;
            case 3:
                back= userInfo.getType()==0?userMapper.update_phone(userMessage):userMapper.update_worker_phone(userMessage);
                break;
            case 4:
                back= userInfo.getType()==0?userMapper.update_message(userMessage):userMapper.update_worker_message(userMessage);
                break;
            default:
                back=0;
                break;
        }
        return back;
    }

    @Override
    public String getUserImg(int uid) {
        int type=userMapper.getType(uid);
        return type==0?userMapper.getUserImg(uid):userMapper.getWorkerImg(uid);
    }

    @Override
    public int updateUserImg(UserMessage userMessage) {
        int type=userMapper.getType(userMessage.getUid());
//        System.out.println(userMessage);
        return type==0?userMapper.updateUserImg(userMessage):userMapper.updateWorkerImg(userMessage);
    }
}
