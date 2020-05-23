/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-05-23 18:38:21
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
-- Table structure for `tbl_admin_message`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_admin_message`;
CREATE TABLE `tbl_admin_message` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  `postId` int(10) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `personId` varchar(11) CHARACTER SET utf8 DEFAULT NULL,
  `unread` int(2) DEFAULT NULL,
  `content_answer` varchar(1000) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_admin_message
-- ----------------------------
INSERT INTO `tbl_admin_message` VALUES ('1', '欢迎光临微家大家庭，点击查看使用规则~', '0', '15230867500', '491602', '1', '');
INSERT INTO `tbl_admin_message` VALUES ('2', '您的个人资料违规已被修改，点击查看', '0', '15230867500', '491602', '1', '');
INSERT INTO `tbl_admin_message` VALUES ('3', '您的帖子已被更改，点击查看', '0', '15230867500', '491602', '1', '');
INSERT INTO `tbl_admin_message` VALUES ('4', '您的帖子已被审核，点击查看', '159', '15230867500', '491602', '1', '');
INSERT INTO `tbl_admin_message` VALUES ('21', '您的问题反馈已被回复，点击查看', '-9', '15230867500', '', '1', '驱蚊器翁群');
INSERT INTO `tbl_admin_message` VALUES ('22', '您的问题反馈已被回复，点击查看', '-8', '15230867500', '', '1', '123123123');
INSERT INTO `tbl_admin_message` VALUES ('23', '您的问题反馈已被回复，点击查看', '-10', '15230867500', '', '1', '好的，谢谢你，，，，，');
INSERT INTO `tbl_admin_message` VALUES ('24', '您的问题反馈已被回复，点击查看', '-11', '15230867500', '', '1', 'adasdasdasd');
INSERT INTO `tbl_admin_message` VALUES ('25', '您的问题反馈已被回复，点击查看', '-12', '15230867500', '', '1', '0..0.0.');
INSERT INTO `tbl_admin_message` VALUES ('26', '您的问题反馈已被回复，点击查看', '-13', '15230867500', '', '1', '0000');
INSERT INTO `tbl_admin_message` VALUES ('27', '您的问题反馈已被回复，点击查看', '-14', '15230867500', '', '1', '0.0.0.0');
INSERT INTO `tbl_admin_message` VALUES ('28', '您的问题反馈已被回复，点击查看', '-15', '15230867500', '', '1', '恭喜发财');
INSERT INTO `tbl_admin_message` VALUES ('29', '您的问题反馈已被回复，点击查看', '-16', '15230867500', '', '1', '好的好的');
INSERT INTO `tbl_admin_message` VALUES ('30', '您的问题反馈已被回复，点击查看', '-17', '15230867500', '', '1', '阿斯达斯');
INSERT INTO `tbl_admin_message` VALUES ('31', '您的问题反馈已被回复，点击查看', '-4', '13600000000', '', '0', '11111');
INSERT INTO `tbl_admin_message` VALUES ('32', '您的问题反馈已被回复，点击查看', '-3', '15500000000', '', '0', '00000');
INSERT INTO `tbl_admin_message` VALUES ('33', '您的问题反馈已被回复，点击查看', '-2', '15200000000', '', '0', '啊实打实大师');
INSERT INTO `tbl_admin_message` VALUES ('34', '您的问题反馈已被回复，点击查看', '-18', '13513171332', '', '1', '企鹅翁群翁群无');
INSERT INTO `tbl_admin_message` VALUES ('35', '您的问题反馈已被回复，点击查看', '-1', '13300000000', '', '0', '企鹅翁群无');
INSERT INTO `tbl_admin_message` VALUES ('36', '您的问题反馈已被回复，点击查看', '-19', '13513171332', '', '1', '驱蚊器翁群翁群');
INSERT INTO `tbl_admin_message` VALUES ('37', '您的问题反馈已被回复，点击查看', '-20', '15230867500', '', '1', '戚薇戚薇戚薇戚薇戚薇');

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
INSERT INTO `tbl_alarm` VALUES ('16', '07:30', '9654782', '195412', '今天有活动，超市大减价', '0');
INSERT INTO `tbl_alarm` VALUES ('26', '5:00', '1111111', '99663438', '888', '1');
INSERT INTO `tbl_alarm` VALUES ('32', '09:22', '15194980385', '18032168790', '嗯', '0');
INSERT INTO `tbl_alarm` VALUES ('35', '19:35', '15194980385', '15230867500', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('36', '10:37', '15194980385', '18032168790', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('37', '10:00', '15194980385', '18032168790', '………', '0');
INSERT INTO `tbl_alarm` VALUES ('38', '15:42', '15194980385', 'jpcaa', '6有6', '0');

-- ----------------------------
-- Table structure for `tbl_answers`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_answers`;
CREATE TABLE `tbl_answers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) NOT NULL,
  `content` varchar(2000) CHARACTER SET utf8 NOT NULL,
  `postId` int(10) NOT NULL,
  `registrationID` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_answers
-- ----------------------------
INSERT INTO `tbl_answers` VALUES ('35', '15230867500', '好的，谢谢您的反馈！', '9', '');
INSERT INTO `tbl_answers` VALUES ('36', '15230867500', '驱蚊器翁群', '8', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('37', '15230867500', '好的，谢谢你，，，，，', '10', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('38', '15230867500', 'adasdasdasd', '11', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('39', '15230867500', '0..0.0.', '12', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('40', '15230867500', '0000', '13', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('41', '15230867500', '0.0.0.0', '14', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('42', '15230867500', '恭喜发财', '15', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('43', '15230867500', '好的好的', '16', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('44', '15230867500', '阿斯达斯', '17', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('45', '13600000000', '11111', '4', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('46', '15500000000', '00000', '3', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('47', '15200000000', '啊实打实大师', '2', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('48', '13513171332', '企鹅翁群翁群无', '18', '');
INSERT INTO `tbl_answers` VALUES ('49', '13300000000', '企鹅翁群无', '1', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('50', '13513171332', '驱蚊器翁群翁群', '19', '13065ffa4e8b884daa9');
INSERT INTO `tbl_answers` VALUES ('51', '15230867500', '戚薇戚薇戚薇戚薇戚薇', '20', '1104a8979228f292c09');

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
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_comment` VALUES ('33', '125', '491602', 'sunsunsun', 'header15230867500', '的', '2020-04-15 16:17:18');
INSERT INTO `tbl_comment` VALUES ('34', '126', '491602', 'sunsunsun', 'header15230867500', '计算机', '2020-04-19 16:28:43');
INSERT INTO `tbl_comment` VALUES ('35', '129', '491602', 'sunsunsun', 'header15230867500', '在你在家呢', '2020-04-24 19:13:48');
INSERT INTO `tbl_comment` VALUES ('36', '128', '491602', 'sunsunsun', 'header15230867500', '日', '2020-04-24 21:03:06');
INSERT INTO `tbl_comment` VALUES ('37', '128', '491602', 'sunsunsun', 'header15230867500', 'ok', '2020-04-24 21:03:10');
INSERT INTO `tbl_comment` VALUES ('38', '128', '491602', 'sunsunsun', 'header15230867500', '尼玛', '2020-04-24 21:03:13');
INSERT INTO `tbl_comment` VALUES ('39', '130', '491602', 'sunsunsun', 'header15230867500', '山', '2020-04-25 15:51:30');
INSERT INTO `tbl_comment` VALUES ('40', '130', '491602', 'sunsunsun', 'header15230867500', '安安啦', '2020-04-25 15:51:33');
INSERT INTO `tbl_comment` VALUES ('41', '156', '491602', 'sunsunsun', 'header15230867500', '牛逼', '2020-04-27 22:26:10');
INSERT INTO `tbl_comment` VALUES ('42', '161', '491602', 'sunsunsun', 'header15230867500', '战局', '2020-05-20 18:54:21');
INSERT INTO `tbl_comment` VALUES ('43', '161', '491602', 'sunsunsun', 'header15230867500', '发', '2020-05-22 16:52:53');
INSERT INTO `tbl_comment` VALUES ('44', '161', '491602', 'sunsunsun', 'header15230867500', '下', '2020-05-22 16:52:55');
INSERT INTO `tbl_comment` VALUES ('45', '161', '491602', 'sunsunsun', 'header15230867500', '等等', '2020-05-22 16:52:58');
INSERT INTO `tbl_comment` VALUES ('46', '161', '491602', 'sunsunsun', 'header15230867500', '的东西', '2020-05-22 16:53:00');
INSERT INTO `tbl_comment` VALUES ('47', '199', '491602', 'sunsunsun', 'header15230867500', '睡觉睡觉手机', '2020-05-23 17:21:06');
INSERT INTO `tbl_comment` VALUES ('48', '199', '491602', 'sunsunsun', 'header15230867500', '待机时间多看看', '2020-05-23 17:21:09');
INSERT INTO `tbl_comment` VALUES ('49', '199', '491602', 'sunsunsun', 'header15230867500', '择席', '2020-05-23 17:21:11');

-- ----------------------------
-- Table structure for `tbl_connect`
-- ----------------------------
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
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_goodpost` VALUES ('92', '161', '491602', '852000', '2020-05-20 18:54:17');
INSERT INTO `tbl_goodpost` VALUES ('93', '194', '491602', '491602', '2020-05-20 19:17:31');
INSERT INTO `tbl_goodpost` VALUES ('94', '159', '491602', '491602', '2020-05-20 23:30:43');
INSERT INTO `tbl_goodpost` VALUES ('95', '199', '491602', '491602', '2020-05-23 17:21:13');

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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('47', '852000', '491602');

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
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------
INSERT INTO `tbl_mycollection` VALUES ('131', '491602', '165', '2020-05-10 22:25:45');
INSERT INTO `tbl_mycollection` VALUES ('133', '491602', '167', '2020-05-11 17:52:24');
INSERT INTO `tbl_mycollection` VALUES ('134', '491602', '199', '2020-05-23 17:21:15');

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

-- ----------------------------
-- Records of tbl_parent_userinfo
-- ----------------------------
INSERT INTO `tbl_parent_userinfo` VALUES ('13513171332', '254870', '发货1231', 'female', '江西省，南昌市，东湖区', '1968-10-10', '0', '12312312', 'header13513171332');
INSERT INTO `tbl_parent_userinfo` VALUES ('15227856991', '852000', 'mon', 'female', '阿斯顿', '1970-10-10', '0', null, 'header15227856991');
INSERT INTO `tbl_parent_userinfo` VALUES ('15230867500', '491602', 'sunsunsun', 'female', '贵州省，贵阳市，乌当区', '1998-12-14', '0', '哈哈', 'header15230867500');
INSERT INTO `tbl_parent_userinfo` VALUES ('kefuxw', '000000', '客服小薇', 'female', '石家庄', '1970-10-10', '0', '-', 'headerkefuxw');

-- ----------------------------
-- Table structure for `tbl_post`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL,
  `headimg` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `personId` varchar(30) CHARACTER SET utf8mb4 NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=169 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post
-- ----------------------------
INSERT INTO `tbl_post` VALUES ('159', 'sunsunsun', 'header15230867500', '大酒店', '491602', '2020-04-27 20:58:00', 'temp_photo1587992280173.jpg');
INSERT INTO `tbl_post` VALUES ('160', 'mon', 'header15227856991', '拉去', '852000', '2020-05-17 16:00:31', 'temp_photo1587992280173.jpg');
INSERT INTO `tbl_post` VALUES ('161', 'mon', 'header15227856991', '拍照', '852000', '2020-05-17 14:52:31', 'temp_photo1587992280173.jpg');
INSERT INTO `tbl_post` VALUES ('162', 'sunsunsun', 'header15230867500', '是', '491602', '2020-05-20 16:34:05', 'SVID_20200519_233601_1.mp4\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg');
INSERT INTO `tbl_post` VALUES ('163', 'sunsunsun', 'header15230867500', '试试', '491602', '2020-05-20 19:13:07', 'Screenshot_20200519_222642_com.kuaishou.nebula.jpg\",\"mmexport1587277665128.jpg\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg\",\"mmexport1587277665128.jpg\",\"wx_camera_1589020536367.jpg\",\"wx_camera_1588912749720.jpg\",\"1096708182.jpeg\",\"IMG_20200409_233648.jpg\",\"1098325509.jpeg\",\"IMG_20200407_001917.jpg\",\"997421641.jpeg');
INSERT INTO `tbl_post` VALUES ('164', 'sunsunsun', 'header15230867500', '1', '491602', '2020-05-20 19:16:44', 'IMG_20190109_193153.jpg\",\"433503642_mh1547286904455.jpg\",\"IMG_20190219_165159.jpg\",\"IMG_20190107_131657.jpg\",\"IMG_20190101_222040.jpg\",\"IMG_20190105_234837.jpg\",\"IMG_20181106_171754.jpg\",\"IMG_20190101_122425.jpg\",\"IMG_20190107_134719.jpg');
INSERT INTO `tbl_post` VALUES ('165', 'sunsunsun', 'header15230867500', '1', '491602', '2020-05-21 23:59:09', 'Screenshot_20200521_230553_com.kuaishou.nebula.jpg');
INSERT INTO `tbl_post` VALUES ('166', 'sunsunsun', 'header15230867500', '试试', '491602', '2020-05-22 12:04:55', 'Screenshot_20200521_230553_com.kuaishou.nebula.jpg\",\"Screenshot_20200521_184253_com.tencent.tmgp.sgame.jpg\",\"headerkefuxw.jpg\",\"header15227856991.jpg\",\"Screenshot_20200520_210448_com.vhome.chat.jpg\",\"Screenshot_20200520_204825_com.vhome.chat.jpg\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg');
INSERT INTO `tbl_post` VALUES ('167', 'sunsunsun', 'header15230867500', '1', '491602', '2020-05-22 13:11:45', '1.mp4\",\"headerkefuzhanghao.jpg');
INSERT INTO `tbl_post` VALUES ('168', 'sunsunsun', 'header15230867500', '1', '491602', '2020-05-22 13:12:02', 'headerkefuzhanghao.jpg');

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
-- Table structure for `tbl_post_copy`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post_copy`;
CREATE TABLE `tbl_post_copy` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(30) DEFAULT NULL,
  `headimg` varchar(50) DEFAULT NULL,
  `content` varchar(500) DEFAULT NULL,
  `personId` varchar(30) NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(2048) DEFAULT NULL,
  `examine` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post_copy
-- ----------------------------
INSERT INTO `tbl_post_copy` VALUES ('183', 'mon', 'header15227856991', '加图删一个', '852000', '2020-05-17 14:51:55', '200856152244627_2.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('184', 'mon', 'header15227856991', '拍照', '852000', '2020-05-17 14:52:31', 'IMG_20200517_14521749.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('186', 'mon', 'header15227856991', '删一个视频', '852000', '2020-05-17 14:54:17', 'OIP.2xOrqtuh-c3BS4RVlKwQfQHaNK_pid\\u003dApi\\u0026dpr\\u003d2.jpg', '审核失败');
INSERT INTO `tbl_post_copy` VALUES ('187', 'mon', 'header15227856991', '拉去', '852000', '2020-05-17 16:00:31', '200856152244627_2.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('188', 'sun', 'header15230867500', '视频', '491602', '2020-05-20 16:09:44', '200856152244627_2.jpg', '审核失败');
INSERT INTO `tbl_post_copy` VALUES ('191', 'sun', 'header15230867500', '是', '491602', '2020-05-20 16:34:05', 'SVID_20200519_233601_1.mp4\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('192', 'sun', 'header15230867500', 'sjsj', '491602', '2020-05-20 16:46:19', 'wx_camera_1588912749720.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('193', 'sun', 'header15230867500', '试试', '491602', '2020-05-20 19:13:07', 'Screenshot_20200519_222642_com.kuaishou.nebula.jpg\",\"mmexport1587277665128.jpg\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg\",\"mmexport1587277665128.jpg\",\"wx_camera_1589020536367.jpg\",\"wx_camera_1588912749720.jpg\",\"1096708182.jpeg\",\"IMG_20200409_233648.jpg\",\"1098325509.jpeg\",\"IMG_20200407_001917.jpg\",\"997421641.jpeg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('194', 'sun', 'header15230867500', '1', '491602', '2020-05-20 19:16:44', 'IMG_20190109_193153.jpg\",\"433503642_mh1547286904455.jpg\",\"IMG_20190219_165159.jpg\",\"IMG_20190107_131657.jpg\",\"IMG_20190101_222040.jpg\",\"IMG_20190105_234837.jpg\",\"IMG_20181106_171754.jpg\",\"IMG_20190101_122425.jpg\",\"IMG_20190107_134719.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('195', 'sun', 'header15230867500', '1', '491602', '2020-05-21 23:59:09', 'Screenshot_20200521_230553_com.kuaishou.nebula.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('196', 'sun', 'header15230867500', '试试', '491602', '2020-05-22 12:04:55', 'Screenshot_20200521_230553_com.kuaishou.nebula.jpg\",\"Screenshot_20200521_184253_com.tencent.tmgp.sgame.jpg\",\"headerkefuxw.jpg\",\"header15227856991.jpg\",\"Screenshot_20200520_210448_com.vhome.chat.jpg\",\"Screenshot_20200520_204825_com.vhome.chat.jpg\",\"Screenshot_20200519_222642_com.kuaishou.nebula.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('197', 'sun', 'header15230867500', '1', '491602', '2020-05-22 13:11:45', '1.mp4\",\"headerkefuzhanghao.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('198', 'sun', 'header15230867500', '1', '491602', '2020-05-22 13:12:02', 'headerkefuzhanghao.jpg', '已审核');
INSERT INTO `tbl_post_copy` VALUES ('199', 'sun', 'header15230867500', '直接睡觉睡觉', '491602', '2020-05-23 17:13:34', '-4fd5f905fc13763a.jpg', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('200', 'sun', 'header15230867500', '找找', '491602', '2020-05-23 17:48:08', '', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('201', 'sun', 'header15230867500', '嘻嘻嘻', '491602', '2020-05-23 17:50:44', '', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('202', 'sun', 'header15230867500', '杀鸡', '491602', '2020-05-23 17:51:01', '-4fd5f905fc13763a.jpg', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('203', 'sun', 'header15230867500', '睡觉睡觉手机', '491602', '2020-05-23 17:51:43', 'Screenshot_20200521_184253_com.tencent.tmgp.sgame.jpg', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('204', 'sun', 'header15230867500', '1', '491602', '2020-05-23 17:52:46', 'Screenshot_20200519_222642_com.kuaishou.nebula.jpg', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('205', 'sun', 'header15230867500', '就是生快生快', '491602', '2020-05-23 17:59:03', 'Screenshot_20200522_115008_com.vhome.chat.jpg', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('206', 'sun', 'header15230867500', '只剩下', '491602', '2020-05-23 17:59:19', '', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('207', 'sun', 'header15230867500', '现在在', '491602', '2020-05-23 17:59:25', '', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('208', 'sun', 'header15230867500', '嘻嘻嘻', '491602', '2020-05-23 17:59:34', '', '待审核');
INSERT INTO `tbl_post_copy` VALUES ('209', 'sun', 'header15230867500', '少食多餐', '491602', '2020-05-23 17:59:43', '', '待审核');

-- ----------------------------
-- Table structure for `tbl_post_report`
-- ----------------------------
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

-- ----------------------------
-- Records of tbl_post_report
-- ----------------------------
INSERT INTO `tbl_post_report` VALUES ('154', '屎', 'header15230867500', '上', '491602', '2020-04-27 21:18:29', 'temp_photo1587992315661.jpg');

-- ----------------------------
-- Table structure for `tbl_questions`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_questions`;
CREATE TABLE `tbl_questions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(11) NOT NULL,
  `registrationID` varchar(100) NOT NULL,
  `theme` varchar(20) CHARACTER SET utf8 NOT NULL,
  `content` varchar(200) CHARACTER SET utf8 NOT NULL,
  `status` int(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_questions
-- ----------------------------
INSERT INTO `tbl_questions` VALUES ('1', '姬文斌', '13300000000', '1104a8979228f292c09', '关于获奖资金分配问题', '我自愿退出分配奖金，希望大家同意，跪下磕头了给你们', '1');
INSERT INTO `tbl_questions` VALUES ('2', '姬豆豆', '15200000000', '1104a8979228f292c09', '关于自觉问题', '姬文斌十一点起，十一点睡，该治治了', '1');
INSERT INTO `tbl_questions` VALUES ('3', '姬小生', '15500000000', '1104a8979228f292c09', '关于哈哈', '靳文斌', '1');
INSERT INTO `tbl_questions` VALUES ('4', '姬妹妹', '13600000000', '1104a8979228f292c09', '管你呢', '嗯嗯嗯嗯呃', '1');
INSERT INTO `tbl_questions` VALUES ('8', '京津冀', '15230867500', '1104a8979228f292c09', '刷卡', '傻男说你什么萨克斯', '1');
INSERT INTO `tbl_questions` VALUES ('9', '123', '15230867500', '1104a8979228f292c09', '是吧', '好的呢', '1');
INSERT INTO `tbl_questions` VALUES ('10', 'jsjs', '15230867500', '1104a8979228f292c09', 'sjsj', '京津冀京津冀\n\n', '1');
INSERT INTO `tbl_questions` VALUES ('11', 'd', '15230867500', '1104a8979228f292c09', 'ss', 'xjsjzj', '1');
INSERT INTO `tbl_questions` VALUES ('12', 'qq', '15230867500', '1104a8979228f292c09', 'qq', 'ajjajd\n\n', '1');
INSERT INTO `tbl_questions` VALUES ('13', 'l', '15230867500', '1104a8979228f292c09', 'l', 'ajajs\n', '1');
INSERT INTO `tbl_questions` VALUES ('14', 'a', '15230867500', '1104a8979228f292c09', 'a', 'jdjd', '1');
INSERT INTO `tbl_questions` VALUES ('15', 'q', '15230867500', '1104a8979228f292c09', 'q', 'sksk\n', '1');
INSERT INTO `tbl_questions` VALUES ('16', 'jsksk', '15230867500', '1104a8979228f292c09', 'skskkd', 'sksk\n\n', '1');
INSERT INTO `tbl_questions` VALUES ('17', 'd', '15230867500', '1104a8979228f292c09', 'd', 'xdd', '1');
INSERT INTO `tbl_questions` VALUES ('18', 'QW ', '13513171332', '', 'QWE ', '请问请问', '1');
INSERT INTO `tbl_questions` VALUES ('19', '11', '13513171332', '13065ffa4e8b884daa9', '11', '12', '1');
INSERT INTO `tbl_questions` VALUES ('20', '1', '15230867500', '1104a8979228f292c09', '1', 'xxx处有bug', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_relations_request
-- ----------------------------
INSERT INTO `tbl_relations_request` VALUES ('2', '15194980385', '尿', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('3', '123456789', '尿', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('4', '15230867500', '屎', '15194980385', '-1');
INSERT INTO `tbl_relations_request` VALUES ('5', '555555', '5', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('6', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('7', '15230867500', '滚滚滚滚', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('8', '15194980385', '改', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('9', '15230867500', 'sun', '15227856991', '0');
INSERT INTO `tbl_relations_request` VALUES ('10', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('11', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('12', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('13', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('14', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('15', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('16', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('17', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('18', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('19', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('20', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('21', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('22', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('23', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('24', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');
INSERT INTO `tbl_relations_request` VALUES ('25', '15194980385', '子女子女子女子女测测测测', '15230867500', '-1');

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
INSERT INTO `tbl_user` VALUES ('13513171332', '123456', '2020-04-28 21:56:36', '254870', '', '', '0');
INSERT INTO `tbl_user` VALUES ('15194980385', '123456', '2019-12-03 15:29:25', '658943', '', '', '1');
INSERT INTO `tbl_user` VALUES ('15227856991', '654321', '2020-02-15 16:03:24', '852000', null, null, '0');
INSERT INTO `tbl_user` VALUES ('15230867500', '123456', '2019-10-15 16:03:24', '491602', '', '', '0');
INSERT INTO `tbl_user` VALUES ('kefuxw', 'wanjpc123', '0000-00-00 00:00:00', '000000', null, null, '0');

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
