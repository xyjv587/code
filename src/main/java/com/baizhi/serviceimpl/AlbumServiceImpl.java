package com.baizhi.serviceimpl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    AlbumMapper albumMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Map<String, Object> map1 = new HashMap<>();
        Integer start = (page - 1) * rows;
        List<Album> list = albumMapper.findAll(start, rows);
        Integer count = albumMapper.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        map1.put("rows", list);
        map1.put("records", count);
        map1.put("page", page);
        map1.put("total", total);
        return map1;
    }

    @Override
    public String save(Album album) {
        String s = UUID.randomUUID().toString();
        album.setId(s);
        albumMapper.insert(album);
        return s;
    }

    @Override
    public void delete(String[] id) {
        albumMapper.del(id);
    }

    @Override
    public void update(Album album) {
        albumMapper.updateUrl(album);
    }

    @Override
    public void updateScore(Album album) {
        albumMapper.updateScore(album);
    }
}
