package com.spider.pipeline;

import com.spider.bean.MusicComment;
import com.spider.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;
import java.util.Map;

@Component
public class Music163PipeLine implements Pipeline
{

    @Autowired
    private MusicService musicService;

    @Override
    public void process(ResultItems resultItems, Task task)//抽取爬取结果结果，调用musicService，保存到数据库
    {
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet())
        {
            if ("commentsList".equals(entry.getKey()))
            {
                List<MusicComment> commentList =  (List<MusicComment>) entry.getValue();
                if (commentList.size() < 0) {
                    return;
                }
                try {
                    musicService.addMusicComment(commentList);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("评论长度：" + commentList.size());
                }
            }

        }
    }
}
