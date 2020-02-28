package com.kuangshen.service.impl;

import com.kuangshen.mapper.UserMapper;
import com.kuangshen.pojo.User;
import com.kuangshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByName(String name) {
        return userMapper.queryByName(name);
    }
}
