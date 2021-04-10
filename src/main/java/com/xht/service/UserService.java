package com.xht.service;

import com.xht.pojo.UserInfo;
import com.xht.pojo.UserMessage;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public int addUserMessage(UserMessage userMessage);
    public int addUser(UserInfo userInfo);
    public UserMessage getUserMessage(int id);
    public UserInfo getUser(String username);

    public int checkUsername(String username);

    public int updateUsermesage(UserMessage userMessage,int index);

    public String getUserImg(int uid);
    public int updateUserImg(UserMessage userMessage);
}
