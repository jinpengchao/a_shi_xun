/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-05-19 17:33:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_admin`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_admin`;
CREATE TABLE `tbl_admin` (
  `admin_id` int(10) NOT NULL AUTO_INCREMENT,
  `admin_number` varchar(11) NOT NULL,
  `admin_password` varchar(18) NOT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `header_image` varchar(100) NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_admin
-- ----------------------------
INSERT INTO `tbl_admin` VALUES ('1', '1', '1', '朝爹', 'header15230867500.jpg');

-- ----------------------------
-- Table structure for `tbl_alarm`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_alarm`;
CREATE TABLE `tbl_alarm` (
  `alarmId` int(30) NOT NULL AUTO_INCREMENT,
  `alarmTime` varchar(30) DEFAULT NULL,
  `sendPersonId` varchar(30) DEFAULT NULL,
  `receivePersonId` varchar(30) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `clocktype` int(11) NOT NULL,
  PRIMARY KEY (`alarmId`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_alarm"
#

INSERT INTO `tbl_alarm` VALUES (3,'520:520','242424000','21321','8885208520',0),(5,'15:43','2424000','199906060','哈哈哈爸爸吧吧吧',0),(6,'05:00','792997','222222','1111111',0),(7,'15:08','792997','222222','2121213123123123123123123',0),(8,'13:06','792997','195412','1231231231231231231',0),(16,'07:30','9654782','195412','今天有活动，超市大减价',0),(26,'5:00','1111111','99663438','888',1),(32,'09:22','15194980385','18032168790','嗯',0),(35,'19:35','15194980385','15230867500','别展示了，下来吧',0),(36,'10:37','15194980385','18032168790','别展示了，下来吧',0),(37,'10:00','15194980385','18032168790','………',0),(38,'15:42','15194980385','jpcaa','6有6',0);

#
# Structure for table "tbl_child_userinfo"
#

DROP TABLE IF EXISTS `tbl_child_userinfo`;
CREATE TABLE `tbl_child_userinfo` (
  `phone` varchar(20) NOT NULL DEFAULT '',
  `id` varchar(30) DEFAULT NULL,
  `nickName` varchar(30) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `headerImg` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_child_userinfo
-- ----------------------------
INSERT INTO `tbl_child_userinfo` VALUES ('15194980385', '308462', '子女子女子女子女测测测测', 'female', '天国', 'header15194980385');

-- ----------------------------
-- Table structure for `tbl_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postId` int(11) NOT NULL,
  `personId` varchar(30) NOT NULL,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `content` varchar(200) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_comment"
#

INSERT INTO `tbl_comment` VALUES (11,79,'414223','怪兽马克','header57852687.jpg','速度发货','2019-12-16 06:29:52'),(12,82,'909874','托尼','header99663438.jpg','？？','2019-12-16 17:11:50'),(14,83,'275686','弗瑞','header6330654.jpg','牛啊','2019-12-16 18:53:59'),(15,83,'275686','弗瑞','header6330654.jpg','与君绝','2019-12-16 18:55:37'),(16,101,'909874','托尼','header99663438.jpg','哈哈哈','2019-12-18 17:05:46'),(17,101,'909874','托尼','header99663438.jpg','？。。','2019-12-18 17:05:52'),(18,68,'909874','托尼','header99663438.jpg','嘤嘤嘤','2019-12-18 17:31:35'),(19,102,'180321','嘻嘻哈哈','header18032168790.jpg','嘿嘿','2019-12-18 17:54:10'),(20,105,'275686','弗瑞','header6330654.jpg','没人赞我吗','2019-12-18 19:58:32'),(21,108,'180321','嘻嘻哈哈','header18032168790.jpg','啊啊啊','2019-12-19 10:37:59'),(22,109,'180321','嘻嘻哈哈','header18032168790.jpg','日尼玛','2020-04-08 09:54:36'),(23,109,'180321','嘻嘻哈哈','header18032168790.jpg','你是什么东西','2020-04-08 09:54:42'),(24,112,'180321','嘻嘻哈哈','header18032168790.jpg','的','2020-04-09 11:07:52'),(25,118,'','','','观后感','2020-04-13 19:51:39'),(26,119,'','','','哥哥','2020-04-13 20:38:26'),(27,118,'','','','bb','2020-04-14 11:34:47'),(28,121,'','','','股海护航','2020-04-14 20:57:36'),(29,122,'','','','？','2020-04-14 20:58:34'),(30,124,'293876','屎坦克','header18032168790.jpg','？','2020-04-14 21:06:24'),(31,124,'293876','屎坦克','header18032168790.jpg','哦哦哦','2020-04-14 21:06:27'),(32,125,'638880','1666','rc_default_portrait.png','哈哈哈','2020-04-15 10:42:04'),(33,125,'491602','屎','header15230867500','的','2020-04-15 16:17:18'),(34,126,'491602','屎','header15230867500','计算机','2020-04-19 16:28:43'),(35,129,'491602','屎','header15230867500','在你在家呢','2020-04-24 19:13:48'),(36,128,'491602','屎','header15230867500','日','2020-04-24 21:03:06'),(37,128,'491602','屎','header15230867500','ok','2020-04-24 21:03:10'),(38,128,'491602','屎','header15230867500','尼玛','2020-04-24 21:03:13'),(39,130,'491602','屎','header15230867500','山','2020-04-25 15:51:30'),(40,130,'491602','屎','header15230867500','安安啦','2020-04-25 15:51:33'),(41,156,'491602','屎','header15230867500','牛逼','2020-04-27 22:26:10');

#
# Structure for table "tbl_connect"
#

DROP TABLE IF EXISTS `tbl_connect`;
CREATE TABLE `tbl_connect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receivePhone` varchar(30) NOT NULL,
  `receiveName` varchar(30) NOT NULL,
  `sendPhone` varchar(30) NOT NULL,
  `sendName` varchar(30) NOT NULL,
  `setName` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_connect
-- ----------------------------
INSERT INTO `tbl_connect` VALUES ('33', '15230867500', '屎', '15194980385', '尿', '');
INSERT INTO `tbl_connect` VALUES ('34', '15230867500', '滚滚滚滚', '15230867500', '滚滚滚滚', '');
INSERT INTO `tbl_connect` VALUES ('35', '15230867500', '？睡觉睡觉大家', '15194980385', '改', '');

-- ----------------------------
-- Table structure for `tbl_goodcomment`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_goodcomment`;
CREATE TABLE `tbl_goodcomment` (
  `commantId` int(30) NOT NULL,
  `postId` int(30) NOT NULL,
  `goodPersonId` varchar(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`commantId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_goodcomment
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_goodpost`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_goodpost`;
CREATE TABLE `tbl_goodpost` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `postId` int(30) NOT NULL,
  `goodPersonId` varchar(30) DEFAULT NULL,
  `publishPersonId` varchar(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_goodpost
-- ----------------------------
INSERT INTO `tbl_goodpost` VALUES ('1', '64', '909874', '609387', '2019-12-11 14:21:54');
INSERT INTO `tbl_goodpost` VALUES ('2', '62', '909874', '609387', '2019-12-11 14:46:57');
INSERT INTO `tbl_goodpost` VALUES ('3', '66', '909874', '909874', '2019-12-11 17:53:51');
INSERT INTO `tbl_goodpost` VALUES ('4', '65', '909874', '909874', '2019-12-11 17:53:52');
INSERT INTO `tbl_goodpost` VALUES ('6', '63', '909874', '609387', '2019-12-12 17:25:09');
INSERT INTO `tbl_goodpost` VALUES ('7', '61', '909874', '609387', '2019-12-12 17:25:10');
INSERT INTO `tbl_goodpost` VALUES ('8', '60', '909874', '609387', '2019-12-12 17:25:11');
INSERT INTO `tbl_goodpost` VALUES ('9', '58', '909874', '1', '2019-12-12 17:25:15');
INSERT INTO `tbl_goodpost` VALUES ('10', '67', '909874', '909874', '2019-12-12 17:25:42');
INSERT INTO `tbl_goodpost` VALUES ('11', '59', '909874', '609387', '2019-12-12 19:05:43');
INSERT INTO `tbl_goodpost` VALUES ('13', '66', '909874', '909874', '2019-12-13 13:06:18');
INSERT INTO `tbl_goodpost` VALUES ('14', '67', '249984', '909874', '2019-12-13 14:28:23');
INSERT INTO `tbl_goodpost` VALUES ('15', '65', '249984', '909874', '2019-12-13 14:28:24');
INSERT INTO `tbl_goodpost` VALUES ('16', '68', '909874', '249984', '2019-12-14 10:58:03');
INSERT INTO `tbl_goodpost` VALUES ('17', '76', '165196', '165196', '2019-12-15 12:59:45');
INSERT INTO `tbl_goodpost` VALUES ('18', '75', '414223', '165196', '2019-12-15 13:27:57');
INSERT INTO `tbl_goodpost` VALUES ('19', '77', '414223', '414223', '2019-12-15 13:28:16');
INSERT INTO `tbl_goodpost` VALUES ('20', '78', '917106', '414223', '2019-12-16 01:22:42');
INSERT INTO `tbl_goodpost` VALUES ('21', '82', '909874', '909874', '2019-12-16 17:12:02');
INSERT INTO `tbl_goodpost` VALUES ('22', '83', '909874', '275686', '2019-12-16 19:04:24');
INSERT INTO `tbl_goodpost` VALUES ('23', '92', '275686', '275686', '2019-12-17 10:31:11');
INSERT INTO `tbl_goodpost` VALUES ('24', '90', '99663438', '909874', '2019-12-17 16:36:30');
INSERT INTO `tbl_goodpost` VALUES ('25', '88', '99663438', '275686', '2019-12-17 16:36:31');
INSERT INTO `tbl_goodpost` VALUES ('26', '65', '99663438', '909874', '2019-12-17 16:36:34');
INSERT INTO `tbl_goodpost` VALUES ('27', '62', '99663438', '609387', '2019-12-17 16:36:36');
INSERT INTO `tbl_goodpost` VALUES ('28', '92', '99663438', '275686', '2019-12-17 16:36:43');
INSERT INTO `tbl_goodpost` VALUES ('29', '87', '909874', '275686', '2019-12-18 11:08:00');
INSERT INTO `tbl_goodpost` VALUES ('30', '88', '909874', '275686', '2019-12-18 11:08:01');
INSERT INTO `tbl_goodpost` VALUES ('31', '96', '909874', '909874', '2019-12-18 14:02:15');
INSERT INTO `tbl_goodpost` VALUES ('32', '96', '909874', '909874', '2019-12-18 14:31:42');
INSERT INTO `tbl_goodpost` VALUES ('33', '101', '180321', '909874', '2019-12-18 16:56:40');
INSERT INTO `tbl_goodpost` VALUES ('35', '68', '909874', '249984', '2019-12-18 17:31:30');
INSERT INTO `tbl_goodpost` VALUES ('36', '102', '180321', '180321', '2019-12-18 17:37:14');
INSERT INTO `tbl_goodpost` VALUES ('37', '103', '909874', '909874', '2019-12-18 18:04:54');
INSERT INTO `tbl_goodpost` VALUES ('38', '101', '180321', '909874', '2019-12-18 19:04:03');
INSERT INTO `tbl_goodpost` VALUES ('40', '103', '180321', '909874', '2019-12-18 19:04:27');
INSERT INTO `tbl_goodpost` VALUES ('41', '101', '180321', '909874', '2019-12-18 19:04:28');
INSERT INTO `tbl_goodpost` VALUES ('42', '105', '275686', '275686', '2019-12-18 19:58:35');
INSERT INTO `tbl_goodpost` VALUES ('43', '106', '180321', '180321', '2019-12-18 22:49:59');
INSERT INTO `tbl_goodpost` VALUES ('44', '108', '180321', '180321', '2019-12-19 10:37:54');
INSERT INTO `tbl_goodpost` VALUES ('56', '109', '180321', '180321', '2020-04-08 09:55:17');
INSERT INTO `tbl_goodpost` VALUES ('57', '118', '', '180321', '2020-04-13 19:51:41');
INSERT INTO `tbl_goodpost` VALUES ('58', '119', '', '', '2020-04-13 20:44:56');
INSERT INTO `tbl_goodpost` VALUES ('59', '121', '', '', '2020-04-14 15:56:03');
INSERT INTO `tbl_goodpost` VALUES ('60', '123', '', '180321', '2020-04-14 20:57:22');
INSERT INTO `tbl_goodpost` VALUES ('61', '122', '', '', '2020-04-14 20:58:34');
INSERT INTO `tbl_goodpost` VALUES ('62', '123', '293876', '180321', '2020-04-14 21:05:37');
INSERT INTO `tbl_goodpost` VALUES ('63', '122', '293876', '', '2020-04-14 21:05:38');
INSERT INTO `tbl_goodpost` VALUES ('64', '120', '293876', '', '2020-04-14 21:05:49');
INSERT INTO `tbl_goodpost` VALUES ('65', '124', '293876', '293876', '2020-04-14 21:06:28');
INSERT INTO `tbl_goodpost` VALUES ('66', '125', '638880', '638880', '2020-04-15 10:42:00');
INSERT INTO `tbl_goodpost` VALUES ('68', '126', '491602', '', '2020-04-19 16:28:59');
INSERT INTO `tbl_goodpost` VALUES ('69', '127', '491602', '491602', '2020-04-19 16:29:15');
INSERT INTO `tbl_goodpost` VALUES ('71', '129', '491602', '', '2020-04-24 20:37:29');
INSERT INTO `tbl_goodpost` VALUES ('72', '128', '491602', '', '2020-04-24 20:37:32');
INSERT INTO `tbl_goodpost` VALUES ('73', '125', '491602', '638880', '2020-04-24 20:37:35');
INSERT INTO `tbl_goodpost` VALUES ('74', '157', '491602', '491602', '2020-04-29 13:38:47');
INSERT INTO `tbl_goodpost` VALUES ('75', '156', '491602', '491602', '2020-04-29 13:38:51');
INSERT INTO `tbl_goodpost` VALUES ('76', '151', '491602', '491602', '2020-04-29 13:38:54');
INSERT INTO `tbl_goodpost` VALUES ('77', '148', '491602', '491602', '2020-04-29 13:38:57');
INSERT INTO `tbl_goodpost` VALUES ('78', '153', '491602', '491602', '2020-04-29 13:39:01');
INSERT INTO `tbl_goodpost` VALUES ('79', '154', '491602', '491602', '2020-04-30 14:11:41');
INSERT INTO `tbl_goodpost` VALUES ('81', '155', '491602', '491602', '2020-04-30 14:11:43');
INSERT INTO `tbl_goodpost` VALUES ('82', '145', '852000', '852000', '2020-05-06 22:14:12');
INSERT INTO `tbl_goodpost` VALUES ('83', '166', '491602', '852000', '2020-05-07 08:17:17');
INSERT INTO `tbl_goodpost` VALUES ('91', '167', '491602', '852000', '2020-05-11 17:52:34');

-- ----------------------------
-- Table structure for `tbl_healthhouse`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_healthhouse`;
CREATE TABLE `tbl_healthhouse` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `publishTime` datetime DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `resource` varchar(30) DEFAULT NULL,
  `viewImg` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_healthhouse
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_myachievement`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_myachievement`;
CREATE TABLE `tbl_myachievement` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `achieve` int(11) DEFAULT NULL,
  `achieveName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myachievement
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_myattentions`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_myattentions`;
CREATE TABLE `tbl_myattentions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `attentionPersonId` varchar(30) NOT NULL,
  `personId` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_myattentions"
#
-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('47', '852000', '491602');
#
# Structure for table "tbl_mycollection"
#

-- ----------------------------
-- Table structure for `tbl_mycollection`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mycollection`;
CREATE TABLE `tbl_mycollection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` varchar(20) NOT NULL DEFAULT '',
  `postId` int(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------
INSERT INTO `tbl_mycollection` VALUES ('131', '491602', '165', '2020-05-10 22:25:45');
INSERT INTO `tbl_mycollection` VALUES ('133', '491602', '167', '2020-05-11 17:52:24');

-- ----------------------------
-- Table structure for `tbl_news`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_news`;
CREATE TABLE `tbl_news` (
  `id` varchar(30) NOT NULL,
  `publishTime` datetime DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `resource` varchar(50) DEFAULT NULL,
  `viewImg` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_news
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_parent_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_parent_userinfo`;
CREATE TABLE `tbl_parent_userinfo` (
  `phone` varchar(20) NOT NULL,
  `id` varchar(30) NOT NULL,
  `nickName` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `birthday` varchar(50) DEFAULT NULL,
  `achieve` int(11) DEFAULT NULL,
  `personalWord` varchar(255) DEFAULT NULL,
  `headimg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`phone`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tbl_parent_userinfo"
#

INSERT INTO `tbl_parent_userinfo` VALUES ('13513171332','254870','发货','','','0',0,'','header13513171332'),('15227856991','852000','mon','female','阿斯顿','1970-10-10',0,NULL,'header15227856991'),('15230867500','491602','屎','male','美国','0',0,'','header15230867500');

#
# Structure for table "tbl_post"
#

DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL,
  `headimg` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `personId` varchar(30) CHARACTER SET utf8mb4 NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_post"
#

INSERT INTO `tbl_post` VALUES (159,'屎','header15230867500','大酒店','491602','2020-04-27 20:58:00','temp_photo1587992280173.jpg'),(160,'mon','header15227856991','拉去','852000','2020-05-17 16:00:31','d48fd9371a76177881af003aaf912bc5.jpg\",\"764f9ec150b5801a314f1c98b10cd29e.mp4'),(161,'mon','header15227856991','拍照','852000','2020-05-17 14:52:31','IMG_20200517_14521749.jpg');

-- ----------------------------
-- Records of tbl_posts
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_questions`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_questions`;
CREATE TABLE `tbl_questions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(11) NOT NULL,
  `theme` varchar(20) CHARACTER SET utf8 NOT NULL,
  `content` varchar(200) CHARACTER SET utf8 NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_questions
-- ----------------------------
INSERT INTO `tbl_questions` VALUES ('1', '姬文斌', '13300000000', '关于获奖资金分配问题', '我自愿退出分配奖金，希望大家同意，跪下磕头了给你们', '1');
INSERT INTO `tbl_questions` VALUES ('2', '姬豆豆', '15200000000', '关于自觉问题', '姬文斌十一点起，十一点睡，该治治了', '1');
INSERT INTO `tbl_questions` VALUES ('3', '姬小生', '15500000000', '关于哈哈', '靳文斌', '0');
INSERT INTO `tbl_questions` VALUES ('4', '姬妹妹', '15202020220', '管你呢', '嗯嗯嗯嗯呃', '0');

DROP TABLE IF EXISTS `tbl_post_copy`;
CREATE TABLE `tbl_post_copy` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `personId` varchar(30) NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(1000) DEFAULT NULL,
  `examine` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_post_copy"
#

INSERT INTO `tbl_post_copy` VALUES (183,'mon','header15227856991','加图删一个','852000','2020-05-17 14:51:55','200856152244627_2.jpg','已审核'),(184,'mon','header15227856991','拍照','852000','2020-05-17 14:52:31','IMG_20200517_14521749.jpg','已审核'),(186,'mon','header15227856991','删一个视频','852000','2020-05-17 14:54:17','OIP.2xOrqtuh-c3BS4RVlKwQfQHaNK_pid\\u003dApi\\u0026dpr\\u003d2.jpg','审核失败'),(187,'mon','header15227856991','拉去','852000','2020-05-17 16:00:31','d48fd9371a76177881af003aaf912bc5.jpg\",\"764f9ec150b5801a314f1c98b10cd29e.mp4','已审核');

#
# Structure for table "tbl_post_report"
#

DROP TABLE IF EXISTS `tbl_post_report`;
CREATE TABLE `tbl_post_report` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `personId` varchar(30) NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;

#
# Data for table "tbl_post_report"
#

INSERT INTO `tbl_post_report` VALUES (154,'屎','header15230867500','上','491602','2020-04-27 21:18:29','temp_photo1587992315661.jpg');

#
# Structure for table "tbl_posts"
#

DROP TABLE IF EXISTS `tbl_posts`;
CREATE TABLE `tbl_posts` (
  `num` int(11) NOT NULL DEFAULT '0',
  `id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "tbl_posts"
#


#
# Structure for table "tbl_relations_request"
#

-- ----------------------------
-- Table structure for `tbl_relations_request`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_relations_request`;
CREATE TABLE `tbl_relations_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `send_phone` varchar(11) NOT NULL,
  `send_name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `receive_phone` varchar(11) NOT NULL,
  `type` varchar(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_relations_request
-- ----------------------------
INSERT INTO `tbl_relations_request` VALUES ('2', '15194980385', '尿', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('3', '123456789', '尿', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('4', '15230867500', '屎', '15194980385', '-1');
INSERT INTO `tbl_relations_request` VALUES ('5', '555555', '5', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('6', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('7', '15230867500', '滚滚滚滚', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('8', '15194980385', '改', '15230867500', '1');

-- ----------------------------
-- Table structure for `tbl_remind`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_remind`;
CREATE TABLE `tbl_remind` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `phone` char(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_remind
-- ----------------------------
INSERT INTO `tbl_remind` VALUES ('1', 'hahah', '242424000');
INSERT INTO `tbl_remind` VALUES ('2', '55555', '242424000');
INSERT INTO `tbl_remind` VALUES ('12', '超长测试qqqqqqqqqqqqqqqqqqqqqqqqqqqq', '15194980385');

-- ----------------------------
-- Table structure for `tbl_reply_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_reply_comment`;
CREATE TABLE `tbl_reply_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentId` int(30) NOT NULL,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `personId` varchar(30) DEFAULT NULL,
  `replyTotal` int(11) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tbl_step`;
CREATE TABLE `tbl_step` (
  `parentId` varchar(30) NOT NULL DEFAULT '',
  `step` varchar(20) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_step
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_user`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `phone` varchar(20) NOT NULL,
  `password` varchar(30) NOT NULL,
  `registerTime` datetime NOT NULL,
  `id` varchar(30) NOT NULL,
  `wechat` varchar(30) DEFAULT NULL,
  `qq` varchar(30) DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`phone`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('13513171332', '123456', '2020-04-28 21:56:36', '254870', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15194980385', '123456', '2019-12-03 15:29:25', '658943', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15227856991', '654321', '2020-02-15 16:03:24', '852000', null, null, '0');
INSERT INTO `tbl_user` VALUES ('15230867500', '123456', '2019-10-15 16:03:24', '491602', '', '', '0');
INSERT INTO `tbl_user` VALUES ('kefuzhanghao', 'wanjpc123', '0000-00-00 00:00:00', '000000', null, null, '0');

-- ----------------------------
-- Table structure for `tbl_walkpath`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_walkpath`;
CREATE TABLE `tbl_walkpath` (
  `id` varchar(30) NOT NULL DEFAULT '',
  `time` datetime DEFAULT NULL,
  `img` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_walkpath
-- ----------------------------
#
# Data for table "tbl_walkpath"
#
