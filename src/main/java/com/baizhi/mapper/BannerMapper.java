package com.baizhi.mapper;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface BannerMapper {
    public List<Banner> findAll(@Param("start")Integer start, @Param("rows") Integer rows);
    public Integer count();
    public void save(Banner banner);
    public void update(Banner banner);
    public void updateStatus(Banner banner);
    public void del(String[] id);
    public List<Banner> getAll();
}
