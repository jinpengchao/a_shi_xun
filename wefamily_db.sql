/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-04-19 16:11:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `asd`
-- ----------------------------
DROP TABLE IF EXISTS `asd`;
CREATE TABLE `asd` (
  `asd` varchar(20) NOT NULL,
  `as` int(11) NOT NULL,
  PRIMARY KEY (`asd`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of asd
-- ----------------------------
INSERT INTO `asd` VALUES ('按时大大', '0');

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

-- ----------------------------
-- Records of tbl_alarm
-- ----------------------------
INSERT INTO `tbl_alarm` VALUES ('3', '520:520', '242424000', '21321', '8885208520', '0');
INSERT INTO `tbl_alarm` VALUES ('5', '15:43', '2424000', '199906060', '哈哈哈爸爸吧吧吧', '0');
INSERT INTO `tbl_alarm` VALUES ('6', '05:00', '792997', '222222', '1111111', '0');
INSERT INTO `tbl_alarm` VALUES ('7', '15:08', '792997', '222222', '2121213123123123123123123', '0');
INSERT INTO `tbl_alarm` VALUES ('8', '13:06', '792997', '195412', '1231231231231231231', '0');
INSERT INTO `tbl_alarm` VALUES ('12', '05:00', '242424000', '195412', '******************', '0');
INSERT INTO `tbl_alarm` VALUES ('15', '05:00', '242424000', '222222', '啊飒飒 阿斯顿', '0');
INSERT INTO `tbl_alarm` VALUES ('16', '07:30', '9654782', '195412', '今天有活动，超市大减价', '0');
INSERT INTO `tbl_alarm` VALUES ('26', '5:00', '1111111', '99663438', '888', '1');
INSERT INTO `tbl_alarm` VALUES ('32', '09:22', '15194980385', '18032168790', '嗯', '0');
INSERT INTO `tbl_alarm` VALUES ('35', '10:37', '15194980385', '6330654', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('36', '10:37', '15194980385', '18032168790', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('37', '10:00', '15194980385', '18032168790', '………', '0');
INSERT INTO `tbl_alarm` VALUES ('38', '15:42', '15194980385', 'jpcaa', '6有6', '0');

-- ----------------------------
-- Table structure for `tbl_child_userinfo`
-- ----------------------------
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
INSERT INTO `tbl_child_userinfo` VALUES ('15194980385', '308462', '尿', 'female', '天国', 'header18032168790.jpg');

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
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_comment
-- ----------------------------
INSERT INTO `tbl_comment` VALUES ('11', '79', '414223', '怪兽马克', 'header57852687.jpg', '速度发货', '2019-12-16 06:29:52');
INSERT INTO `tbl_comment` VALUES ('12', '82', '909874', '托尼', 'header99663438.jpg', '？？', '2019-12-16 17:11:50');
INSERT INTO `tbl_comment` VALUES ('14', '83', '275686', '弗瑞', 'header6330654.jpg', '牛啊', '2019-12-16 18:53:59');
INSERT INTO `tbl_comment` VALUES ('15', '83', '275686', '弗瑞', 'header6330654.jpg', '与君绝', '2019-12-16 18:55:37');
INSERT INTO `tbl_comment` VALUES ('16', '101', '909874', '托尼', 'header99663438.jpg', '哈哈哈', '2019-12-18 17:05:46');
INSERT INTO `tbl_comment` VALUES ('17', '101', '909874', '托尼', 'header99663438.jpg', '？。。', '2019-12-18 17:05:52');
INSERT INTO `tbl_comment` VALUES ('18', '68', '909874', '托尼', 'header99663438.jpg', '嘤嘤嘤', '2019-12-18 17:31:35');
INSERT INTO `tbl_comment` VALUES ('19', '102', '180321', '嘻嘻哈哈', 'header18032168790.jpg', '嘿嘿', '2019-12-18 17:54:10');
INSERT INTO `tbl_comment` VALUES ('20', '105', '275686', '弗瑞', 'header6330654.jpg', '没人赞我吗', '2019-12-18 19:58:32');
INSERT INTO `tbl_comment` VALUES ('21', '108', '180321', '嘻嘻哈哈', 'header18032168790.jpg', '啊啊啊', '2019-12-19 10:37:59');
INSERT INTO `tbl_comment` VALUES ('22', '109', '180321', '嘻嘻哈哈', 'header18032168790.jpg', '日尼玛', '2020-04-08 09:54:36');
INSERT INTO `tbl_comment` VALUES ('23', '109', '180321', '嘻嘻哈哈', 'header18032168790.jpg', '你是什么东西', '2020-04-08 09:54:42');
INSERT INTO `tbl_comment` VALUES ('24', '112', '180321', '嘻嘻哈哈', 'header18032168790.jpg', '的', '2020-04-09 11:07:52');
INSERT INTO `tbl_comment` VALUES ('25', '118', '', '', '', '观后感', '2020-04-13 19:51:39');
INSERT INTO `tbl_comment` VALUES ('26', '119', '', '', '', '哥哥', '2020-04-13 20:38:26');
INSERT INTO `tbl_comment` VALUES ('27', '118', '', '', '', 'bb', '2020-04-14 11:34:47');
INSERT INTO `tbl_comment` VALUES ('28', '121', '', '', '', '股海护航', '2020-04-14 20:57:36');
INSERT INTO `tbl_comment` VALUES ('29', '122', '', '', '', '？', '2020-04-14 20:58:34');
INSERT INTO `tbl_comment` VALUES ('30', '124', '293876', '屎坦克', 'header18032168790.jpg', '？', '2020-04-14 21:06:24');
INSERT INTO `tbl_comment` VALUES ('31', '124', '293876', '屎坦克', 'header18032168790.jpg', '哦哦哦', '2020-04-14 21:06:27');
INSERT INTO `tbl_comment` VALUES ('32', '125', '638880', '1666', 'rc_default_portrait.png', '哈哈哈', '2020-04-15 10:42:04');
INSERT INTO `tbl_comment` VALUES ('33', '125', '491602', '屎', 'header18032168790.jpg', '的', '2020-04-15 16:17:18');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_connect
-- ----------------------------
INSERT INTO `tbl_connect` VALUES ('1', '996663438', 'a101088', '0');
INSERT INTO `tbl_connect` VALUES ('2', '542400', '99663438', '1');
INSERT INTO `tbl_connect` VALUES ('3', '199906060', '996663438', '0');
INSERT INTO `tbl_connect` VALUES ('4', '56454', '242424000', '0');
INSERT INTO `tbl_connect` VALUES ('5', '88888', '242424000', '0');
INSERT INTO `tbl_connect` VALUES ('6', '6330654', '15194980385', '0');
INSERT INTO `tbl_connect` VALUES ('7', '18032168790', '15194980385', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('5', '414223', '917106');
INSERT INTO `tbl_myattentions` VALUES ('8', '249984', '414223');
INSERT INTO `tbl_myattentions` VALUES ('9', '909874', '275686');
INSERT INTO `tbl_myattentions` VALUES ('10', '275686', '180321');
INSERT INTO `tbl_myattentions` VALUES ('11', '180321', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------
INSERT INTO `tbl_mycollection` VALUES ('85', '1', '58', '2019-12-08 09:32:55');
INSERT INTO `tbl_mycollection` VALUES ('86', '1', '51', '2019-12-08 09:32:59');
INSERT INTO `tbl_mycollection` VALUES ('87', '609387', '60', '2019-12-09 15:49:16');
INSERT INTO `tbl_mycollection` VALUES ('88', '609387', '59', '2019-12-09 15:49:23');
INSERT INTO `tbl_mycollection` VALUES ('89', '609387', '61', '2019-12-09 16:01:46');
INSERT INTO `tbl_mycollection` VALUES ('90', '909874', '65', '2019-12-10 08:56:42');
INSERT INTO `tbl_mycollection` VALUES ('91', '909874', '54', '2019-12-12 17:25:16');
INSERT INTO `tbl_mycollection` VALUES ('92', '909874', '58', '2019-12-12 17:25:17');
INSERT INTO `tbl_mycollection` VALUES ('93', '909874', '67', '2019-12-13 19:48:24');
INSERT INTO `tbl_mycollection` VALUES ('102', '414223', '79', '2019-12-16 06:41:37');
INSERT INTO `tbl_mycollection` VALUES ('103', '909874', '82', '2019-12-16 17:12:04');
INSERT INTO `tbl_mycollection` VALUES ('107', '99663438', '66', '2019-12-17 16:36:05');
INSERT INTO `tbl_mycollection` VALUES ('108', '99663438', '65', '2019-12-17 16:36:06');
INSERT INTO `tbl_mycollection` VALUES ('109', '99663438', '62', '2019-12-17 16:36:09');
INSERT INTO `tbl_mycollection` VALUES ('110', '180321', '105', '2019-12-19 08:22:15');
INSERT INTO `tbl_mycollection` VALUES ('111', '180321', '106', '2019-12-19 10:05:51');
INSERT INTO `tbl_mycollection` VALUES ('112', '180321', '105', '2019-12-19 10:05:52');
INSERT INTO `tbl_mycollection` VALUES ('113', '180321', '108', '2019-12-19 10:37:55');
INSERT INTO `tbl_mycollection` VALUES ('114', '', '119', '2020-04-13 20:44:59');
INSERT INTO `tbl_mycollection` VALUES ('116', '293876', '122', '2020-04-14 21:05:46');
INSERT INTO `tbl_mycollection` VALUES ('117', '293876', '123', '2020-04-14 21:05:46');
INSERT INTO `tbl_mycollection` VALUES ('118', '293876', '124', '2020-04-14 21:06:29');

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
INSERT INTO `tbl_parent_userinfo` VALUES ('15230867500', '491602', '屎', 'male', '美国', '0', '0', '', 'header18032168790.jpg');

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
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_post` VALUES ('65', '托尼', 'header99663438.jpg', '？？？？？？？？？？', '909874', '2019-12-10 08:56:40', '[]');
INSERT INTO `tbl_post` VALUES ('66', '托尼', 'header99663438.jpg', '？？？？？？？', '909874', '2019-12-10 20:33:45', '[]');
INSERT INTO `tbl_post` VALUES ('68', '阿达安市安市', 'header199906060.jpg', '？？？？？嘤嘤嘤', '249984', '2019-12-13 19:51:35', '[\"temp_photo1576237890074.jpg\"]');
INSERT INTO `tbl_post` VALUES ('78', '怪兽马克', 'header57852687.jpg', '增加图片', '414223', '2019-12-16 00:47:25', '[\"temp_photo1576457243661.jpg\"]');
INSERT INTO `tbl_post` VALUES ('79', '怪兽马克', 'header57852687.jpg', '增加功能', '414223', '2019-12-16 06:25:33', '[\"temp_photo1576477531815.jpg\"]');
INSERT INTO `tbl_post` VALUES ('80', '照你', '', '是你', '981660', '2019-12-16 17:00:18', '[\"temp_photo1576486786433.jpg\",\"temp_photo1576486799381.jpg\"]');
INSERT INTO `tbl_post` VALUES ('81', '托尼', 'header99663438.jpg', 'sdfh', '909874', '2019-12-16 17:06:31', '[]');
INSERT INTO `tbl_post` VALUES ('82', '托尼', 'header99663438.jpg', '？？？', '909874', '2019-12-16 17:11:44', '[\"temp_photo1576487471418.jpg\",\"temp_photo1576487480666.jpg\",\"temp_photo1576487498690.jpg\"]');
INSERT INTO `tbl_post` VALUES ('83', '弗瑞', 'header6330654.jpg', '您老', '275686', '2019-12-16 18:53:32', '[\"temp_photo1576493601906.jpg\",\"temp_photo1576493611051.jpg\"]');
INSERT INTO `tbl_post` VALUES ('84', '托尼', 'header99663438.jpg', '上小学到学校', '909874', '2019-12-16 19:07:49', '[]');
INSERT INTO `tbl_post` VALUES ('85', '怪兽马克', 'header57852687.jpg', '图片', '414223', '2019-12-16 13:28:12', '[\"temp_photo1576502868854.jpg\",\"temp_photo1576502889201.jpg\"]');
INSERT INTO `tbl_post` VALUES ('86', '怪兽马克', 'header57852687.jpg', '水电费', '414223', '2019-12-16 13:28:33', '[]');
INSERT INTO `tbl_post` VALUES ('87', '弗瑞', 'header6330654.jpg', '头像', '275686', '2019-12-17 08:18:41', '[\"temp_photo1576541920065.jpg\"]');
INSERT INTO `tbl_post` VALUES ('90', '托尼', 'header99663438.jpg', 'n', '909874', '2019-12-17 09:21:34', '[\"temp_photo1576545690774.jpg\"]');
INSERT INTO `tbl_post` VALUES ('94', '你麻痹EventBus', 'header99663438.jpg', '1231', '99663438', '2019-12-18 09:06:03', '[]');
INSERT INTO `tbl_post` VALUES ('96', '托尼', 'header99663438.jpg', '复仇者！集结', '909874', '2019-12-18 14:02:10', '[\"temp_photo1576648929201.jpg\"]');
INSERT INTO `tbl_post` VALUES ('97', '嘻嘻哈哈', 'header18032168790.jpg', '第一条帖子', '180321', '2019-12-18 16:04:41', '[\"temp_photo1576656280164.jpg\"]');
INSERT INTO `tbl_post` VALUES ('98', '托尼', 'header99663438.jpg', '新家', '909874', '2019-12-18 16:17:43', '[]');
INSERT INTO `tbl_post` VALUES ('99', '嘻嘻哈哈', 'header18032168790.jpg', '大结局道具', '180321', '2019-12-18 16:32:36', '[]');
INSERT INTO `tbl_post` VALUES ('100', '托尼', 'header99663438.jpg', '？', '909874', '2019-12-18 16:52:00', '[]');
INSERT INTO `tbl_post` VALUES ('101', '托尼', 'header99663438.jpg', '带图', '909874', '2019-12-18 16:56:17', '[\"temp_photo1576659375793.jpg\"]');
INSERT INTO `tbl_post` VALUES ('102', '嘻嘻哈哈', 'header18032168790.jpg', '这一刻！完工喽！', '180321', '2019-12-18 17:37:10', '[\"temp_photo1576661829262.jpg\"]');
INSERT INTO `tbl_post` VALUES ('103', '托尼', 'header99663438.jpg', '完ser！', '909874', '2019-12-18 18:02:09', '[\"temp_photo1576663327914.jpg\"]');
INSERT INTO `tbl_post` VALUES ('105', '弗瑞', 'header6330654.jpg', '夜色凉如水', '275686', '2019-12-18 19:51:35', '[\"temp_photo1576669868762.jpg\"]');
INSERT INTO `tbl_post` VALUES ('106', '嘻嘻哈哈', 'header18032168790.jpg', '1', '180321', '2019-12-19 09:15:26', '[]');
INSERT INTO `tbl_post` VALUES ('107', '嘻嘻哈哈', 'header18032168790.jpg', '感谢大家', '180321', '2019-12-19 10:07:50', '[\"temp_photo1576721269707.jpg\"]');
INSERT INTO `tbl_post` VALUES ('108', '嘻嘻哈哈', 'header18032168790.jpg', '11', '180321', '2019-12-19 10:37:51', '[\"temp_photo1576723070661.jpg\"]');
INSERT INTO `tbl_post` VALUES ('109', '嘻嘻哈哈', 'header18032168790.jpg', '哈哈哈(ಡωಡ)hiahiahia', '180321', '2020-04-08 09:54:29', '[\"temp_photo1586310866710.jpg\"]');
INSERT INTO `tbl_post` VALUES ('110', '嘻嘻哈哈', 'header18032168790.jpg', 'www.baidu.com', '180321', '2020-04-08 10:07:44', '[]');
INSERT INTO `tbl_post` VALUES ('111', '嘻嘻哈哈', 'header18032168790.jpg', '你是什么', '180321', '2020-04-09 11:07:03', '[\"temp_photo1586401622453.jpg\"]');
INSERT INTO `tbl_post` VALUES ('112', '嘻嘻哈哈', 'header18032168790.jpg', '什么', '180321', '2020-04-09 11:07:21', '[\"temp_photo1586401639773.jpg\"]');
INSERT INTO `tbl_post` VALUES ('113', '嘻嘻哈哈', 'header18032168790.jpg', '？', '180321', '2020-04-09 11:08:11', '[\"temp_photo1586401684075.jpg\"]');
INSERT INTO `tbl_post` VALUES ('114', '嘻嘻哈哈', 'header18032168790.jpg', '？', '180321', '2020-04-09 11:08:51', '[\"temp_photo1586401719252.jpg\"]');
INSERT INTO `tbl_post` VALUES ('115', '嘻嘻哈哈', 'header18032168790.jpg', 'hhh', '180321', '2020-04-09 11:09:11', '[]');
INSERT INTO `tbl_post` VALUES ('116', '嘻嘻哈哈', 'header18032168790.jpg', 'ddd', '180321', '2020-04-09 11:09:23', '[\"temp_photo1586401759001.jpg\"]');
INSERT INTO `tbl_post` VALUES ('117', '嘻嘻哈哈', 'header18032168790.jpg', 'nn', '180321', '2020-04-09 11:18:06', '[\"temp_photo1586402283407.jpg\"]');
INSERT INTO `tbl_post` VALUES ('118', '嘻嘻哈哈', 'header18032168790.jpg', '的', '180321', '2020-04-09 15:46:13', '[\"temp_photo1586418370422.jpg\"]');
INSERT INTO `tbl_post` VALUES ('119', '', '', '咿呀咿呀哟', '', '2020-04-13 20:19:54', '[\"temp_photo1586780393534.jpg\"]');
INSERT INTO `tbl_post` VALUES ('120', '', '', '哈哈哈(ಡωಡ)hiahiahia', '', '2020-04-13 20:23:02', '[\"temp_photo1586780577771.jpg\"]');
INSERT INTO `tbl_post` VALUES ('121', '', '', '嗯', '', '2020-04-14 15:55:56', '[\"temp_photo1586850934428.jpg\",\"temp_photo1586850954656.jpg\"]');
INSERT INTO `tbl_post` VALUES ('122', '', '', '不', '', '2020-04-14 18:23:14', '[]');
INSERT INTO `tbl_post` VALUES ('123', '嘻嘻哈哈', 'header18032168790.jpg', '？', '180321', '2020-04-14 18:23:39', '[]');
INSERT INTO `tbl_post` VALUES ('124', '屎坦克', 'header18032168790.jpg', '滚滚滚', '293876', '2020-04-14 21:06:20', '[]');
INSERT INTO `tbl_post` VALUES ('125', '1666', 'rc_default_portrait.png', '？', '638880', '2020-04-15 10:41:17', '[\"temp_photo1586918476467.jpg\"]');
INSERT INTO `tbl_post` VALUES ('126', '', '', '不行', '', '2020-04-19 16:10:34', '[\"temp_photo1587283833256.jpg\"]');

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
  `phone` char(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_remind
-- ----------------------------
INSERT INTO `tbl_remind` VALUES ('1', 'hahah', '242424000');
INSERT INTO `tbl_remind` VALUES ('2', '55555', '242424000');
INSERT INTO `tbl_remind` VALUES ('3', '爸，我给你买了好酒，你记得去取啊', '15194980385');

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reply_comment
-- ----------------------------
INSERT INTO `tbl_reply_comment` VALUES ('6', '14', '弗瑞', 'header6330654.jpg', '275686', '0', '天', '2019-12-16 18:55:01');
INSERT INTO `tbl_reply_comment` VALUES ('7', '22', '嘻嘻哈哈', 'header18032168790.jpg', '180321', '0', '我是你妈妈', '2020-04-08 09:55:03');
INSERT INTO `tbl_reply_comment` VALUES ('8', '25', '', '', '', '0', '腹股沟管', '2020-04-13 20:15:22');
INSERT INTO `tbl_reply_comment` VALUES ('9', '27', '', '', '', '0', '宝宝vv', '2020-04-14 11:34:57');

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
INSERT INTO `tbl_user` VALUES ('15194980385', '123456', '2019-12-03 15:29:25', '658943', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15230867500', '123456', '2020-04-15 16:03:24', '491602', '', '', '0');

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
