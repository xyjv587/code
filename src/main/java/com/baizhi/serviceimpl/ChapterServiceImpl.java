package com.baizhi.serviceimpl;

import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private AlbumMapper albumMapper;
    @Override
    public Map<String, Object> query(Integer page, Integer rows, String albumId) {
        Map<String,Object> map=new HashMap<>();
        Integer start=(page-1)*rows;
        List<Chapter> chapters = chapterMapper.queryPage(start, rows, albumId);
        Integer count=chapterMapper.count(albumId);
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("rows", chapters);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @Override
    public String save(Chapter chapter) {
        String s = UUID.randomUUID().toString();
        chapter.setId(s);
        chapter.setUpload_date(new Date());
        chapter.setTime("sdf");
        chapter.setSize("sss");
        System.out.println(chapter);
        chapterMapper.save(chapter);
        return s;
    }

    @Override
    public void updateUrl(Chapter chapter) {
        chapterMapper.updateUrl(chapter);
        int count = chapterMapper.count(chapter.getAlbum_id());
        Album album=new Album();
        album.setCount(count);
        album.setId(chapter.getAlbum_id());
        albumMapper.updateCount(album);
    }

    @Override
    public void del(String[] id) {
        chapterMapper.delete(id);
    }
}
