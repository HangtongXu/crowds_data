package com.xht.mapper;


import com.xht.pojo.UserInfo;
import com.xht.pojo.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    public UserInfo getUser(String username);
    public int addUser(UserInfo userInfo);
    public int getType(@Param("uid") int uid);
    public Integer checkUsername(String Username);
//    public int getUserIdByUsername(String Username);

    public UserMessage getUserMessage(int id);
    public UserMessage getWorkerMessage(int id);

    public int addUserMessage(UserMessage userMessage);
    public int addWorkerMessage(UserMessage userMessage);

    public int update_address(UserMessage userMessage);
    public int update_phone(UserMessage userMessage);
    public int update_mail(UserMessage userMessage);
    public int update_message(UserMessage userMessage);
    public String getUserImg(int uid);
    public int updateUserImg(UserMessage userMessage);

    public int update_worker_address(UserMessage userMessage);
    public int update_worker_phone(UserMessage userMessage);
    public int update_worker_mail(UserMessage userMessage);
    public int update_worker_message(UserMessage userMessage);
    public String getWorkerImg(int uid);
    public int updateWorkerImg(UserMessage userMessage);
}
