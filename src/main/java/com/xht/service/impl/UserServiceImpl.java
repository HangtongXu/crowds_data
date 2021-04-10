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
    public int addUserMessage(UserMessage userMessage) {
        return userMapper.addUserMessage(userMessage);
    }

    @Override
    public int addUser(UserInfo userInfo)
    {
        userInfo.setPassword(DigestUtils.md5Hex(userInfo.getPassword()));
        return userMapper.addUser(userInfo);
    }

    @Override
    public UserMessage getUserMessage(int id) {
        return userMapper.getUserMessage(id);
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
    public int updateUsermesage(UserMessage userMessage, int index) {
        int back=0;
        switch (index){
            case 1:
                back= userMapper.update_address(userMessage);
                break;
            case 2:
                back= userMapper.update_mail(userMessage);
                break;
            case 3:
                back= userMapper.update_phone(userMessage);
                break;
            case 4:
                back= userMapper.update_message(userMessage);
                break;
            default:
                back=0;
                break;
        }
        return back;
    }

    @Override
    public String getUserImg(int uid) {
        return userMapper.getUserImg(uid);
    }

    @Override
    public int updateUserImg(UserMessage userMessage) {
        return userMapper.updateUserImg(userMessage);
    }
}
