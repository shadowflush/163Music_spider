/*
SQLyog Community v12.2.4 (64 bit)
MySQL - 5.7.19 : Database - spiderhouse
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spiderhouse` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `spiderhouse`;

/*Table structure for table `musci_comment` */

DROP TABLE IF EXISTS `music_comment`;

CREATE TABLE `music_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `songId` int(11) DEFAULT NULL COMMENT '歌曲id',
  `nickname` varchar(60) COLLATE utf8_bin DEFAULT NULL COMMENT '评论人姓名',
  `likedCount` int(20) DEFAULT NULL COMMENT '赞数',
  `content` text COLLATE utf8_bin COMMENT '评论内容',
  `time` datetime DEFAULT NULL COMMENT '评论日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6006 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
