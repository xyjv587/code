package com.baizhi.service;

import com.baizhi.entity.Chapter;

import java.util.Map;

public interface ChapterService {
    public Map<String,Object> query(Integer start,Integer rows,String albumId);

    public String save(Chapter chapter);

    public void updateUrl(Chapter chapter);

    public void del(String[] id);

}
