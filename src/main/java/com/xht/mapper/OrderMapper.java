package com.xht.mapper;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.xht.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    public int addOrder(@Param("wid") int wid, @Param("uid")int uid,@Param("money") int money);

    public List<Order> getOrderByUid(@Param("id") int id);

    public List<Order> getOrderWaitAck(@Param("id")int id);

    public List<Order> getOrderUnPay(@Param("id")int id);

    public List<Order> getOrderHis(@Param("id")int id);



    public int updateOrderState(@Param("state")int state,@Param("uid")int uid,@Param("wid")int wid);
}
