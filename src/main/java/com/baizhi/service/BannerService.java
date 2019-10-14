package com.baizhi.service;

import com.baizhi.entity.Banner;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String,Object> findAll(Integer page, Integer rows);
    public String save(Banner banner);
    public void delete(String[] id);
    public void update(Banner banner);
    public void updateStatus(Banner banner);
    public List<Banner> find(HttpServletResponse response) throws IOException;
}
