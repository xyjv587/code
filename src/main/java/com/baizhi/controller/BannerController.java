package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    BannerService bannerService;

    @RequestMapping("findByPage")
    public Map<String, Object> findByPage(Integer page, Integer rows) {
        Map<String, Object> map = bannerService.findAll(page, rows);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Banner banner, String oper, HttpSession session,String[] id) {
        if (oper.equals("add")) {
            String s=bannerService.save(banner);
            return s;
        } else if (oper.equals("del")) {
            bannerService.delete(id);
        }else{
            bannerService.updateStatus(banner);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile img_path, String bannerId, HttpSession session){
        System.out.println(img_path);
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename = img_path.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        System.out.println(newFileName);
        try {
            img_path.transferTo(new File(realPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改图片的路径
        Banner banner=new Banner();
        banner.setImg_path(newFileName);
        banner.setId(bannerId);
        bannerService.update(banner);

    }
    @RequestMapping("show")
    public List<Banner> show(HttpServletResponse response) throws IOException {
        List<Banner> list = bannerService.find(response);
        return list;
    }
}
