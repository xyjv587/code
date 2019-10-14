package com.baizhi.mapper;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    public List<Album> findAll(@Param("start")Integer start, @Param("rows") Integer rows);
    public int count();
    public void insert(Album album);
    public void updateUrl(Album album);
    public void updateScore(Album album);
    public void del(String[] id);
    public void updateCount(Album album);
}
