package com.baizhi.mapper;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    public List<Chapter> queryPage(@Param("start")Integer start, @Param("rows")Integer rows, @Param("album_id")String album_id);

    public int count(String album_id);

    public void save(Chapter chapter);

    public void delete(String[] id);

    public void updateUrl(Chapter chapter);
}
