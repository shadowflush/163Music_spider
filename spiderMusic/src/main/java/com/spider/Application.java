package com.spider;

import com.spider.pipeline.Music163PipeLine;
import com.spider.processer.Music163Processer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.component.HashSetDuplicateRemover;

@SpringBootApplication
@MapperScan("com.spider.dao")
@EnableScheduling
@EnableCaching
public class Application implements ApplicationRunner //主类，启动爬虫
{
    @Autowired
    private Music163PipeLine music163PipeLine;

    public static void main(String[] args)
    {
    	String url = "https://music.163.com/song?id=411214279";//爬取源 地址
        SpringApplication.run(Application.class, url);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		String[] url = args.getSourceArgs();
		System.out.println(url[0]);
		Spider.create(new Music163Processer()).addUrl(url[0])
    .setScheduler(new QueueScheduler().setDuplicateRemover(new HashSetDuplicateRemover()))
            .thread(10).addPipeline(music163PipeLine).run();
		return;
	}
}
