package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    public Map<String,Object> findAll(Integer page, Integer rows);
    public String save(Album album);
    public void delete(String[] id);
    public void update(Album album);
    public void updateScore(Album album);
}
