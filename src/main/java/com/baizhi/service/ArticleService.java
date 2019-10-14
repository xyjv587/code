package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<String,Object> queryAll(Integer page, Integer rows);
    public void save(Article article);
    public void update(Article article);
}
