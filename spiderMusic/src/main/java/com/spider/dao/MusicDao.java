package com.spider.dao;

import com.spider.bean.MusicComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MusicDao //数据库插入接口类
{
    /**
     * 新增歌曲评论
     *
     * @param commentList
     * @return
     */
    int addMusicComment(@Param("commentList") List<MusicComment> commentList);
}
