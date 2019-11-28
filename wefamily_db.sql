/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-11-27 21:42:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tbl_alarm`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_alarm`;
CREATE TABLE `tbl_alarm` (
  `alarmId` int(30) NOT NULL AUTO_INCREMENT,
  `alarmTime` datetime DEFAULT NULL,
  `sendPersonId` varchar(30) DEFAULT NULL,
  `receivePersonId` varchar(30) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`alarmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_alarm
-- ----------------------------

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

-- ----------------------------
-- Table structure for `tbl_comment`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `postId` int(11) NOT NULL,
  `personId` varchar(30) NOT NULL,
  `content` varchar(200) NOT NULL,
  `time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_connect`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_connect`;
CREATE TABLE `tbl_connect` (
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `connectId` varchar(30) NOT NULL,
  `personId` varchar(30) NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_connect
-- ----------------------------

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
  `postId` int(30) NOT NULL,
  `goodPersonId` varchar(30) DEFAULT NULL,
  `publishPersonId` varchar(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`postId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_goodpost
-- ----------------------------

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
  `num` int(11) NOT NULL AUTO_INCREMENT,
  `attentionId` varchar(30) NOT NULL,
  `personId` varchar(30) NOT NULL,
  PRIMARY KEY (`num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------

-- ----------------------------
-- Table structure for `tbl_mycollection`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mycollection`;
CREATE TABLE `tbl_mycollection` (
  `phone` varchar(20) NOT NULL DEFAULT '',
  `postId` int(30) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------

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
  `sex` char(2) DEFAULT NULL,
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

-- ----------------------------
-- Table structure for `tbl_post`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) DEFAULT NULL,
  `personId` varchar(30) NOT NULL,
  `publishTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post
-- ----------------------------
INSERT INTO `tbl_post` VALUES ('1', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('2', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('3', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('4', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('5', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('6', '1', '1', '2019-08-02 02:30:09');
INSERT INTO `tbl_post` VALUES ('7', '123', '1', '2019-11-27 00:25:48');
INSERT INTO `tbl_post` VALUES ('8', '36', '1', '2019-11-27 01:00:51');

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
  `commentid` int(30) NOT NULL AUTO_INCREMENT,
  `postId` int(30) DEFAULT NULL,
  `replyPersonId` varchar(30) DEFAULT NULL,
  `commentPersonId` varchar(30) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  PRIMARY KEY (`commentid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reply_comment
-- ----------------------------

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