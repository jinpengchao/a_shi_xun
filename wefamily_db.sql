/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50506
Source Host           : localhost:3306
Source Database       : wefamily_db

Target Server Type    : MYSQL
Target Server Version : 50506
File Encoding         : 65001

Date: 2020-06-03 19:45:56
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
INSERT INTO `tbl_admin` VALUES ('1', '1093750621', '123456', '管理员靳朋朝', '368653986.jpg');

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
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_admin_message
-- ----------------------------
INSERT INTO `tbl_admin_message` VALUES ('1', '欢迎光临微家大家庭，点击查看使用规则~', '-999999', '15230867500', '491602', '1', '使用');
INSERT INTO `tbl_admin_message` VALUES ('2', '欢迎光临微家大家庭，点击查看使用规则~', '-999999', '15227856991', '852000', '0', '使用');
INSERT INTO `tbl_admin_message` VALUES ('64', '恭喜！您的帖子已通过审核，点击查看', '231', '15227856991', '852000', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('71', '恭喜！您的帖子已通过审核，点击查看', '225', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('72', '恭喜！您的帖子已通过审核，点击查看', '244', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('73', '恭喜！您的帖子已通过审核，点击查看', '245', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('74', '恭喜！您的帖子已通过审核，点击查看', '246', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('83', '您的问题反馈已被回复，点击查看', '-29', '15230867500', '', '1', '尊敬的靳朋朝，你好：\r\n    巴拉巴拉');
INSERT INTO `tbl_admin_message` VALUES ('84', '恭喜！您的帖子已通过审核，点击查看', '247', '15227856991', '', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('85', '您的问题反馈已被回复，点击查看', '-30', '15230867500', '', '1', '收到测试~~~！！！');
INSERT INTO `tbl_admin_message` VALUES ('86', '很遗憾！您的帖子没有通过审核，请检查是否违反社区规定！', '248', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('87', '恭喜！您的帖子已通过审核，点击查看', '250', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('88', '您的问题反馈已被回复，点击查看', '-31', '15230867500', '', '1', '000000000');
INSERT INTO `tbl_admin_message` VALUES ('89', '很遗憾！您的帖子没有通过审核，请检查是否违反社区规定！', '249', '15230867500', '491602', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('90', '您的帖子 \"九图展示馆\" 被其他用户举报，现已被删除，请注意遵守社区规定！', '0', '15230867500', '', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('91', '恭喜！您的帖子已通过审核，点击查看', '251', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('92', '您的帖子 \"发表\" 被其他用户举报，现已被删除，请注意遵守社区规定！', '0', '15230867500', '', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('93', '很遗憾！您的帖子没有通过审核，请检查是否违反社区规定！', '252', '15230867500', '491602', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('94', '很遗憾！您的帖子没有通过审核，请检查是否违反社区规定！', '253', '15230867500', '491602', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('95', '恭喜！您的帖子已通过审核，点击查看', '254', '15230867500', '491602', '1', '-');
INSERT INTO `tbl_admin_message` VALUES ('97', '您的问题反馈已被回复，点击查看', '-33', '15230867500', '', '1', '尊敬的xxx:\r\n您好 您的反馈我们收到了！');
INSERT INTO `tbl_admin_message` VALUES ('100', '恭喜！您的帖子已通过审核，点击查看', '255', '15230867500', '491602', '0', '-');
INSERT INTO `tbl_admin_message` VALUES ('101', '您的帖子 \"发表\" 被其他用户举报，现已被删除，请注意遵守社区规定！', '0', '15230867500', '', '0', '-');

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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_alarm` VALUES ('35', '19:35', '15194980385', '1523086750', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('36', '10:37', '15194980385', '18032168790', '别展示了，下来吧', '0');
INSERT INTO `tbl_alarm` VALUES ('37', '10:00', '15194980385', '18032168790', '………', '0');
INSERT INTO `tbl_alarm` VALUES ('38', '15:42', '15194980385', 'jpcaa', '6有6', '0');
INSERT INTO `tbl_alarm` VALUES ('39', '12:10', '老二', '15230867500', '妈，该吃降压药了', '0');
INSERT INTO `tbl_alarm` VALUES ('43', '15:21', '老二', '15230867500', '妈，改起来去晨练了！', '0');
INSERT INTO `tbl_alarm` VALUES ('44', '06:15', '15194980385', '妈(15230867500)', '妈，改起来去晨练了！', '0');
INSERT INTO `tbl_alarm` VALUES ('45', '06:15', '15194980385', '妈(15230867500)', '妈，改起来去晨练了！', '0');
INSERT INTO `tbl_alarm` VALUES ('46', '05:00', '15194980385', '爸(15194980385)', '妈，该吃降压药了', '0');
INSERT INTO `tbl_alarm` VALUES ('47', '06:15', '15194980385', '妈(15230867500)', '妈，改起来去晨练了！', '0');

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_answers
-- ----------------------------
INSERT INTO `tbl_answers` VALUES ('1', '13513171332', '谢谢支持，我们会继续努力的', '1', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('2', '15194980385', '谢谢！！', '2', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('3', '15227856991', '加油，构建美好社区！', '3', '1104a8979228f292c09');
INSERT INTO `tbl_answers` VALUES ('66', '15230867500', '尊敬的靳朋朝，你好：\r\n    巴拉巴拉', '29', '1507bfd3f74068c75d7');
INSERT INTO `tbl_answers` VALUES ('67', '15230867500', '收到测试~~~！！！', '30', '1507bfd3f74068c75d7');
INSERT INTO `tbl_answers` VALUES ('68', '15230867500', '000000000', '31', '1507bfd3f74068c75d7');
INSERT INTO `tbl_answers` VALUES ('69', '15230867500', '尊敬的xxx：\r\n您的反馈我们收到了！', '33', '1507bfd3f74068c75d7');
INSERT INTO `tbl_answers` VALUES ('70', '15230867500', '尊敬的xxx:\r\n您好 您的反馈我们收到了！', '33', '1507bfd3f74068c75d7');

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
INSERT INTO `tbl_child_userinfo` VALUES ('15194980385', '308462', '测', 'female', '天国', 'header15194980385');

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
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_comment` VALUES ('33', '125', '491602', 'abcd', 'header15230867500', '的', '2020-04-15 16:17:18');
INSERT INTO `tbl_comment` VALUES ('34', '126', '491602', 'abcd', 'header15230867500', '计算机', '2020-04-19 16:28:43');
INSERT INTO `tbl_comment` VALUES ('35', '129', '491602', 'abcd', 'header15230867500', '在你在家呢', '2020-04-24 19:13:48');
INSERT INTO `tbl_comment` VALUES ('36', '128', '491602', 'abcd', 'header15230867500', '日', '2020-04-24 21:03:06');
INSERT INTO `tbl_comment` VALUES ('37', '128', '491602', 'abcd', 'header15230867500', 'ok', '2020-04-24 21:03:10');
INSERT INTO `tbl_comment` VALUES ('38', '128', '491602', 'abcd', 'header15230867500', '尼玛', '2020-04-24 21:03:13');
INSERT INTO `tbl_comment` VALUES ('39', '130', '491602', 'abcd', 'header15230867500', '山', '2020-04-25 15:51:30');
INSERT INTO `tbl_comment` VALUES ('40', '130', '491602', 'abcd', 'header15230867500', '安安啦', '2020-04-25 15:51:33');
INSERT INTO `tbl_comment` VALUES ('41', '156', '491602', 'abcd', 'header15230867500', '牛逼', '2020-04-27 22:26:10');
INSERT INTO `tbl_comment` VALUES ('42', '161', '491602', 'abcd', 'header15230867500', '战局', '2020-05-20 18:54:21');
INSERT INTO `tbl_comment` VALUES ('43', '161', '491602', 'abcd', 'header15230867500', '发', '2020-05-22 16:52:53');
INSERT INTO `tbl_comment` VALUES ('44', '161', '491602', 'abcd', 'header15230867500', '下', '2020-05-22 16:52:55');
INSERT INTO `tbl_comment` VALUES ('45', '161', '491602', 'abcd', 'header15230867500', '等等', '2020-05-22 16:52:58');
INSERT INTO `tbl_comment` VALUES ('46', '161', '491602', 'abcd', 'header15230867500', '的东西', '2020-05-22 16:53:00');
INSERT INTO `tbl_comment` VALUES ('47', '199', '491602', 'abcd', 'header15230867500', '睡觉睡觉手机', '2020-05-23 17:21:06');
INSERT INTO `tbl_comment` VALUES ('48', '199', '491602', 'abcd', 'header15230867500', '待机时间多看看', '2020-05-23 17:21:09');
INSERT INTO `tbl_comment` VALUES ('49', '199', '491602', 'abcd', 'header15230867500', '择席', '2020-05-23 17:21:11');
INSERT INTO `tbl_comment` VALUES ('50', '180', '491602', 'abcd', 'header15230867500', '是男是女是你家的', '2020-05-24 18:19:46');
INSERT INTO `tbl_comment` VALUES ('51', '180', '491602', 'abcd', 'header15230867500', '在不在男士内裤收款方', '2020-05-24 18:19:49');
INSERT INTO `tbl_comment` VALUES ('52', '180', '491602', 'abcd', 'header15230867500', '占军', '2020-05-24 18:19:52');
INSERT INTO `tbl_comment` VALUES ('53', '247', '852000', 'mon', 'header15227856991', '优秀', '2020-05-26 07:47:17');
INSERT INTO `tbl_comment` VALUES ('54', '247', '491602', 'abcd', 'header15230867500', '分享', '2020-05-31 11:35:17');
INSERT INTO `tbl_comment` VALUES ('55', '251', '491602', 'abcd', 'header15230867500', '真好', '2020-06-01 15:32:32');

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_connect
-- ----------------------------
INSERT INTO `tbl_connect` VALUES ('33', '15194980385', '爸', '15194980385', '尿', '');
INSERT INTO `tbl_connect` VALUES ('34', '15230867500', '滚滚滚滚', '15230867500', '滚滚滚滚', '');
INSERT INTO `tbl_connect` VALUES ('35', '15230867500', '妈', '15194980385', '改', '');
INSERT INTO `tbl_connect` VALUES ('36', '15230867500', 'sunsunsun', '15194980385', '测', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;

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
INSERT INTO `tbl_goodpost` VALUES ('96', '180', '491602', '491602', '2020-05-24 18:16:46');
INSERT INTO `tbl_goodpost` VALUES ('97', '225', '491602', '491602', '2020-05-25 11:20:57');
INSERT INTO `tbl_goodpost` VALUES ('98', '232', '491602', '852000', '2020-05-25 21:18:20');
INSERT INTO `tbl_goodpost` VALUES ('99', '247', '852000', '852000', '2020-05-26 07:47:12');
INSERT INTO `tbl_goodpost` VALUES ('100', '232', '852000', '852000', '2020-05-26 07:47:38');
INSERT INTO `tbl_goodpost` VALUES ('101', '247', '491602', '852000', '2020-05-28 17:11:55');
INSERT INTO `tbl_goodpost` VALUES ('102', '250', '491602', '491602', '2020-05-30 21:02:35');
INSERT INTO `tbl_goodpost` VALUES ('103', '247', '', '852000', '2020-05-31 11:05:59');
INSERT INTO `tbl_goodpost` VALUES ('104', '251', '491602', '491602', '2020-06-01 15:32:33');
INSERT INTO `tbl_goodpost` VALUES ('105', '253', '491602', '491602', '2020-06-01 22:00:19');
INSERT INTO `tbl_goodpost` VALUES ('106', '253', '491602', '491602', '2020-06-01 22:00:23');
INSERT INTO `tbl_goodpost` VALUES ('107', '253', '491602', '491602', '2020-06-01 22:00:25');

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
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_myattentions
-- ----------------------------
INSERT INTO `tbl_myattentions` VALUES ('52', '491602', '852000');
INSERT INTO `tbl_myattentions` VALUES ('60', '852000', '491602');

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
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mycollection
-- ----------------------------
INSERT INTO `tbl_mycollection` VALUES ('131', '491602', '165', '2020-05-10 22:25:45');
INSERT INTO `tbl_mycollection` VALUES ('133', '491602', '167', '2020-05-11 17:52:24');
INSERT INTO `tbl_mycollection` VALUES ('134', '491602', '199', '2020-05-23 17:21:15');
INSERT INTO `tbl_mycollection` VALUES ('136', '491602', '180', '2020-05-24 18:16:48');
INSERT INTO `tbl_mycollection` VALUES ('137', '491602', '225', '2020-05-25 11:21:00');
INSERT INTO `tbl_mycollection` VALUES ('138', '491602', '232', '2020-05-26 08:09:50');
INSERT INTO `tbl_mycollection` VALUES ('139', '491602', '251', '2020-06-01 15:32:35');
INSERT INTO `tbl_mycollection` VALUES ('140', '491602', '253', '2020-06-01 22:00:20');
INSERT INTO `tbl_mycollection` VALUES ('141', '491602', '253', '2020-06-01 22:00:24');
INSERT INTO `tbl_mycollection` VALUES ('142', '491602', '253', '2020-06-01 22:00:25');

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
-- Table structure for `tbl_parentuser_reported`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_parentuser_reported`;
CREATE TABLE `tbl_parentuser_reported` (
  `phone` varchar(11) DEFAULT NULL,
  `nickName` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `personalWord` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  `headerImg` varchar(1024) DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `closeDays` int(10) DEFAULT NULL,
  `id` int(10) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_parentuser_reported
-- ----------------------------
INSERT INTO `tbl_parentuser_reported` VALUES ('15227856991', 'mon', '嘻嘻哈哈', 'header15227856991', '-', '0', '5');

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
  `imei` varchar(1024) DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `personalWord` varchar(255) DEFAULT NULL,
  `headimg` varchar(40) DEFAULT NULL,
  `closeDays` int(10) DEFAULT NULL,
  PRIMARY KEY (`phone`),
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_parent_userinfo
-- ----------------------------
INSERT INTO `tbl_parent_userinfo` VALUES ('13513171332', '254870', '春天的野菜', 'female', '江西省，南昌市，东湖区', '1968-10-10', '0', '封禁', '嘻嘻哈哈嘻嘻哈哈', '1083289628', '0');
INSERT INTO `tbl_parent_userinfo` VALUES ('15227856991', '852000', 'mon', 'female', '河北省，保定市，定州市', '1970-10-10', '0', null, '嘻嘻哈哈', 'header15227856991', null);
INSERT INTO `tbl_parent_userinfo` VALUES ('15230867500', '491602', 'abcd', 'female', '吉林省，长春市，朝阳区', '1968-07-14', '0', '封禁', '啦啦啦啦', 'header15230867500', '1');
INSERT INTO `tbl_parent_userinfo` VALUES ('kefuxw', '000000', '客服小薇', 'female', '石家庄', '1970-10-10', '0', null, '-', 'headerkefuxw', null);

-- ----------------------------
-- Table structure for `tbl_post`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `id` int(30) NOT NULL,
  `nickName` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL,
  `headimg` varchar(50) CHARACTER SET utf8mb4 DEFAULT NULL,
  `content` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `personId` varchar(30) CHARACTER SET utf8mb4 NOT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post
-- ----------------------------
INSERT INTO `tbl_post` VALUES ('232', 'mon', 'header15227856991', '图片加视频\n测\n试\n帖\n子', '852000', '2020-05-25 17:53:23', '[\"QQ空间视频_20200413223340.mp4\",\"IMG_20200407_001917.jpg\",\"951114803.jpg\"]');
INSERT INTO `tbl_post` VALUES ('244', 'abcd', 'header15230867500', '四图的显示格式\n纵\n向', '491602', '2020-05-25 21:38:46', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"1076553839.jpg\"]');
INSERT INTO `tbl_post` VALUES ('246', 'abcd', 'header15230867500', '发表了英语单词学习视频（单视频）', '491602', '2020-05-25 21:40:28', '[\"VID_20200525_164336.mp4\"]');
INSERT INTO `tbl_post` VALUES ('247', 'mon', 'header15227856991', '三图', '852000', '2020-05-26 07:44:41', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\"]');
INSERT INTO `tbl_post` VALUES ('251', 'abcd', 'header15230867500', '广场舞最新视频，大家快来看看学习啊！', '491602', '2020-05-30 22:14:50', '[\"421899789.mp4\"]');

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
  `phone` varchar(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `imgs` varchar(2048) DEFAULT NULL,
  `examine` varchar(20) NOT NULL,
  `rId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post_copy
-- ----------------------------
INSERT INTO `tbl_post_copy` VALUES ('213', 'sunsunsun', 'header15230867500', '单图', '491602', '15230867500', '2020-05-24 18:29:47', '[\"temp_photo1588250478407.jpg\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('232', 'mon', 'header15227856991', '图片加视频\n测\n试\n帖\n子', '852000', '15227856991', '2020-05-25 17:53:23', '[\"QQ空间视频_20200413223340.mp4\",\"IMG_20200407_001917.jpg\",\"951114803.jpg\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('244', 'sunsunsun', 'header15230867500', '四图的显示格式\n纵\n向', '491602', '15230867500', '2020-05-25 21:38:46', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"1076553839.jpg\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('246', 'sunsunsun', 'header15230867500', '发表了英语单词学习视频（单视频）', '491602', '15230867500', '2020-05-25 21:40:28', '[\"VID_20200525_164336.mp4\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('247', 'mon', 'header15227856991', '三图', '852000', '15227856991', '2020-05-26 07:44:41', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('248', 'sunsunsun', 'header15230867500', '发表图片', '491602', '15230867500', '2020-05-26 08:27:56', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"433503642_mh1547286904455.jpg\",\"1069983542.jpg\",\"1076553838.jpg\",\"1076553839.jpg\"]', '审核失败', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('249', 'sunsunsun', 'header15230867500', '测试测试帖子', '491602', '15230867500', '2020-05-26 08:31:12', '[\"1076553840.jpg\",\"433503642_mh1547286904455.jpg\",\"1069983542.jpg\",\"1095332074.jpg\",\"1098325509.jpg\",\"1076553838.jpg\"]', '审核失败', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('251', 'sunsunsun', 'header15230867500', '广场舞最新视频，大家快来看看学习啊！', '491602', '15230867500', '2020-05-30 22:14:50', '[\"421899789.mp4\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('252', 'sunsunsun', 'header15230867500', '发表', '491602', '15230867500', '2020-06-01 15:49:30', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"VID_20200525_164336.mp4\",\"1076553838.jpg\",\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"VID_20200525_164336.mp4\",\"1076553838.jpg\"]', '审核失败', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('253', 'sunsunsun', 'header15230867500', '发表', '491602', '15230867500', '2020-06-01 15:51:49', '[\"1083289628.jpg\",\"1076553841.jpg\",\"1076553840.jpg\",\"939444113.jpg\",\"1069983542.jpg\",\"1076553838.jpg\",\"VID_20200525_164336.mp4\"]', '已审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('254', 'abcd', 'header15230867500', '喝点水', '491602', '15230867500', '2020-06-02 22:27:02', '[]', '待审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('255', 'abcd', 'header15230867500', 'xxx', '491602', '15230867500', '2020-06-02 22:27:33', '[]', '待审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('256', 'abcd', 'header15230867500', '谢谢', '491602', '15230867500', '2020-06-02 22:28:26', '[]', '待审核', '1507bfd3f74068c75d7');
INSERT INTO `tbl_post_copy` VALUES ('257', 'abcd', 'header15230867500', 'hhh', '491602', '15230867500', '2020-06-02 22:38:06', '[]', '待审核', '1507bfd3f74068c75d7');

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
  `rId` varchar(1000) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_post_report
-- ----------------------------

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
  `answer_content` varchar(1024) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of tbl_questions
-- ----------------------------
INSERT INTO `tbl_questions` VALUES ('1', '姬妹妹', '13513171332', '1104a8979228f292c09', '这个APP太赞了', '真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好', '1', '谢谢支持，我们会继续努力的');
INSERT INTO `tbl_questions` VALUES ('2', '姬豆豆', '15194980385', '1104a8979228f292c09', '微家团队也太赞了吧！', '真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好真好', '1', '谢谢！！');
INSERT INTO `tbl_questions` VALUES ('3', '姬小生', '15227856991', '1104a8979228f292c09', '微家加油加油！！', '加油加油加油加油加油加油加油加油加油', '1', '加油，构建美好社区！');
INSERT INTO `tbl_questions` VALUES ('29', '靳朋朝', '15230867500', '1507bfd3f74068c75d7', '好评', '好，微家太棒了', '1', '尊敬的靳朋朝，你好：\r\n    巴拉巴拉');
INSERT INTO `tbl_questions` VALUES ('30', 'xx', '15230867500', '1507bfd3f74068c75d7', 'xxx', '测试', '1', '收到测试~~~！！！');
INSERT INTO `tbl_questions` VALUES ('31', 'xx', '15230867500', '1507bfd3f74068c75d7', 'x', '测测测', '1', '000000000');
INSERT INTO `tbl_questions` VALUES ('33', '靳朋朝', '15230867500', '1507bfd3f74068c75d7', 'bug反馈', 'xx处有bug', '1', '尊敬的xxx:\r\n您好 您的反馈我们收到了！');

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
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

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
INSERT INTO `tbl_relations_request` VALUES ('9', '15230867500', 'sun', '15227856991', '-1');
INSERT INTO `tbl_relations_request` VALUES ('10', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('11', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('12', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('13', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('14', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('15', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('16', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('17', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('18', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('19', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('20', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('21', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('22', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('23', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('24', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('25', '15194980385', '子女子女子女子女测测测测', '15230867500', '1');
INSERT INTO `tbl_relations_request` VALUES ('27', '15194980385', '测', '15230867500', '1');

-- ----------------------------
-- Table structure for `tbl_remind`
-- ----------------------------
DROP TABLE IF EXISTS `tbl_remind`;
CREATE TABLE `tbl_remind` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) DEFAULT NULL,
  `phone` char(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_remind
-- ----------------------------
INSERT INTO `tbl_remind` VALUES ('1', 'hahah', '242424000');
INSERT INTO `tbl_remind` VALUES ('2', '55555', '242424000');
INSERT INTO `tbl_remind` VALUES ('12', '妈，该吃降压药了', '15194980385');
INSERT INTO `tbl_remind` VALUES ('13', '妈，改起来去晨练了！', '15194980385');
INSERT INTO `tbl_remind` VALUES ('18', '新建常用提示', '15194980385');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reply_comment
-- ----------------------------
INSERT INTO `tbl_reply_comment` VALUES ('1', '53', '', '', '', '0', '棒棒棒！', '2020-05-31 11:33:41');
INSERT INTO `tbl_reply_comment` VALUES ('2', '53', 'abcd', 'header15230867500', '491602', '0', '哈哈 好美', '2020-05-31 11:34:51');
INSERT INTO `tbl_reply_comment` VALUES ('3', '55', 'abcd', 'header15230867500', '491602', '0', '谢谢', '2020-06-01 15:32:43');

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
