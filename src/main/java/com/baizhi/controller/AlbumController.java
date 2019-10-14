package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @RequestMapping("findByPage")
    public Map<String,Object> findAllByPage(Integer page,Integer rows){
        Map<String, Object> map = albumService.findAll(page, rows);
        return map;
    }


    @RequestMapping("edit")
    public String edit(Album album, String oper, HttpSession session, String[] id){
        if(oper.equals("add")){
            String s= albumService.save(album);
            return s;
        }else if (oper.equals("del")){
            albumService.delete(id);
        }else{
            albumService.updateScore(album);
        }
        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile cover, String albumId, HttpSession session){
        System.out.println(cover);
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename = cover.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        System.out.println(newFileName);
        try {
            cover.transferTo(new File(realPath,newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //修改图片的路径
        Album album=new Album();
        album.setCover(newFileName);
        album.setId(albumId);
        albumService.update(album);

    }

}
