# spiderMusic
爬取网易云音乐评论
该项目采用**springboot**+**mybatis**+**redis**+**webMagic**爬取网易云音乐评论。<br>
该项目默认启动时会对爬取网易云音乐评论进行一次爬取。<br>
# 如何配置：
### 1.下载源码
### 2.初始化数据库相关信息
    找到src/main/DB/DB.sql,进入mysql执行文件.
### 3.修改springboot的相关配置：
    找到 resources/application.yml  配置数据库相关信息 redis的端口地址密码
### 4.启动项目：
	Eclipse 建立Maven Project 导入下载的源码，运行即可，爬取结果保存至数据库
