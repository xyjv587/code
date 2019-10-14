package com.baizhi.controller;
import com.baizhi.dto.UserDTO;
import com.baizhi.entity.User;
import com.baizhi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("echarts")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @RequestMapping("queryMap")
    public List<UserDTO> qureyMap(){
        List<UserDTO> all = userMapper.getAll();
        return all;
    }

    @RequestMapping("getAll")
    public List<Integer> getAll(Integer day){
        ArrayList<Integer>  list= new ArrayList<>();
        for (int i=0;i<7;i++){
            Integer count = userMapper.getCount(i);
            list.add(count);
        }
        return list;
    }
}
