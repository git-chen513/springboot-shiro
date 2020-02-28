package com.kuangshen.mapper;

import com.kuangshen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    public User queryByName(String name);
}
