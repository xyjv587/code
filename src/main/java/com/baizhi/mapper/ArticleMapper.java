package com.baizhi.mapper;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    public List<Article> queryAll(@Param("start") Integer start, @Param("rows") Integer rows);
    public Integer getCount();
    public void save(Article article);
    public void update(Article article);
}
