/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-12-13 17:47:09

*/

SET FOREIGN_KEY_CHECKS=0;

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_alarm
-- ----------------------------
INSERT INTO `tbl_alarm` VALUES ('1', '16:04', '123456', '99663438', '++++++', '1');
INSERT INTO `tbl_alarm` VALUES ('2', '15:51', '8520', '99663438', '*****', '1');
INSERT INTO `tbl_alarm` VALUES ('3', '520:520', '2561', '21321', '8885208520', '0');
INSERT INTO `tbl_alarm` VALUES ('4', '17:24', '85208520', '99663438', '12嗯嗯 ', '1');
INSERT INTO `tbl_alarm` VALUES ('5', '15:43', '8888888', '199906060', '哈哈哈爸爸吧吧吧', '1');

-- ----------------------------
-- Table structure for `tbl_child_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_child_userinfo`;
CREATE TABLE `tbl_child_userinfo` (
  `phone` varchar(20) NOT NULL DEFAULT '',
  `id` varchar(30) DEFAULT NULL,
  `nickName` varchar(30) DEFAULT NULL,
  `sex` char(2) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `headerImg` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_child_userinfo
-- ----------------------------
INSERT INTO `tbl_child_userinfo` VALUES ('15199980888', '792997', 'qwqw', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('542400', '840875', '542400', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('8885201', '550707', '浮点数是的', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('996521', '414292', '请问', '', '', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_comment
-- ----------------------------
INSERT INTO `tbl_comment` VALUES ('1', '61', '1', '1', 'sss.jpg', '第一条评论', '2019-12-10 08:57:38');
INSERT INTO `tbl_comment` VALUES ('2', '61', '1', '1', 'sss.jpg', '第二条评论', '2019-12-10 08:57:48');
INSERT INTO `tbl_comment` VALUES ('3', '61', '1', '1', 'sss.jpg', '大佬hi来了', '2019-12-10 13:27:41');
INSERT INTO `tbl_comment` VALUES ('4', '62', '1', '1', 'sss.jpg', '好', '2019-12-11 00:26:03');
INSERT INTO `tbl_comment` VALUES ('5', '62', '1', '1', 'sss.jpg', '加一条', '2019-12-11 00:31:03');
INSERT INTO `tbl_comment` VALUES ('6', '62', '1', '1', 'sss.jpg', '修改', '2019-12-11 00:31:17');
INSERT INTO `tbl_comment` VALUES ('7', '63', '1', '1', 'sss.jpg', '测试评论', '2019-12-11 00:34:07');
INSERT INTO `tbl_comment` VALUES ('8', '63', '1', '1', 'sss.jpg', '赶赶', '2019-12-11 00:48:29');
INSERT INTO `tbl_comment` VALUES ('9', '63', '1', '1', 'sss.jpg', '神凭', '2019-12-11 00:58:44');
INSERT INTO `tbl_comment` VALUES ('10', '60', '1', '1', 'sss.jpg', '哦ing了', '2019-12-11 01:03:22');
INSERT INTO `tbl_comment` VALUES ('11', '60', '1', '1', 'sss.jpg', '再来', '2019-12-11 01:03:31');
INSERT INTO `tbl_comment` VALUES ('12', '60', '1', '1', 'sss.jpg', '师傅', '2019-12-11 01:08:15');
INSERT INTO `tbl_comment` VALUES ('13', '63', '1', '1', 'sss.jpg', '电饭锅', '2019-12-11 01:08:29');
INSERT INTO `tbl_comment` VALUES ('14', '59', '1', '1', 'sss.jpg', '胡歌', '2019-12-11 01:11:23');
INSERT INTO `tbl_comment` VALUES ('15', '1', '383098', '一个', '', '评论', '2019-12-11 07:03:15');

-- ----------------------------
-- Table structure for `tbl_connect`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_connect`;
CREATE TABLE `tbl_connect` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `receivePhone` varchar(30) NOT NULL,
  `sendPhone` varchar(30) NOT NULL,
  `receiveType` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_connect
-- ----------------------------
INSERT INTO `tbl_connect` VALUES ('1', '996663438', 'a101088', '0');
INSERT INTO `tbl_connect` VALUES ('2', '542400', '99663438', '1');
INSERT INTO `tbl_connect` VALUES ('3', '199906060', '996663438', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Records of tbl_goodpost
-- ----------------------------

INSERT INTO `tbl_goodpost` VALUES ('1', '64', '909874', '609387', '2019-12-11 14:21:54');
INSERT INTO `tbl_goodpost` VALUES ('2', '62', '909874', '609387', '2019-12-11 14:46:57');
INSERT INTO `tbl_goodpost` VALUES ('3', '66', '909874', '909874', '2019-12-11 17:53:51');
INSERT INTO `tbl_goodpost` VALUES ('4', '65', '909874', '909874', '2019-12-11 17:53:52');
INSERT INTO `tbl_goodpost` VALUES ('5', '65', '', '909874', '2019-12-12 11:24:41');
INSERT INTO `tbl_goodpost` VALUES ('6', '63', '909874', '609387', '2019-12-12 17:25:09');
INSERT INTO `tbl_goodpost` VALUES ('7', '61', '909874', '609387', '2019-12-12 17:25:10');
INSERT INTO `tbl_goodpost` VALUES ('8', '60', '909874', '609387', '2019-12-12 17:25:11');
INSERT INTO `tbl_goodpost` VALUES ('9', '58', '909874', '1', '2019-12-12 17:25:15');
INSERT INTO `tbl_goodpost` VALUES ('10', '67', '909874', '909874', '2019-12-12 17:25:42');
INSERT INTO `tbl_goodpost` VALUES ('11', '59', '909874', '609387', '2019-12-12 19:05:43');
INSERT INTO `tbl_goodpost` VALUES ('12', '67', '', '909874', '2019-12-13 12:25:52');
INSERT INTO `tbl_goodpost` VALUES ('13', '66', '909874', '909874', '2019-12-13 13:06:18');
INSERT INTO `tbl_goodpost` VALUES ('14', '67', '249984', '909874', '2019-12-13 14:28:23');
INSERT INTO `tbl_goodpost` VALUES ('15', '65', '249984', '909874', '2019-12-13 14:28:24');


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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('5', '383098', '489147');

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
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------
INSERT INTO `tbl_mycollection` VALUES ('89', '1', '60', '2019-12-09 07:31:20');
INSERT INTO `tbl_mycollection` VALUES ('90', '1', '59', '2019-12-09 07:31:38');
INSERT INTO `tbl_mycollection` VALUES ('91', '1', '62', '2019-12-10 13:29:08');
INSERT INTO `tbl_mycollection` VALUES ('92', '909874', '63', '2019-12-11 08:23:06');
INSERT INTO `tbl_mycollection` VALUES ('93', '909874', '62', '2019-12-11 08:23:07');

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
  `happy` int(11) DEFAULT NULL,
  `achieve` int(11) DEFAULT NULL,
  `personalWord` varchar(50) DEFAULT NULL,
  `headimg` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`phone`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_parent_userinfo
-- ----------------------------
INSERT INTO `tbl_parent_userinfo` VALUES ('', '546272', '', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('1', '1', '1', '男', '1', null, '2', '可以', 'sss.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('110110110', '877145', '衰呙', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('15227856991', '948878', '一曲离殇', '男', '石家庄', null, '100', '撒也不会', 'head.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('15513155225', '195412', '史泰龙', '男', '石家庄', null, '100', '今晚必须死', 'sss.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('19198080', '796031', '阿斯顿', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('199906060', '249984', '阿达安市安市', 'female', '安徽省-阜阳市-阜南县', '0', '0', '', 'header199906060.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('43843866', '609387', '阿达啊', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('9866521', '109156', '哈哈', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('987654222438', '201977', '123123', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('99442205', '863018', '232.3', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('9963438', '231042', '靳朋朝', '', '安徽省-黄山市-黄山区', '0', '0', '', 'header1576025129320.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('99663438', '909874', '4381111安市', 'male', '北京市-北京市-昌平区', '0', '0', '', 'header99663438.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('996663438', '254653', '996663438', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('31415926', '954572', '离殇', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('31415936', '489147', '篇幅', '', '', '0', '0', '', 'header1576050406115.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('911911', '383098', '扁舟', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('998877', '503117', '蜀黍', '', '', '0', '0', '', '');

-- ----------------------------
-- Table structure for `tbl_post`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `personId` varchar(30) NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post
-- ----------------------------
INSERT INTO `tbl_post` VALUES ('49', '一曲离殇', 'head.jpg', '初雪清凉', '948878', '2019-12-08 04:31:00', '[\"temp_photo1575779457996.jpg\"]');
INSERT INTO `tbl_post` VALUES ('50', '一曲离殇', 'head.jpg', '没图', '948878', '2019-12-08 04:31:12', '[]');
INSERT INTO `tbl_post` VALUES ('51', '一曲离殇', 'head.jpg', '拍照', '948878', '2019-12-08 04:31:41', '[\"temp_photo1575779496004.jpg\"]');
INSERT INTO `tbl_post` VALUES ('54', '1', 'sss.jpg', '回电话给', '1', '2019-12-08 07:01:26', '[]');
INSERT INTO `tbl_post` VALUES ('58', '1', 'sss.jpg', '有的', '1', '2019-12-08 08:56:51', '[\"temp_photo1575795403804.jpg\"]');
INSERT INTO `tbl_post` VALUES ('59', '阿达啊', '', 'cnm', '609387', '2019-12-09 15:44:57', '[\"temp_photo1575877492406.jpg\"]');
INSERT INTO `tbl_post` VALUES ('60', '阿达啊', '', '8888888', '609387', '2019-12-09 15:49:11', '[\"temp_photo1575877746088.jpg\"]');
INSERT INTO `tbl_post` VALUES ('61', '阿达啊', '', 'okhtttp', '609387', '2019-12-09 15:50:21', '[\"temp_photo1575877785485.jpg\"]');
INSERT INTO `tbl_post` VALUES ('62', '阿达啊', '', '11212', '609387', '2019-12-09 16:02:41', '[\"temp_photo1575878559995.jpg\"]');
INSERT INTO `tbl_post` VALUES ('63', '阿达啊', '', '23123 12', '609387', '2019-12-09 16:07:26', '[]');
INSERT INTO `tbl_post` VALUES ('64', '阿达啊', '', '12312312的说法', '609387', '2019-12-09 16:07:40', '[]');
INSERT INTO `tbl_post` VALUES ('65', '驱蚊器', '', '？？？？？？？？？？', '909874', '2019-12-10 08:56:40', '[]');
INSERT INTO `tbl_post` VALUES ('66', '20205555', 'header1575981189195.jpg', '？？？？？？？', '909874', '2019-12-10 20:33:45', '[]');
INSERT INTO `tbl_post` VALUES ('67', '438', 'header99663438.jpg', '草你妈的', '909874', '2019-12-12 17:25:39', '[\"temp_photo1576142734885.jpg\"]');

-- ----------------------------
-- Table structure for `tbl_posts`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_posts`;
CREATE TABLE `tbl_posts` (
  `num` int(11) NOT NULL DEFAULT '0',
  `id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_posts
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_remind`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_remind`;
CREATE TABLE `tbl_remind` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_remind
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_reply_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_reply_comment`;
CREATE TABLE `tbl_reply_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commentId` int(30) NOT NULL,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `PersonId` varchar(30) DEFAULT NULL,
  `replyTotal` int(11) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reply_comment
-- ----------------------------
INSERT INTO `tbl_reply_comment` VALUES ('1', '1', '1', 'sss.jpg', '1', '0', '第一条回复', '2019-12-10 12:52:05');

-- ----------------------------
-- Table structure for `tbl_step`
-- ----------------------------
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
INSERT INTO `tbl_user` VALUES ('110110110', '654321', '2019-12-11 07:37:11', '877145', '', '', '0');
INSERT INTO `tbl_user` VALUES ('11111111111', '111', '2019-12-03 15:29:25', '111111', null, null, '0');
INSERT INTO `tbl_user` VALUES ('12122', '11111', '2019-12-09 15:26:57', '609387', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15199980888', '1', '2019-12-04 10:56:41', '792997', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15227856991', '123', '2019-12-03 10:26:52', '948878', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15230145956', 'zhangpeng002', '2019-12-10 08:38:54', '151190', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15230867500', 'qqq123', '2019-12-03 10:27:52', '385206', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15513155225', 'qqq123', '2019-12-03 15:29:25', '658943', '', '', '0');
INSERT INTO `tbl_user` VALUES ('19198080', '1234', '2019-12-11 15:41:12', '796031', '', '', '0');
INSERT INTO `tbl_user` VALUES ('199906060', '11111', '2019-12-13 14:11:54', '249984', '', '', '0');
INSERT INTO `tbl_user` VALUES ('199908177', '11111', '0000-00-00 00:00:00', '', null, null, '1');
INSERT INTO `tbl_user` VALUES ('2', '2', '0000-00-00 00:00:00', '2', null, null, '1');
INSERT INTO `tbl_user` VALUES ('542400', '11111', '2019-12-13 08:16:40', '840875', '', '', '1');
INSERT INTO `tbl_user` VALUES ('8885201', '11111', '2019-12-12 17:27:51', '550707', '', '', '1');
INSERT INTO `tbl_user` VALUES ('9696438', '555555555', '2019-12-09 20:14:06', '114607', '', '', '0');
INSERT INTO `tbl_user` VALUES ('9866521', '1111', '2019-12-10 08:48:24', '109156', '', '', '0');
INSERT INTO `tbl_user` VALUES ('987438', '11111111', '2019-12-09 19:59:06', '955244', '', '', '0');
INSERT INTO `tbl_user` VALUES ('987654222438', '11111111', '2019-12-09 20:24:27', '201977', '', '', '0');
INSERT INTO `tbl_user` VALUES ('99442205', '11111', '2019-12-11 15:41:53', '863018', '', '', '0');
INSERT INTO `tbl_user` VALUES ('9963438', '11111111', '2019-12-11 08:43:34', '231042', '', '', '0');
INSERT INTO `tbl_user` VALUES ('996521', '11111', '2019-12-12 20:24:59', '414292', '', '', '1');
INSERT INTO `tbl_user` VALUES ('996633438', '11111', '2019-12-11 20:13:52', '546272', '', '', '0');
INSERT INTO `tbl_user` VALUES ('99663438', '111111', '2019-12-09 20:25:20', '909874', '', '', '0');
INSERT INTO `tbl_user` VALUES ('996663438', '11111', '2019-12-12 21:00:02', '254653', '', '', '0');

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
