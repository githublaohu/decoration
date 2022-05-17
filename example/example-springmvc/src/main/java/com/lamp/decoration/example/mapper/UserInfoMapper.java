package com.lamp.decoration.example.mapper;

import com.lamp.decoration.example.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select("select * from user ")
    public List<UserInfo> queryUsedrInfo(UserInfo userInfo);
}
