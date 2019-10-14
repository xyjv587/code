package com.baizhi.controller;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    AlbumMapper albumMapper;
    @Autowired
    ChapterService chapterService;
    @RequestMapping("findByPage")
    public Map<String,Object> query(Integer page,Integer rows,String albumId){
        Map<String, Object> map= chapterService.query(page, rows,albumId);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper, String albumId, String[] id){
        if("add".equals(oper)){
            String s = chapterService.save(chapter);
            System.out.println("11111");
            System.out.println(chapter);
            System.out.println("22222");
            return s;
        }
        if("del".equals(oper)){
            chapterService.del(id);
            Integer count = chapterMapper.count(albumId);
            Album album = new Album();
            album.setId(albumId);
            album.setCount(count);
            albumMapper.updateCount(album);
        }

        return null;
    }

    @RequestMapping("upload")
    public void upload(MultipartFile video, String chapterId, String albumId, HttpSession session) throws IOException, TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException {
        String realPath = session.getServletContext().getRealPath("/audio");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }

        String originalFilename = video.getOriginalFilename();
        String newFileName=new Date().getTime()+"_"+originalFilename;

        video.transferTo(new File(realPath,newFileName));

        Chapter chapter = new Chapter();
        chapter.setId(chapterId);
        System.out.println(chapterId);
        chapter.setVideo(newFileName);
        System.out.println(newFileName);
        chapter.setAlbum_id(albumId);
        System.out.println(albumId);


        String realPath1 = session.getServletContext().getRealPath("/audio/"+newFileName);
        File file1 = new File(realPath1);
        //获取文件大小  单位是字节 byte
        long length = file1.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file1);
        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m=trackLength/60;
        //获取秒秒数
        Integer s=trackLength%60;

        //将文件大小强转成double类型
        double size=(double) length;

        //获取文件大小 单位是MB
        double ll=size/1024/1024;
        //取double小数点后两位  四舍五入
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);


        chapter.setSize(bg+"MB");
        chapter.setTime(m+"分"+s+"秒");

        chapterService.updateUrl(chapter);

    }

    @RequestMapping("download")
    public void download(String audioName, HttpSession session, HttpServletResponse response) throws IOException {
        String realPath = session.getServletContext().getRealPath("/audio");
        File file = new File(realPath, audioName);
        String s = audioName.split("_")[10];

        String encode = URLEncoder.encode(s,"UTF-8");
        String replace = encode.replace("+", "%20");
        System.out.println(replace);
        System.out.println(encode);
        response.setHeader("content-disposition","attachment;filename="+replace);
        FileUtils.copyFile(file,response.getOutputStream());

    }
}
