package com.akun.demo.mapper;

import com.akun.demo.model.UserLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserLocationMapper")
public interface UserLocationMapper {

    //2、查询：（最新）的num篇文章
    List<UserLocation> selectAllUserLocation(int num);

    int deleteByPrimaryKey(Integer id);

    int insert(UserLocation record);

    int insertSelective(UserLocation record);

    UserLocation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLocation record);

    int updateByPrimaryKey(UserLocation record);
}