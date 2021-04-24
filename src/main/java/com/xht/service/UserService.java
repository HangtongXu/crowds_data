package com.xht.service;

import com.xht.pojo.UserInfo;
import com.xht.pojo.UserMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public int addUserMessage(UserMessage userMessage,int type);
    public int addUser(UserInfo userInfo);
    public UserMessage getUserMessage(UserInfo user);
    public UserInfo getUser(String username);

    public int checkUsername(String username);

    public int updateUsermesage(UserMessage userMessage,int index,UserInfo userInfo);

    public String getUserImg(int uid);
    public int updateUserImg(UserMessage userMessage);
}
