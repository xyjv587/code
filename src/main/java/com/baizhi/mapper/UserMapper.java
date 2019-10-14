package com.baizhi.mapper;
import com.baizhi.dto.UserDTO;
import java.util.List;

public interface UserMapper {
    public List<UserDTO> getAll();
    public Integer getCount(Integer day);
}
