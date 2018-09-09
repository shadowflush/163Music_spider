package com.spider.service;

import com.spider.bean.MusicComment;
import com.spider.dao.MusicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicService //信息存储类
{

    @Autowired
    private MusicDao musicDao;


    public int addMusicComment(List<MusicComment> commentList) {
        return musicDao.addMusicComment(commentList);
    }
}
