/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : center

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 15/12/2022 23:06:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for a_agent
-- ----------------------------
DROP TABLE IF EXISTS `a_agent`;
CREATE TABLE `a_agent`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `star` int(0) NULL DEFAULT NULL,
  `pool` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `limit` int(0) NULL DEFAULT 0 COMMENT '0->非限定\r\n1->周年限定\r\n2->联动限定\r\n3->五倍权值\r\n4->新年限定',
  PRIMARY KEY (`name`, `pool`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for a_nick_name
-- ----------------------------
DROP TABLE IF EXISTS `a_nick_name`;
CREATE TABLE `a_nick_name`  (
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`nick_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_bot
-- ----------------------------
DROP TABLE IF EXISTS `t_bot`;
CREATE TABLE `t_bot`  (
  `id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'botID',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '所属用户',
  `bot_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'bot名字',
  `ram` bigint(0) NULL DEFAULT NULL COMMENT '占用内存',
  `cpu` bigint(0) NULL DEFAULT NULL COMMENT '占用cpu',
  `send` bigint(0) NULL DEFAULT NULL COMMENT '总发送消息量',
  `receive` bigint(0) NULL DEFAULT NULL COMMENT '总接收消息量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '公告id，自增',
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `img` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_qq
-- ----------------------------
DROP TABLE IF EXISTS `t_qq`;
CREATE TABLE `t_qq`  (
  `qq` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'qq',
  `frame` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录框架',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录协议',
  `login_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后一次心跳时间',
  `bot_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属bot',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '所属用户',
  PRIMARY KEY (`qq`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id自增',
  `pwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码md5',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名，用于登录',
  `is_admin` int(0) NULL DEFAULT NULL COMMENT '是否管理0不是，1是',
  `token` bigint(0) NULL DEFAULT NULL COMMENT 'token余额',
  `use_token` bigint(0) NULL DEFAULT NULL COMMENT '已使用token',
  `count` bigint(0) NULL DEFAULT NULL COMMENT '调用次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_function`;
CREATE TABLE `t_function`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL AUTO_INCREMENT COMMENT '功能名',
  `bot_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属bot',
  `count` bigint(0) NULL DEFAULT NULL COMMENT '调用次数',
  PRIMARY KEY (`name`, `bot_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
