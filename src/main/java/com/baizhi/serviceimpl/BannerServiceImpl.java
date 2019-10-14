package com.baizhi.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Banner;
import com.baizhi.mapper.BannerMapper;
import com.baizhi.service.BannerService;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerMapper bannerMapper;
    @Override
    public Map<String,Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        Integer start = (page - 1) * rows;
        List<Banner> list = bannerMapper.findAll(start, rows);
        Integer count = bannerMapper.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("rows", list);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    public String save(Banner banner) {
        String s = UUID.randomUUID().toString();
        banner.setId(s);
        bannerMapper.save(banner);
        return s;
    }

    @Override
    public void delete(String[] id) {
        bannerMapper.del(id);
    }

    @Override
    public void update(Banner banner) {
        bannerMapper.update(banner);
    }

    @Override
    public void updateStatus(Banner banner) {
        bannerMapper.updateStatus(banner);
    }

    @Override
    public List<Banner> find(HttpServletResponse response) throws IOException {
        List<Banner> all = bannerMapper.getAll();
        for (Banner banner:all) {
            banner.setImg_path("F:\\idea\\code\\cmfz\\src\\main\\webapp\\img\\"+banner.getImg_path());
            }
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图","轮播图"),Banner.class,all);
        String encode = URLEncoder.encode("轮播图信息.xls","UTF-8");
        response.setHeader("content-disposition","attachment;filename="+encode);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        return null;
    }
}

