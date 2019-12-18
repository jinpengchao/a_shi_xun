/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2019-12-18 23:20:54
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
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_alarm` VALUES ('32', '11:02', '15194980385', '18032168790', '嗯', '1');

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
INSERT INTO `tbl_child_userinfo` VALUES ('15194980385', '135632', '队长', 'female', '', 'header15194980385.jpg');
INSERT INTO `tbl_child_userinfo` VALUES ('15199980888', '792997', 'qwqw', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('242424000', '635192', '242424000', 'female', '', 'header242424000.jpg');
INSERT INTO `tbl_child_userinfo` VALUES ('542400', '840875', '542400', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('8885201', '550707', '浮点数是的', '', '', '');
INSERT INTO `tbl_child_userinfo` VALUES ('9654782', '394014', '小兰', '', '', '');
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
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_comment` VALUES ('19', '102', '180321', '长者', 'header18032168790.jpg', '嘿嘿', '2019-12-18 17:54:10');
INSERT INTO `tbl_comment` VALUES ('20', '105', '275686', '弗瑞', 'header6330654.jpg', '没人赞我吗', '2019-12-18 19:58:32');

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
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('5', '414223', '917106');
INSERT INTO `tbl_myattentions` VALUES ('8', '249984', '414223');
INSERT INTO `tbl_myattentions` VALUES ('9', '909874', '275686');

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
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_parent_userinfo` VALUES ('1', '1', '1', '男', '1', null, '2', '可以', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('11231935210', '755340', '13131231231', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('12312398986', '385441', '89615302.', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('13133131313', '324686', '131241', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('15227856991', '563794', '雷神', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('15230867500', '293876', '屎坦克', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('15513155225', '195412', '史泰龙', '男', '石家庄', null, '100', '今晚必须死', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('18032168790', '180321', '长者', 'male', '北京市-北京市-昌平区', '0', '0', null, 'header18032168790.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('19198080', '796031', '阿斯顿', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('199906060', '249984', '阿达安市安市', 'female', '安徽省-阜阳市-阜南县', '0', '0', '', 'header199906060.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('38163816', '917106', '小黄', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('43843866', '609387', '阿达啊', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('57852687', '414223', '怪兽马克', 'female', '', '0', '0', '', 'header57852687.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('6330654', '275686', '弗瑞', 'male', '广西壮族自治区-防城港市-东兴市', '0', '0', '', 'header6330654.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('87987987987', '770389', '89615302.其实', 'male', '', '0', '0', '', 'header87987987987.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('89416601435', '213440', '99946513', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('91919191991', '791657', '大青蛙', '', '', '0', '0', '', 'rc_default_portrait.png');
INSERT INTO `tbl_parent_userinfo` VALUES ('986532', '981660', '照你', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('9866521', '109156', '哈哈', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('987654222438', '201977', '123123', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('99442205', '863018', '232.3', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('9963438', '231042', '靳朋朝', '', '安徽省-黄山市-黄山区', '0', '0', '', 'header1576025129320.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('99663438', '909874', '托尼', 'male', '河北省-保定市-定州市', '0', '0', '', 'header99663438.jpg');
INSERT INTO `tbl_parent_userinfo` VALUES ('996663438', '254653', '996663438', '', '', '0', '0', '', '');
INSERT INTO `tbl_parent_userinfo` VALUES ('99865324986', '202737', '123额', '', '', '0', '0', '', 'rc_default_portrait.png');

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
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_post` VALUES ('97', '长者', 'header18032168790.jpg', '第一条帖子', '180321', '2019-12-18 16:04:41', '[\"temp_photo1576656280164.jpg\"]');
INSERT INTO `tbl_post` VALUES ('98', '托尼', 'header99663438.jpg', '新家', '909874', '2019-12-18 16:17:43', '[]');
INSERT INTO `tbl_post` VALUES ('99', '长者', 'header18032168790.jpg', '大结局道具', '180321', '2019-12-18 16:32:36', '[]');
INSERT INTO `tbl_post` VALUES ('100', '托尼', 'header99663438.jpg', '？', '909874', '2019-12-18 16:52:00', '[]');
INSERT INTO `tbl_post` VALUES ('101', '托尼', 'header99663438.jpg', '带图', '909874', '2019-12-18 16:56:17', '[\"temp_photo1576659375793.jpg\"]');
INSERT INTO `tbl_post` VALUES ('102', '长者', 'header18032168790.jpg', '这一刻！完工喽！', '180321', '2019-12-18 17:37:10', '[\"temp_photo1576661829262.jpg\"]');
INSERT INTO `tbl_post` VALUES ('103', '托尼', 'header99663438.jpg', '完ser！', '909874', '2019-12-18 18:02:09', '[\"temp_photo1576663327914.jpg\"]');
INSERT INTO `tbl_post` VALUES ('105', '弗瑞', 'header6330654.jpg', '夜色凉如水', '275686', '2019-12-18 19:51:35', '[\"temp_photo1576669868762.jpg\"]');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reply_comment
-- ----------------------------
INSERT INTO `tbl_reply_comment` VALUES ('6', '14', '弗瑞', 'header6330654.jpg', '275686', '0', '天', '2019-12-16 18:55:01');

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
INSERT INTO `tbl_user` VALUES ('1', '1', '0000-00-00 00:00:00', '1', null, null, '0');
INSERT INTO `tbl_user` VALUES ('11111111111', '111', '2019-12-03 15:29:25', '111111', null, null, '0');
INSERT INTO `tbl_user` VALUES ('11231935210', '123456', '2019-12-17 17:02:18', '755340', '', '', '0');
INSERT INTO `tbl_user` VALUES ('12122', '11111', '2019-12-09 15:26:57', '609387', '', '', '0');
INSERT INTO `tbl_user` VALUES ('12312398986', '6556565', '2019-12-17 17:06:27', '385441', '', '', '0');
INSERT INTO `tbl_user` VALUES ('13133131313', '111111', '2019-12-17 17:12:04', '324686', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15194980385', '111111', '2019-12-18 08:33:12', '135632', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15199980888', '1', '2019-12-04 10:56:41', '792997', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15227856991', '111111', '2019-12-18 10:52:34', '563794', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15230145956', 'zhangpeng002', '2019-12-09 11:58:47', '734624', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15230867500', '111111', '2019-12-18 10:39:56', '293876', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15513155225', 'qqq123', '2019-12-03 15:29:25', '658943', '', '', '0');
INSERT INTO `tbl_user` VALUES ('18032168790', '123456', '0000-00-00 00:00:00', '180321', null, null, '0');
INSERT INTO `tbl_user` VALUES ('19198080', '1234', '2019-12-11 15:41:12', '796031', '', '', '0');
INSERT INTO `tbl_user` VALUES ('199906060', '11111', '2019-12-13 14:11:54', '249984', '', '', '0');
INSERT INTO `tbl_user` VALUES ('199908177', '11111', '0000-00-00 00:00:00', '', null, null, '1');
INSERT INTO `tbl_user` VALUES ('2', '2', '0000-00-00 00:00:00', '2', null, null, '1');
INSERT INTO `tbl_user` VALUES ('242424000', '11111', '2019-12-13 22:25:14', '635192', '', '', '1');
INSERT INTO `tbl_user` VALUES ('38163816', '654321', '2019-12-16 01:22:32', '917106', '', '', '0');
INSERT INTO `tbl_user` VALUES ('542400', '11111', '2019-12-13 08:16:40', '840875', '', '', '1');
INSERT INTO `tbl_user` VALUES ('57852687', '654321', '2019-12-15 13:27:44', '414223', '', '', '0');
INSERT INTO `tbl_user` VALUES ('6330654', '654321', '2019-12-16 18:52:39', '275686', '', '', '0');
INSERT INTO `tbl_user` VALUES ('87987987987', '111111', '2019-12-17 17:07:05', '770389', '', '', '0');
INSERT INTO `tbl_user` VALUES ('8885201', '11111', '2019-12-12 17:27:51', '550707', '', '', '1');
INSERT INTO `tbl_user` VALUES ('89416601435', '111111', '2019-12-17 17:10:47', '213440', '', '', '0');
INSERT INTO `tbl_user` VALUES ('91919191991', '111111', '2019-12-17 17:14:15', '791657', '', '', '0');
INSERT INTO `tbl_user` VALUES ('9654782', '654321', '2019-12-16 00:35:34', '394014', '', '', '1');
INSERT INTO `tbl_user` VALUES ('9696438', '555555555', '2019-12-09 20:14:06', '114607', '', '', '0');
INSERT INTO `tbl_user` VALUES ('986532', '654321', '2019-12-16 16:58:18', '981660', '', '', '0');
INSERT INTO `tbl_user` VALUES ('9866521', '1111', '2019-12-10 08:48:24', '109156', '', '', '0');
INSERT INTO `tbl_user` VALUES ('987438', '11111111', '2019-12-09 19:59:06', '955244', '', '', '0');
INSERT INTO `tbl_user` VALUES ('987654222438', '11111111', '2019-12-09 20:24:27', '201977', '', '', '0');
INSERT INTO `tbl_user` VALUES ('99442205', '11111', '2019-12-11 15:41:53', '863018', '', '', '0');
INSERT INTO `tbl_user` VALUES ('9963438', '11111111', '2019-12-11 08:43:34', '231042', '', '', '0');
INSERT INTO `tbl_user` VALUES ('996521', '11111', '2019-12-12 20:24:59', '414292', '', '', '1');
INSERT INTO `tbl_user` VALUES ('996633438', '11111', '2019-12-11 20:13:52', '546272', '', '', '0');
INSERT INTO `tbl_user` VALUES ('99663438', '111111', '2019-12-09 20:25:20', '909874', '', '', '0');
INSERT INTO `tbl_user` VALUES ('996663438', '11111', '2019-12-12 21:00:02', '254653', '', '', '0');
INSERT INTO `tbl_user` VALUES ('99865324986', '1233', '2019-12-17 17:04:47', '202737', '', '', '0');

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
