package com.lamp.decoration.example.controller;

import com.lamp.decoration.example.entity.UserInfo;
import com.lamp.decoration.example.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/page")
@RestController
public class PageController {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @PostMapping("/paging")
    public List<UserInfo> paging( ){

        return userInfoMapper.queryUsedrInfo(null);
    }
}
