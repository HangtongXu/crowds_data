package com.xht.mapper;

import com.xht.pojo.Purse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PurseMapper {

    public Purse getPurseById(@Param("uid") int uid);

    public boolean checkMoney(@Param("uid") int uid,@Param("money") int money);
    public int pay(@Param("uid") int uid,@Param("money") int money);
    public int earn(@Param("uid") int uid,@Param("money") int money);

    public boolean checkPurseExist(@Param("uid")int uid);

    public int createPurse(@Param("uid")int uid);
}
