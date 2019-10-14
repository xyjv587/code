package com.baizhi;

import com.baizhi.dto.UserDTO;
import com.baizhi.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
    @Autowired
    UserMapper userMapper;
    @Test
    public void contextLoads() {
        Integer count = userMapper.getCount(1);
        System.out.println(count);
        System.out.println("hello");
    }

}
