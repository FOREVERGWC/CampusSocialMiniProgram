/*
 Navicat Premium Dump SQL

 Source Server         : MYSQL8
 Source Server Type    : MySQL
 Source Server Version : 80038 (8.0.38)
 Source Host           : localhost:3306
 Source Schema         : 校园社交小程序

 Target Server Type    : MySQL
 Target Server Version : 80038 (8.0.38)
 File Encoding         : 65001

 Date: 19/01/2025 09:43:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_count_rate
-- ----------------------------
DROP TABLE IF EXISTS `biz_count_rate`;
CREATE TABLE `biz_count_rate`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rate_id` bigint NOT NULL COMMENT '评分ID',
  `count` bigint NOT NULL COMMENT '评分量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评分量表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of biz_count_rate
-- ----------------------------
INSERT INTO `biz_count_rate` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for biz_note
-- ----------------------------
DROP TABLE IF EXISTS `biz_note`;
CREATE TABLE `biz_note`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `category_id` bigint NOT NULL COMMENT '类别ID',
  `top` tinyint(1) NOT NULL COMMENT '置顶(0否、1是)',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '可见性(0私有、1公开)',
  `commentable` tinyint(1) NOT NULL COMMENT '允许评论(0否、1是)',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0未发布、1已发布)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除(0正常、1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_note
-- ----------------------------
INSERT INTO `biz_note` VALUES (1, 3, '标题', '内容', 1, 1, '1', 1, '1', '1', '2025-01-10 14:09:12', '1', '2025-01-11 15:12:31', '', 0);
INSERT INTO `biz_note` VALUES (2, 3, '测试2', '测试2', 3, 0, '1', 1, '1', '1', '2025-01-14 22:34:55', '1', '2025-01-14 22:34:55', '测试2', 0);
INSERT INTO `biz_note` VALUES (3, 3, '测试3', '测试3', 5, 0, '1', 0, '1', '1', '2025-01-14 22:35:23', '1', '2025-01-14 22:35:23', '', 0);

-- ----------------------------
-- Table structure for biz_note_category
-- ----------------------------
DROP TABLE IF EXISTS `biz_note_category`;
CREATE TABLE `biz_note_category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图标',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除(0正常、1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '笔记类别表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_note_category
-- ----------------------------
INSERT INTO `biz_note_category` VALUES (1, '全部', 'all', '1', '2025-01-09 21:05:01', '1', '2025-01-10 22:01:50', '', 0);
INSERT INTO `biz_note_category` VALUES (2, '美食', 'shop', '1', '2025-01-10 21:49:34', '1', '2025-01-10 21:55:23', '', 0);
INSERT INTO `biz_note_category` VALUES (3, '家居', 'home', '1', '2025-01-10 21:49:42', '1', '2025-01-10 21:55:29', '', 0);
INSERT INTO `biz_note_category` VALUES (4, '旅游', 'location', '1', '2025-01-10 21:49:48', '1', '2025-01-10 21:55:39', '', 0);
INSERT INTO `biz_note_category` VALUES (5, '摄影', 'camera', '1', '2025-01-10 21:50:00', '1', '2025-01-10 21:55:47', '', 0);
INSERT INTO `biz_note_category` VALUES (6, '运动', 'activity', '1', '2025-01-10 21:50:05', '1', '2025-01-10 21:55:53', '', 0);
INSERT INTO `biz_note_category` VALUES (7, '游戏', 'game', '1', '2025-01-10 21:50:11', '1', '2025-01-10 21:56:00', '', 0);
INSERT INTO `biz_note_category` VALUES (8, '兴趣', 'like', '1', '2025-01-10 21:50:17', '1', '2025-01-10 21:56:06', '', 0);
INSERT INTO `biz_note_category` VALUES (9, '学习', 'read', '1', '2025-01-10 21:50:22', '1', '2025-01-10 21:56:12', '', 0);
INSERT INTO `biz_note_category` VALUES (10, '护理', 'skin', '1', '2025-01-10 21:50:28', '1', '2025-01-10 21:56:19', '', 0);
INSERT INTO `biz_note_category` VALUES (11, '情感', 'evaluate', '1', '2025-01-10 21:50:46', '1', '2025-01-10 21:56:25', '', 0);

-- ----------------------------
-- Table structure for biz_rate
-- ----------------------------
DROP TABLE IF EXISTS `biz_rate`;
CREATE TABLE `biz_rate`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除(0正常、1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评分表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_rate
-- ----------------------------
INSERT INTO `biz_rate` VALUES (1, 3, '食堂最好吃的店铺', '你们认为食堂最好吃的店铺是哪一家？说出你的观点', '1', '2025-01-10 14:27:42', '1', '2025-01-10 14:32:33', '', 0);

-- ----------------------------
-- Table structure for biz_rate_item
-- ----------------------------
DROP TABLE IF EXISTS `biz_rate_item`;
CREATE TABLE `biz_rate_item`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rate_id` bigint NOT NULL COMMENT '评分ID',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除(0正常、1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评分项表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_rate_item
-- ----------------------------
INSERT INTO `biz_rate_item` VALUES (1, 1, '川渝小厨', '川渝小厨', '1', '2025-01-10 14:35:14', '1', '2025-01-18 21:33:36', '', 0);
INSERT INTO `biz_rate_item` VALUES (2, 1, '江湖烫', '江湖烫', '1', '2025-01-10 14:36:04', '1', '2025-01-10 14:36:04', '', 0);
INSERT INTO `biz_rate_item` VALUES (3, 1, '酸辣拌', '酸辣拌', '1', '2025-01-10 14:36:25', '1', '2025-01-10 14:36:25', '', 0);

-- ----------------------------
-- Table structure for biz_rate_record
-- ----------------------------
DROP TABLE IF EXISTS `biz_rate_record`;
CREATE TABLE `biz_rate_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `rate_id` bigint NOT NULL COMMENT '评分ID',
  `rate_item_id` bigint NOT NULL COMMENT '评分项ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `score` double NOT NULL COMMENT '分数',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评分记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of biz_rate_record
-- ----------------------------
INSERT INTO `biz_rate_record` VALUES (1, 1, 1, 3, 5.5, '1', '2025-01-10 15:25:30', '1', '2025-01-10 15:25:30', '一般');
INSERT INTO `biz_rate_record` VALUES (2, 1, 2, 3, 9, '1', '2025-01-10 15:29:06', '1', '2025-01-10 15:29:06', '神！');

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hash_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '散列值',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `bucket_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桶名',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件路径',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名称',
  `file_size` bigint NOT NULL COMMENT '文件大小',
  `chunk_total` int NOT NULL COMMENT '分片数量',
  `chunk_size` bigint NOT NULL COMMENT '分片大小',
  `status` tinyint(1) NOT NULL COMMENT '上传状态(0未完成、1已完成)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `hash_code`(`hash_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '附件表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
INSERT INTO `sys_attachment` VALUES (11, '26ffd0f90ea98d32d1d4136893fc747f', 1, 8, '', '/file/26ffd0f90ea98d32d1d4136893fc747f.png', '屏幕截图 2024-09-18 163252.png', 42633, 1, 10485760, 1, '', '2025-01-11 15:12:28', '', '2025-01-11 15:12:28', '');
INSERT INTO `sys_attachment` VALUES (12, '184a4ce9a181ba18f5875aef33462a0b', 1, 8, '', '/file/184a4ce9a181ba18f5875aef33462a0b.png', '屏幕截图 2024-12-29 085812.png', 6988, 1, 10485760, 1, '', '2025-01-11 15:12:30', '', '2025-01-11 15:12:30', '');
INSERT INTO `sys_attachment` VALUES (13, '5d08ced39910341325c102af785beb54', 3, 1, '', '/file/5d08ced39910341325c102af785beb54.png', '安恩溥.png', 65927, 1, 10485760, 1, '', '2025-01-15 21:36:07', '', '2025-01-15 21:36:07', '');
INSERT INTO `sys_attachment` VALUES (14, '56d6b8bd7ce6c764bcc854da0a532597', 1, 10, '', '/file/56d6b8bd7ce6c764bcc854da0a532597.png', '李济深.png', 52618, 1, 10485760, 1, '', '2025-01-18 21:33:33', '', '2025-01-18 21:33:33', '');

-- ----------------------------
-- Table structure for sys_attachment_chunk
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment_chunk`;
CREATE TABLE `sys_attachment_chunk`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `file_id` bigint NOT NULL COMMENT '文件ID',
  `hash_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '散列值',
  `chunk_index` int NOT NULL COMMENT '分片序号',
  `chunk_size` bigint NOT NULL COMMENT '分片大小',
  `status` tinyint(1) NOT NULL COMMENT '上传状态(0未完成、1已完成)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 691 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '附件分片表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_attachment_chunk
-- ----------------------------

-- ----------------------------
-- Table structure for sys_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_comment`;
CREATE TABLE `sys_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `reply_id` bigint NOT NULL COMMENT '回复ID',
  `reply_user_id` bigint NOT NULL COMMENT '回复用户ID',
  `ancestor_id` bigint NOT NULL COMMENT '祖级回复ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作系统',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP',
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP属地',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_comment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_count_comment
-- ----------------------------
DROP TABLE IF EXISTS `sys_count_comment`;
CREATE TABLE `sys_count_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `count` bigint NOT NULL COMMENT '评论',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论量表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_count_comment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_count_dislike
-- ----------------------------
DROP TABLE IF EXISTS `sys_count_dislike`;
CREATE TABLE `sys_count_dislike`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `count` bigint NOT NULL COMMENT '踩',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点踩量表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_count_dislike
-- ----------------------------

-- ----------------------------
-- Table structure for sys_count_favorite
-- ----------------------------
DROP TABLE IF EXISTS `sys_count_favorite`;
CREATE TABLE `sys_count_favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `count` bigint NOT NULL COMMENT '收藏',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏量表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_count_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for sys_count_like
-- ----------------------------
DROP TABLE IF EXISTS `sys_count_like`;
CREATE TABLE `sys_count_like`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `count` bigint NOT NULL COMMENT '赞',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '点赞量表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_count_like
-- ----------------------------
INSERT INTO `sys_count_like` VALUES (2, 1, 8, 1);
INSERT INTO `sys_count_like` VALUES (3, 3, 8, 1);

-- ----------------------------
-- Table structure for sys_count_view
-- ----------------------------
DROP TABLE IF EXISTS `sys_count_view`;
CREATE TABLE `sys_count_view`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `count` bigint NOT NULL COMMENT '浏览',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '浏览量表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_count_view
-- ----------------------------
INSERT INTO `sys_count_view` VALUES (1, 1, 8, 33);
INSERT INTO `sys_count_view` VALUES (2, 2, 8, 1);
INSERT INTO `sys_count_view` VALUES (3, 3, 8, 2);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `label` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标签',
  `value` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '键值',
  `type_id` bigint NOT NULL COMMENT '类型ID',
  `sort` int NOT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0禁用、1正常)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, '女', '0', 1, 1, '1', '1', '2024-09-25 22:56:53', '1', '2024-10-10 22:04:54', '');
INSERT INTO `sys_dict_data` VALUES (2, '男', '1', 1, 2, '1', '1', '2024-09-25 22:57:03', '1', '2024-10-10 22:04:57', '');
INSERT INTO `sys_dict_data` VALUES (3, '未知', '2', 1, 3, '1', '1', '2024-09-25 22:57:14', '1', '2024-10-10 22:05:00', '');
INSERT INTO `sys_dict_data` VALUES (4, '禁用', '0', 2, 1, '1', '1', '2024-10-10 22:37:46', '1', '2024-10-10 22:37:46', '');
INSERT INTO `sys_dict_data` VALUES (5, '正常', '1', 2, 2, '1', '1', '2024-10-10 22:38:05', '1', '2024-10-10 22:38:05', '');
INSERT INTO `sys_dict_data` VALUES (18, '未付款', '0', 6, 1, '1', '1', '2024-12-05 14:17:20', '1', '2024-12-05 14:17:20', '');
INSERT INTO `sys_dict_data` VALUES (19, '已付款', '1', 6, 2, '1', '1', '2024-12-05 14:17:28', '1', '2024-12-05 14:17:28', '');
INSERT INTO `sys_dict_data` VALUES (20, '未退款', '0', 7, 1, '1', '1', '2024-12-05 14:17:52', '1', '2024-12-05 14:17:52', '');
INSERT INTO `sys_dict_data` VALUES (21, '已退款', '1', 7, 2, '1', '1', '2024-12-05 14:17:59', '1', '2024-12-05 14:17:59', '');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典名称',
  `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典标识',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典状态(0禁用、1正常)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type`(`code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '性别', 'gender', '1', '1', '2024-09-25 22:56:38', '1', '2024-10-10 22:04:48', '');
INSERT INTO `sys_dict_type` VALUES (2, '启用状态', 'enable_status', '1', '1', '2024-10-10 22:36:51', '1', '2024-10-10 22:40:29', '');
INSERT INTO `sys_dict_type` VALUES (6, '付款状态', 'pay_status', '1', '1', '2024-12-05 14:16:36', '1', '2024-12-05 14:16:36', '');
INSERT INTO `sys_dict_type` VALUES (7, '退款状态', 'refund_status', '1', '1', '2024-12-05 14:16:56', '1', '2024-12-05 14:16:56', '');

-- ----------------------------
-- Table structure for sys_favorite
-- ----------------------------
DROP TABLE IF EXISTS `sys_favorite`;
CREATE TABLE `sys_favorite`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `biz_id` bigint NOT NULL COMMENT '业务ID',
  `biz_type` tinyint NOT NULL COMMENT '业务类型',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `biz_id`(`biz_id` ASC, `biz_type` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_favorite
-- ----------------------------

-- ----------------------------
-- Table structure for sys_follow
-- ----------------------------
DROP TABLE IF EXISTS `sys_follow`;
CREATE TABLE `sys_follow`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `follow_id` bigint NOT NULL COMMENT '关注用户ID',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0未关注、1已关注)',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '关注表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_follow
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `login_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录类型(1账密、2邮箱验证码、3手机验证码)',
  `os` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作系统',
  `browser` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览器',
  `ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP',
  `location` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'IP属地',
  `status` tinyint(1) NOT NULL COMMENT '状态(0失败、1成功)',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`create_time` DESC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5000266 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO `sys_log_login` VALUES (5000183, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:17:36', '1', '2025-01-09 20:17:36', '');
INSERT INTO `sys_log_login` VALUES (5000184, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:45:10', '1', '2025-01-09 20:45:10', '');
INSERT INTO `sys_log_login` VALUES (5000185, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:45:35', '1', '2025-01-09 20:45:35', '');
INSERT INTO `sys_log_login` VALUES (5000186, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:45:39', '1', '2025-01-09 20:45:39', '');
INSERT INTO `sys_log_login` VALUES (5000187, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:45:45', '1', '2025-01-09 20:45:45', '');
INSERT INTO `sys_log_login` VALUES (5000188, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:45:47', '1', '2025-01-09 20:45:47', '');
INSERT INTO `sys_log_login` VALUES (5000189, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:45:49', '1', '2025-01-09 20:45:49', '');
INSERT INTO `sys_log_login` VALUES (5000190, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:45:50', '1', '2025-01-09 20:45:50', '');
INSERT INTO `sys_log_login` VALUES (5000191, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:47:14', '1', '2025-01-09 20:47:14', '');
INSERT INTO `sys_log_login` VALUES (5000192, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:47:21', '1', '2025-01-09 20:47:21', '');
INSERT INTO `sys_log_login` VALUES (5000193, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:47:22', '1', '2025-01-09 20:47:22', '');
INSERT INTO `sys_log_login` VALUES (5000194, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:47:23', '1', '2025-01-09 20:47:23', '');
INSERT INTO `sys_log_login` VALUES (5000195, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:47:44', '1', '2025-01-09 20:47:44', '');
INSERT INTO `sys_log_login` VALUES (5000196, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:49:02', '1', '2025-01-09 20:49:02', '');
INSERT INTO `sys_log_login` VALUES (5000197, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-09 20:50:37', '1', '2025-01-09 20:50:37', '');
INSERT INTO `sys_log_login` VALUES (5000198, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, 'Bad credentials', '1', '2025-01-09 20:51:08', '1', '2025-01-09 20:51:08', '');
INSERT INTO `sys_log_login` VALUES (5000199, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 0, '用户帐号已被锁定', '1', '2025-01-09 20:59:54', '1', '2025-01-09 20:59:54', '');
INSERT INTO `sys_log_login` VALUES (5000200, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-09 21:00:27', '1', '2025-01-09 21:00:27', '');
INSERT INTO `sys_log_login` VALUES (5000201, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-09 21:03:55', '1', '2025-01-09 21:03:55', '');
INSERT INTO `sys_log_login` VALUES (5000202, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-09 21:15:17', '1', '2025-01-09 21:15:17', '');
INSERT INTO `sys_log_login` VALUES (5000203, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-09 21:17:27', '1', '2025-01-09 21:17:27', '');
INSERT INTO `sys_log_login` VALUES (5000204, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-09 21:19:36', '1', '2025-01-09 21:19:36', '');
INSERT INTO `sys_log_login` VALUES (5000205, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 21:42:53', '1', '2025-01-10 21:42:53', '');
INSERT INTO `sys_log_login` VALUES (5000206, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 21:48:17', '1', '2025-01-10 21:48:17', '');
INSERT INTO `sys_log_login` VALUES (5000207, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 22:00:55', '1', '2025-01-10 22:00:55', '');
INSERT INTO `sys_log_login` VALUES (5000208, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 22:04:32', '1', '2025-01-10 22:04:32', '');
INSERT INTO `sys_log_login` VALUES (5000209, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 22:04:49', '1', '2025-01-10 22:04:49', '');
INSERT INTO `sys_log_login` VALUES (5000210, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-10 22:05:43', '1', '2025-01-10 22:05:43', '');
INSERT INTO `sys_log_login` VALUES (5000211, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 22:08:23', '1', '2025-01-10 22:08:23', '');
INSERT INTO `sys_log_login` VALUES (5000212, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-10 22:10:25', '1', '2025-01-10 22:10:25', '');
INSERT INTO `sys_log_login` VALUES (5000213, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 13:41:14', '1', '2025-01-11 13:41:14', '');
INSERT INTO `sys_log_login` VALUES (5000214, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 13:44:26', '1', '2025-01-11 13:44:26', '');
INSERT INTO `sys_log_login` VALUES (5000215, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:17:25', '1', '2025-01-11 14:17:25', '');
INSERT INTO `sys_log_login` VALUES (5000216, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:18:25', '1', '2025-01-11 14:18:25', '');
INSERT INTO `sys_log_login` VALUES (5000217, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:20:15', '1', '2025-01-11 14:20:15', '');
INSERT INTO `sys_log_login` VALUES (5000218, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:20:22', '1', '2025-01-11 14:20:22', '');
INSERT INTO `sys_log_login` VALUES (5000219, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:20:43', '1', '2025-01-11 14:20:43', '');
INSERT INTO `sys_log_login` VALUES (5000220, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:22:03', '1', '2025-01-11 14:22:03', '');
INSERT INTO `sys_log_login` VALUES (5000221, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:24:20', '1', '2025-01-11 14:24:20', '');
INSERT INTO `sys_log_login` VALUES (5000222, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:26:45', '1', '2025-01-11 14:26:45', '');
INSERT INTO `sys_log_login` VALUES (5000223, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:28:03', '1', '2025-01-11 14:28:03', '');
INSERT INTO `sys_log_login` VALUES (5000224, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:28:53', '1', '2025-01-11 14:28:53', '');
INSERT INTO `sys_log_login` VALUES (5000225, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:31:42', '1', '2025-01-11 14:31:42', '');
INSERT INTO `sys_log_login` VALUES (5000226, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 14:41:42', '1', '2025-01-11 14:41:42', '');
INSERT INTO `sys_log_login` VALUES (5000227, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-11 15:02:53', '1', '2025-01-11 15:02:53', '');
INSERT INTO `sys_log_login` VALUES (5000228, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-14 21:58:18', '1', '2025-01-14 21:58:18', '');
INSERT INTO `sys_log_login` VALUES (5000229, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-14 22:06:14', '1', '2025-01-14 22:06:14', '');
INSERT INTO `sys_log_login` VALUES (5000230, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-14 22:06:17', '1', '2025-01-14 22:06:17', '');
INSERT INTO `sys_log_login` VALUES (5000231, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-14 22:06:55', '1', '2025-01-14 22:06:55', '');
INSERT INTO `sys_log_login` VALUES (5000232, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-14 22:08:36', '1', '2025-01-14 22:08:36', '');
INSERT INTO `sys_log_login` VALUES (5000233, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-14 22:12:57', '1', '2025-01-14 22:12:57', '');
INSERT INTO `sys_log_login` VALUES (5000234, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-14 22:27:35', '1', '2025-01-14 22:27:35', '');
INSERT INTO `sys_log_login` VALUES (5000235, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 20:10:48', '1', '2025-01-15 20:10:48', '');
INSERT INTO `sys_log_login` VALUES (5000236, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 20:27:44', '1', '2025-01-15 20:27:44', '');
INSERT INTO `sys_log_login` VALUES (5000237, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-15 20:30:10', '1', '2025-01-15 20:30:10', '');
INSERT INTO `sys_log_login` VALUES (5000238, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-15 20:33:14', '1', '2025-01-15 20:33:14', '');
INSERT INTO `sys_log_login` VALUES (5000239, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 20:37:48', '1', '2025-01-15 20:37:48', '');
INSERT INTO `sys_log_login` VALUES (5000240, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 20:49:03', '1', '2025-01-15 20:49:03', '');
INSERT INTO `sys_log_login` VALUES (5000241, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:10:42', '1', '2025-01-15 21:10:42', '');
INSERT INTO `sys_log_login` VALUES (5000242, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:14:51', '1', '2025-01-15 21:14:51', '');
INSERT INTO `sys_log_login` VALUES (5000243, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:25:03', '1', '2025-01-15 21:25:03', '');
INSERT INTO `sys_log_login` VALUES (5000244, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:34:57', '1', '2025-01-15 21:34:57', '');
INSERT INTO `sys_log_login` VALUES (5000245, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:37:24', '1', '2025-01-15 21:37:24', '');
INSERT INTO `sys_log_login` VALUES (5000246, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:40:46', '1', '2025-01-15 21:40:46', '');
INSERT INTO `sys_log_login` VALUES (5000247, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-15 21:44:33', '1', '2025-01-15 21:44:33', '');
INSERT INTO `sys_log_login` VALUES (5000248, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:48:14', '1', '2025-01-15 21:48:14', '');
INSERT INTO `sys_log_login` VALUES (5000249, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-15 21:50:13', '1', '2025-01-15 21:50:13', '');
INSERT INTO `sys_log_login` VALUES (5000250, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 20:38:23', '1', '2025-01-18 20:38:23', '');
INSERT INTO `sys_log_login` VALUES (5000251, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 20:53:13', '1', '2025-01-18 20:53:13', '');
INSERT INTO `sys_log_login` VALUES (5000252, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 20:57:14', '1', '2025-01-18 20:57:14', '');
INSERT INTO `sys_log_login` VALUES (5000253, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 21:19:48', '1', '2025-01-18 21:19:48', '');
INSERT INTO `sys_log_login` VALUES (5000254, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 21:25:26', '1', '2025-01-18 21:25:26', '');
INSERT INTO `sys_log_login` VALUES (5000255, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 0, 'Bad credentials', '1', '2025-01-18 21:31:45', '1', '2025-01-18 21:31:45', '');
INSERT INTO `sys_log_login` VALUES (5000256, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 21:31:48', '1', '2025-01-18 21:31:48', '');
INSERT INTO `sys_log_login` VALUES (5000257, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 21:51:33', '1', '2025-01-18 21:51:33', '');
INSERT INTO `sys_log_login` VALUES (5000258, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 22:28:13', '1', '2025-01-18 22:28:13', '');
INSERT INTO `sys_log_login` VALUES (5000259, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 22:28:13', '1', '2025-01-18 22:28:13', '');
INSERT INTO `sys_log_login` VALUES (5000260, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 22:31:30', '1', '2025-01-18 22:31:30', '');
INSERT INTO `sys_log_login` VALUES (5000261, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 22:32:09', '1', '2025-01-18 22:32:09', '');
INSERT INTO `sys_log_login` VALUES (5000262, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-18 23:00:22', '1', '2025-01-18 23:00:22', '');
INSERT INTO `sys_log_login` VALUES (5000263, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '未知 未知 未知', 1, '请求成功！', '1', '2025-01-18 23:09:52', '1', '2025-01-18 23:09:52', '');
INSERT INTO `sys_log_login` VALUES (5000264, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-19 00:00:43', '1', '2025-01-19 00:00:43', '');
INSERT INTO `sys_log_login` VALUES (5000265, '1', 'iPhone', 'MicroMessenger', '0:0:0:0:0:0:0:1', '%s %s %s', 1, '请求成功！', '1', '2025-01-19 00:01:49', '1', '2025-01-19 00:01:49', '');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图标',
  `parent_id` bigint NOT NULL COMMENT '父级菜单ID',
  `ancestor_id` bigint NOT NULL COMMENT '祖级菜单ID',
  `path` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '路由地址',
  `redirect` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '重定向地址',
  `component` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '组件路径',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '类型(1目录、2菜单、3按钮)',
  `sort` int NOT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0禁用、1正常)',
  `visible` tinyint(1) NOT NULL COMMENT '可见(0否、1是)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (12, '仪表盘', '仪表盘', 'Odometer', 22, 22, '/dashboard', '', '/backend/dashboard/index.vue', '2', 1, '1', 1, '1', '2024-09-02 16:58:27', '1', '2024-09-14 21:31:21', '');
INSERT INTO `sys_menu` VALUES (13, '系统管理', '系统管理', 'House', 0, 0, '/system', '/user', '/', '1', 2, '1', 1, '1', '2024-09-02 17:00:03', '1', '2024-09-02 17:00:03', '');
INSERT INTO `sys_menu` VALUES (14, '用户管理', '用户管理', 'User', 13, 13, '/user', '', '/backend/system/user/index.vue', '2', 1, '1', 1, '1', '2024-09-02 17:04:45', '1', '2024-09-02 17:04:45', '');
INSERT INTO `sys_menu` VALUES (15, '角色管理', '角色管理', 'Avatar', 13, 13, '/role', '', '/backend/system/role/index.vue', '2', 2, '1', 1, '1', '2024-09-02 17:05:38', '1', '2024-09-05 22:14:14', '');
INSERT INTO `sys_menu` VALUES (16, '菜单管理', '菜单管理', 'Menu', 13, 13, '/menu', '', '/backend/system/menu/index.vue', '2', 3, '1', 1, '1', '2024-09-02 17:07:21', '1', '2024-09-02 17:07:32', '');
INSERT INTO `sys_menu` VALUES (18, '二手车', '二手车', 'Document', 17, 17, '/car-index', '', '/backend/car/index.vue', '2', 1, '1', 1, '1', '2024-09-02 17:14:03', '1', '2024-11-15 11:21:29', '');
INSERT INTO `sys_menu` VALUES (19, '二手车审核', '二手车审核', 'AddLocation', 17, 17, '/carAudite', '', '/backend/car/audite/index.vue', '2', 5, '1', 1, '1', '2024-09-02 17:16:49', '1', '2025-01-07 16:16:42', '');
INSERT INTO `sys_menu` VALUES (20, '用户地址', '用户地址', 'BrushFilled', 17, 17, '/address', '', '/backend/address/index.vue', '2', 4, '1', 1, '1', '2024-09-02 17:17:49', '1', '2025-01-07 16:16:36', '');
INSERT INTO `sys_menu` VALUES (21, '权限管理', '权限管理', 'Stamp', 13, 13, '/permission', '', '/backend/system/permission/index.vue', '2', 4, '1', 1, '1', '2024-09-05 22:53:27', '1', '2024-09-05 22:53:41', '');
INSERT INTO `sys_menu` VALUES (22, '首页', '首页', 'HomeFilled', 0, 0, '/', '/dashboard', '/', '1', 1, '1', 1, '1', '2024-09-14 21:30:24', '1', '2024-09-15 18:19:03', '');
INSERT INTO `sys_menu` VALUES (23, '日志管理', '日志管理', 'Cellphone', 13, 13, '/log', '/log/login', '', '1', 6, '1', 1, '1', '2024-09-14 21:40:26', '1', '2024-09-25 22:46:40', '');
INSERT INTO `sys_menu` VALUES (24, '登录日志', '登录日志', 'Key', 23, 13, '/log/login', '', '/backend/system/log/login/index.vue', '2', 1, '1', 1, '1', '2024-09-14 21:42:41', '1', '2024-09-14 21:44:42', '');
INSERT INTO `sys_menu` VALUES (25, '字典管理', '字典管理', 'Apple', 13, 13, '/dict', '', '', '1', 5, '1', 1, '1', '2024-09-25 22:46:27', '1', '2025-01-10 15:34:49', '');
INSERT INTO `sys_menu` VALUES (26, '字典类型', '字典类型', 'Apple', 25, 13, '/dict/type', '', '/backend/system/dict/type/index.vue', '2', 1, '1', 1, '1', '2024-09-25 22:49:46', '1', '2025-01-10 15:35:09', '');
INSERT INTO `sys_menu` VALUES (27, '字典数据', '字典数据', 'Burger', 25, 13, '/dict/data', '', '/backend/system/dict/data/index.vue', '2', 2, '1', 1, '1', '2024-09-25 22:50:45', '1', '2025-01-10 15:35:17', '');
INSERT INTO `sys_menu` VALUES (28, '订单管理', '订单管理', 'Calendar', 17, 17, '/order', '', '/backend/order/index.vue', '2', 6, '1', 1, '1', '2024-12-05 14:13:59', '1', '2025-01-07 16:15:23', '');
INSERT INTO `sys_menu` VALUES (29, '二手车品牌', '二手车品牌', 'Cherry', 17, 17, '/carBrand', '', '/backend/car/brand/index.vue', '2', 2, '1', 1, '1', '2025-01-07 16:14:36', '1', '2025-01-07 16:15:43', '');
INSERT INTO `sys_menu` VALUES (30, '二手车型号', '二手车型号', 'Burger', 17, 17, '/carModel', '', '/backend/car/model/index.vue', '2', 3, '1', 1, '1', '2025-01-07 16:16:29', '1', '2025-01-07 16:17:12', '');
INSERT INTO `sys_menu` VALUES (31, '业务管理', '业务管理', 'AlarmClock', 0, 0, '/biz', '/note', '/', '1', 4, '1', 1, '1', '2025-01-10 13:23:35', '1', '2025-01-10 13:24:18', '');
INSERT INTO `sys_menu` VALUES (32, '笔记', '笔记', 'Apple', 31, 31, '/note', '', '/backend/biz/note/index.vue', '2', 1, '1', 1, '1', '2025-01-10 13:25:08', '1', '2025-01-10 14:00:20', '');
INSERT INTO `sys_menu` VALUES (33, '笔记类别', '笔记类别', 'Camera', 31, 31, '/note/category', '', '/backend/biz/note/category/index.vue', '2', 2, '1', 1, '1', '2025-01-10 13:52:37', '1', '2025-01-10 13:53:04', '');
INSERT INTO `sys_menu` VALUES (34, '评分', '评分', 'ArrowDown', 31, 31, '/rate', '', '/backend/biz/rate/index.vue', '2', 3, '1', 1, '1', '2025-01-10 13:57:17', '1', '2025-01-10 13:57:28', '');
INSERT INTO `sys_menu` VALUES (35, '评分项', '评分项', 'Camera', 31, 31, '/rate/item', '', '/backend/biz/rate/item/index.vue', '2', 4, '1', 1, '1', '2025-01-10 13:58:16', '1', '2025-01-10 13:58:33', '');
INSERT INTO `sys_menu` VALUES (36, '评分记录', '评分记录', 'Brush', 31, 31, '/rate/record', '', '/backend/biz/rate/record/index.vue', '2', 5, '1', 1, '1', '2025-01-10 13:59:06', '1', '2025-01-10 13:59:26', '');
INSERT INTO `sys_menu` VALUES (37, '附件管理', '附件管理', 'Files', 13, 13, '/attachment', '', '/backend/system/attachment/index.vue', '2', 9, '1', 1, '1', '2025-01-10 15:35:52', '1', '2025-01-10 15:44:37', '');
INSERT INTO `sys_menu` VALUES (38, '评论管理', '评论管理', 'ChatLineSquare', 13, 13, '/comment', '', '/backend/system/comment/index.vue', '2', 10, '1', 1, '1', '2025-01-10 15:39:33', '1', '2025-01-10 15:44:43', '');
INSERT INTO `sys_menu` VALUES (39, '收藏管理', '收藏管理', 'Collection', 13, 13, '/favorite', '', '/backend/system/favorite/index.vue', '2', 7, '1', 1, '1', '2025-01-10 15:42:39', '1', '2025-01-10 15:43:02', '');
INSERT INTO `sys_menu` VALUES (40, '关注管理', '关注管理', 'Avatar', 13, 13, '/follow', '', '/backend/system/follow/index.vue', '2', 8, '1', 1, '1', '2025-01-10 15:45:18', '1', '2025-01-10 15:45:30', '');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限标识',
  `parent_id` bigint NOT NULL COMMENT '父级权限ID',
  `ancestor_id` bigint NOT NULL COMMENT '祖级权限ID',
  `sort` int NOT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0禁用、1正常)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '用户添加', 'system:user:add', 11, 11, 1, '1', '1', '2024-09-05 22:58:46', '1', '2024-09-05 23:32:22', '');
INSERT INTO `sys_permission` VALUES (2, '用户删除', 'system:user:delete', 11, 11, 2, '1', '1', '2024-09-05 22:59:07', '1', '2024-09-05 23:32:37', '');
INSERT INTO `sys_permission` VALUES (3, '用户修改', 'system:user:edit', 11, 11, 3, '1', '1', '2024-09-05 22:59:38', '1', '2024-09-05 23:44:37', '');
INSERT INTO `sys_permission` VALUES (4, '用户列表', 'system:user:list', 11, 11, 4, '1', '1', '2024-09-05 23:00:37', '1', '2024-09-05 23:44:42', '');
INSERT INTO `sys_permission` VALUES (5, '用户查询', 'system:user:query', 11, 11, 5, '1', '1', '2024-09-05 23:05:18', '1', '2024-09-05 23:44:49', '');
INSERT INTO `sys_permission` VALUES (6, '角色添加', 'system:role:add', 12, 12, 1, '1', '1', '2024-09-05 23:11:59', '1', '2024-09-06 00:24:12', '');
INSERT INTO `sys_permission` VALUES (7, '角色删除', 'system:role:delete', 12, 12, 2, '1', '1', '2024-09-05 23:12:13', '1', '2024-09-06 00:24:16', '');
INSERT INTO `sys_permission` VALUES (8, '角色修改', 'system:role:edit', 12, 12, 3, '1', '1', '2024-09-05 23:12:29', '1', '2024-09-06 00:24:21', '');
INSERT INTO `sys_permission` VALUES (9, '角色列表', 'system:role:list', 12, 12, 4, '1', '1', '2024-09-05 23:12:50', '1', '2024-09-06 00:24:26', '');
INSERT INTO `sys_permission` VALUES (10, '角色查询', 'system:role:query', 12, 12, 5, '1', '1', '2024-09-05 23:13:13', '1', '2024-09-06 00:24:30', '');
INSERT INTO `sys_permission` VALUES (11, '用户', 'system:user:*', 0, 0, 1, '1', '1', '2024-09-05 23:14:40', '1', '2024-09-14 01:12:17', '');
INSERT INTO `sys_permission` VALUES (12, '角色', 'system:role:*', 0, 0, 2, '1', '1', '2024-09-05 23:14:57', '1', '2024-09-05 23:14:57', '');
INSERT INTO `sys_permission` VALUES (13, '菜单', 'system:menu:*', 0, 0, 3, '1', '1', '2024-09-06 01:05:09', '1', '2024-09-06 01:05:09', '');
INSERT INTO `sys_permission` VALUES (14, '菜单添加', 'system:menu:add', 13, 13, 1, '1', '1', '2024-09-06 01:51:27', '1', '2024-09-06 01:53:33', '');
INSERT INTO `sys_permission` VALUES (15, '菜单删除', 'system:menu:delete', 13, 13, 2, '1', '1', '2024-09-06 01:51:36', '1', '2024-09-06 01:53:42', '');
INSERT INTO `sys_permission` VALUES (16, '菜单修改', 'system:menu:edit', 13, 13, 3, '1', '1', '2024-09-06 01:51:50', '1', '2024-09-06 01:53:48', '');
INSERT INTO `sys_permission` VALUES (17, '菜单列表', 'system:menu:list', 13, 13, 4, '1', '1', '2024-09-06 01:52:21', '1', '2024-09-06 01:53:58', '');
INSERT INTO `sys_permission` VALUES (18, '菜单查询', 'system:menu:query', 13, 13, 5, '1', '1', '2024-09-06 01:52:34', '1', '2024-09-06 01:54:06', '');
INSERT INTO `sys_permission` VALUES (19, '权限', 'system:permission:*', 0, 0, 4, '1', '1', '2024-09-13 17:52:19', '1', '2024-09-13 17:52:56', '');
INSERT INTO `sys_permission` VALUES (20, '权限添加', 'system:permission:add', 19, 19, 1, '1', '1', '2024-09-13 17:53:20', '1', '2024-09-13 17:56:47', '');
INSERT INTO `sys_permission` VALUES (21, '权限删除', 'system:permission:delete', 19, 19, 2, '1', '1', '2024-09-13 17:53:27', '1', '2024-09-13 17:56:40', '');
INSERT INTO `sys_permission` VALUES (22, '权限修改', 'system:permission:edit', 19, 19, 3, '1', '1', '2024-09-13 17:53:36', '1', '2024-09-13 17:56:29', '');
INSERT INTO `sys_permission` VALUES (23, '权限列表', 'system:permission:list', 19, 19, 4, '1', '1', '2024-09-13 17:53:44', '1', '2024-09-13 17:56:20', '');
INSERT INTO `sys_permission` VALUES (24, '权限查询', 'system:permission:query', 19, 19, 5, '1', '1', '2024-09-13 17:54:03', '1', '2024-09-13 17:56:12', '');
INSERT INTO `sys_permission` VALUES (25, '角色分配菜单', 'system:role:assign:menu', 12, 12, 8, '1', '1', '2024-09-14 01:14:35', '1', '2024-09-24 19:56:09', '');
INSERT INTO `sys_permission` VALUES (26, '角色分配权限', 'system:role:assign:permission', 12, 12, 9, '1', '1', '2024-09-14 01:15:27', '1', '2024-09-24 19:56:14', '');
INSERT INTO `sys_permission` VALUES (27, '用户导入', 'system:user:import', 11, 11, 6, '1', '1', '2024-09-24 19:52:15', '1', '2024-09-24 19:52:15', '');
INSERT INTO `sys_permission` VALUES (28, '角色导入', 'system:role:import', 12, 12, 6, '1', '1', '2024-09-24 19:52:50', '1', '2024-09-24 19:52:50', '');
INSERT INTO `sys_permission` VALUES (29, '菜单导入', 'system:menu:import', 13, 13, 6, '1', '1', '2024-09-24 19:54:38', '1', '2024-09-24 19:54:38', '');
INSERT INTO `sys_permission` VALUES (30, '权限导入', 'system:permission:import', 19, 19, 6, '1', '1', '2024-09-24 19:55:07', '1', '2024-09-24 19:55:07', '');
INSERT INTO `sys_permission` VALUES (31, '用户导出', 'system:user:export', 11, 11, 7, '1', '1', '2024-09-24 19:55:40', '1', '2024-09-24 19:55:40', '');
INSERT INTO `sys_permission` VALUES (32, '角色导出', 'system:role:export', 12, 12, 7, '1', '1', '2024-09-24 19:56:04', '1', '2024-09-24 19:56:04', '');
INSERT INTO `sys_permission` VALUES (33, '菜单导出', 'system:menu:export', 13, 13, 7, '1', '1', '2024-09-24 19:57:30', '1', '2024-09-24 19:57:30', '');
INSERT INTO `sys_permission` VALUES (34, '权限导出', 'system:permission:export', 19, 19, 7, '1', '1', '2024-09-24 19:58:16', '1', '2024-09-24 19:58:16', '');
INSERT INTO `sys_permission` VALUES (35, '日志', 'system:log:*', 0, 0, 6, '1', '1', '2024-09-24 21:06:30', '1', '2024-09-25 22:27:06', '');
INSERT INTO `sys_permission` VALUES (36, '登录日志', 'system:log:login:*', 35, 35, 1, '1', '1', '2024-09-24 21:06:57', '1', '2024-09-24 21:06:57', '');
INSERT INTO `sys_permission` VALUES (37, '操作日志', 'system:log:operation:*', 35, 35, 2, '1', '1', '2024-09-24 21:07:37', '1', '2024-09-24 21:07:37', '');
INSERT INTO `sys_permission` VALUES (38, '字典', 'system:dict:*:*', 0, 0, 5, '1', '1', '2024-09-25 22:26:43', '1', '2024-09-25 22:26:59', '');
INSERT INTO `sys_permission` VALUES (39, '字典类型', 'system:dict:type:*', 38, 38, 1, '1', '1', '2024-09-25 22:27:41', '1', '2024-09-25 22:27:41', '');
INSERT INTO `sys_permission` VALUES (40, '字典数据', 'system:dict:data:*', 38, 38, 2, '1', '1', '2024-09-25 22:27:54', '1', '2024-09-25 22:27:54', '');
INSERT INTO `sys_permission` VALUES (41, '字典类型添加', 'system:dict:type:add', 39, 38, 1, '1', '1', '2024-09-25 22:28:53', '1', '2024-09-25 22:32:08', '');
INSERT INTO `sys_permission` VALUES (42, '字典类型删除', 'system:dict:type:delete', 39, 38, 2, '1', '1', '2024-09-25 22:29:01', '1', '2024-09-25 22:32:21', '');
INSERT INTO `sys_permission` VALUES (43, '字典类型修改', 'system:dict:type:edit', 39, 38, 3, '1', '1', '2024-09-25 22:29:11', '1', '2024-09-25 22:32:56', '');
INSERT INTO `sys_permission` VALUES (44, '字典类型列表', 'system:dict:type:list', 39, 38, 4, '1', '1', '2024-09-25 22:29:31', '1', '2024-09-25 22:33:04', '');
INSERT INTO `sys_permission` VALUES (45, '字典类型查询', 'system:dict:type:query', 39, 38, 5, '1', '1', '2024-09-25 22:29:46', '1', '2024-09-25 22:33:16', '');
INSERT INTO `sys_permission` VALUES (46, '字典类型导入', 'system:dict:type:import', 39, 38, 6, '1', '1', '2024-09-25 22:29:54', '1', '2024-09-25 22:33:27', '');
INSERT INTO `sys_permission` VALUES (47, '字典类型导出', 'system:dict:type:export', 39, 38, 7, '1', '1', '2024-09-25 22:30:14', '1', '2024-09-25 22:33:38', '');
INSERT INTO `sys_permission` VALUES (48, '字典数据添加', 'system:dict:data:add', 40, 38, 1, '1', '1', '2024-09-25 22:34:02', '1', '2024-09-25 22:36:39', '');
INSERT INTO `sys_permission` VALUES (49, '字典数据删除', 'system:dict:data:delete', 40, 38, 2, '1', '1', '2024-09-25 22:34:13', '1', '2024-09-25 22:37:33', '');
INSERT INTO `sys_permission` VALUES (50, '字典数据修改', 'system:dict:data:edit', 40, 38, 3, '1', '1', '2024-09-25 22:34:22', '1', '2024-09-25 22:36:47', '');
INSERT INTO `sys_permission` VALUES (51, '字典数据列表', 'system:dict:data:list', 40, 38, 4, '1', '1', '2024-09-25 22:34:30', '1', '2024-09-25 22:37:20', '');
INSERT INTO `sys_permission` VALUES (52, '字典数据查询', 'system:dict:data:query', 40, 38, 5, '1', '1', '2024-09-25 22:34:39', '1', '2024-09-25 22:37:11', '');
INSERT INTO `sys_permission` VALUES (53, '字典数据导入', 'system:dict:data:import', 40, 38, 6, '1', '1', '2024-09-25 22:34:47', '1', '2024-09-25 22:36:55', '');
INSERT INTO `sys_permission` VALUES (54, '字典数据导出', 'system:dict:data:export', 40, 38, 7, '1', '1', '2024-09-25 22:34:55', '1', '2024-09-25 22:37:03', '');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色名称',
  `sort` int NOT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0禁用、1正常)',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  `deleted` tinyint(1) NOT NULL COMMENT '逻辑删除(0正常、1删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', 1, '1', '1', '2024-08-19 20:29:06', '1', '2024-08-19 20:39:39', '', 0);
INSERT INTO `sys_role` VALUES (2, '用户', 3, '1', '1', '2024-08-19 20:29:28', '1', '2024-11-19 14:08:26', '', 0);
INSERT INTO `sys_role` VALUES (3, '游客', 3, '1', '1', '2024-08-19 20:29:38', '1', '2024-09-25 00:11:45', '', 0);
INSERT INTO `sys_role` VALUES (4, '测试1', 1, '1', '1', '2024-08-19 20:35:04', '1', '2024-08-21 11:32:50', '', 1);
INSERT INTO `sys_role` VALUES (5, '员工', 2, '1', '1', '2024-11-19 14:08:18', '1', '2024-11-19 14:08:18', '', 0);

-- ----------------------------
-- Table structure for sys_role_menu_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu_link`;
CREATE TABLE `sys_role_menu_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 348 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色、菜单关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu_link
-- ----------------------------
INSERT INTO `sys_role_menu_link` VALUES (237, 5, 12, '1', '2025-01-10 12:44:47', '1', '2025-01-10 12:44:47', '');
INSERT INTO `sys_role_menu_link` VALUES (238, 5, 22, '1', '2025-01-10 12:44:47', '1', '2025-01-10 12:44:47', '');
INSERT INTO `sys_role_menu_link` VALUES (239, 2, 12, '1', '2025-01-10 12:44:53', '1', '2025-01-10 12:44:53', '');
INSERT INTO `sys_role_menu_link` VALUES (240, 2, 22, '1', '2025-01-10 12:44:53', '1', '2025-01-10 12:44:53', '');
INSERT INTO `sys_role_menu_link` VALUES (241, 3, 12, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (242, 3, 13, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (243, 3, 14, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (244, 3, 15, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (245, 3, 16, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (246, 3, 21, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (247, 3, 22, '1', '2025-01-10 12:44:59', '1', '2025-01-10 12:44:59', '');
INSERT INTO `sys_role_menu_link` VALUES (326, 1, 12, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (327, 1, 13, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (328, 1, 14, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (329, 1, 15, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (330, 1, 16, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (331, 1, 21, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (332, 1, 22, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (333, 1, 23, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (334, 1, 24, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (335, 1, 25, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (336, 1, 26, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (337, 1, 27, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (338, 1, 31, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (339, 1, 32, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (340, 1, 33, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (341, 1, 36, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (342, 1, 35, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (343, 1, 34, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (344, 1, 37, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (345, 1, 38, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (346, 1, 39, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');
INSERT INTO `sys_role_menu_link` VALUES (347, 1, 40, '1', '2025-01-10 15:45:41', '1', '2025-01-10 15:45:41', '');

-- ----------------------------
-- Table structure for sys_role_permission_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_link`;
CREATE TABLE `sys_role_permission_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `permission_id` bigint NOT NULL COMMENT '权限ID',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 209 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色、权限关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission_link
-- ----------------------------
INSERT INTO `sys_role_permission_link` VALUES (89, 3, 4, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (90, 3, 5, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (91, 3, 10, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (92, 3, 9, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (93, 3, 17, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (94, 3, 18, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (95, 3, 24, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (96, 3, 23, '1', '2024-09-14 01:20:27', '1', '2024-09-14 01:20:27', '');
INSERT INTO `sys_role_permission_link` VALUES (114, 2, 13, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (115, 2, 3, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (116, 2, 5, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (117, 2, 10, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (118, 2, 17, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (119, 2, 18, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (120, 2, 23, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (121, 2, 24, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (122, 2, 4, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (123, 2, 9, '1', '2024-09-14 07:22:04', '1', '2024-09-14 07:22:04', '');
INSERT INTO `sys_role_permission_link` VALUES (158, 1, 1, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (159, 1, 2, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (160, 1, 3, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (161, 1, 4, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (162, 1, 5, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (163, 1, 12, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (164, 1, 6, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (165, 1, 7, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (166, 1, 8, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (167, 1, 9, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (168, 1, 10, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (169, 1, 13, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (170, 1, 14, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (171, 1, 15, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (172, 1, 16, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (173, 1, 17, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (174, 1, 18, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (175, 1, 19, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (176, 1, 20, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (177, 1, 21, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (178, 1, 22, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (179, 1, 23, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (180, 1, 24, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (181, 1, 25, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (182, 1, 26, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (183, 1, 11, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (184, 1, 31, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (185, 1, 27, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (186, 1, 28, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (187, 1, 32, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (188, 1, 29, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (189, 1, 33, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (190, 1, 30, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (191, 1, 34, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (192, 1, 38, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (193, 1, 39, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (194, 1, 41, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (195, 1, 42, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (196, 1, 43, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (197, 1, 44, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (198, 1, 45, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (199, 1, 46, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (200, 1, 47, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (201, 1, 40, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (202, 1, 48, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (203, 1, 49, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (204, 1, 50, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (205, 1, 51, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (206, 1, 52, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (207, 1, 54, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');
INSERT INTO `sys_role_permission_link` VALUES (208, 1, 53, '1', '2024-09-25 22:54:29', '1', '2024-09-25 22:54:29', '');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '昵称',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别(0女、1男、2未知)',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态(0禁用、1正常)',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '微信小程序开放ID',
  `balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '余额',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后登录IP',
  `login_time` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (3, '1', '$2a$10$3alyCOMoZZt39BASQUwwTOrGodFCjiwMgHurikWrqAhINrIvDbfqG', '', '管理员', '/file/5d08ced39910341325c102af785beb54.png', '2', '2024-08-01', '1', '13037503398', '916586595@qq.com', '1', 0.00, '0:0:0:0:0:0:0:1', '2025-01-19 00:01:48', '', '2024-08-16 01:26:41', '', '2025-01-19 00:01:48', '');
INSERT INTO `sys_user` VALUES (4, '2', '$2a$10$elhEi/ohemfnXateL1BLZ.lLi.fJ31tDVKdSpr3xnr40pdMjAlqlG', '', '张三', '1', '2', '2024-08-22', '1', '13037503390', '1@qq.com', '1', 0.00, '0:0:0:0:0:0:0:1', '2024-11-25 16:38:54', '', '2024-08-16 09:00:11', '', '2024-11-25 16:38:54', '');
INSERT INTO `sys_user` VALUES (5, '3', '$2a$10$3alyCOMoZZt39BASQUwwTOrGodFCjiwMgHurikWrqAhINrIvDbfqG', '1', '1', '/file/c3f7a394-7b91-43b3-b924-5d1592426f06.jpg', '2', '2024-08-27', '1', '13037503391', '2@qq.com', '1', 0.00, '', NULL, '1', '2024-08-21 14:25:56', '1', '2024-08-21 14:25:56', '');
INSERT INTO `sys_user` VALUES (6, '4', '$2a$10$3alyCOMoZZt39BASQUwwTOrGodFCjiwMgHurikWrqAhINrIvDbfqG', '1', '1', '/file/a3336d6e-4ef8-46f0-99e6-a104122b9f88.jpg', '2', '2024-08-17', '0', '13037503392', '3@qq.com', '1', 0.00, '', NULL, '1', '2024-08-21 14:34:13', '1', '2024-08-21 15:13:15', '1');
INSERT INTO `sys_user` VALUES (10, '11', '$2a$10$3alyCOMoZZt39BASQUwwTOrGodFCjiwMgHurikWrqAhINrIvDbfqG', '11', '', '', '2', '2024-10-04', '1', '13037503314', '4@qq.com', '', 0.00, '', NULL, '', '2024-09-13 23:42:22', '', '2024-10-11 13:46:24', '');

-- ----------------------------
-- Table structure for sys_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_auth`;
CREATE TABLE `sys_user_auth`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `auth_type` int NOT NULL COMMENT '认证方式(0微信)',
  `open_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '唯一标识',
  `access_token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '授权令牌',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC, `auth_type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户三方授权表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_auth
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role_link
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_link`;
CREATE TABLE `sys_user_role_link`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '修改者',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC, `role_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户、角色关系表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role_link
-- ----------------------------
INSERT INTO `sys_user_role_link` VALUES (13, 5, 2, '1', '2024-08-21 14:25:56', '1', '2024-08-21 14:25:56', '');
INSERT INTO `sys_user_role_link` VALUES (14, 5, 3, '1', '2024-08-21 14:25:56', '1', '2024-08-21 14:25:56', '');
INSERT INTO `sys_user_role_link` VALUES (21, 6, 2, '1', '2024-08-21 14:58:22', '1', '2024-08-21 14:58:22', '');
INSERT INTO `sys_user_role_link` VALUES (22, 3, 1, '1', '2024-08-21 15:13:59', '1', '2024-08-21 15:13:59', '');
INSERT INTO `sys_user_role_link` VALUES (23, 7, 2, '', '2024-09-02 15:48:49', '', '2024-09-02 15:48:49', '');
INSERT INTO `sys_user_role_link` VALUES (24, 8, 2, '', '2024-09-02 15:48:55', '', '2024-09-02 15:48:55', '');
INSERT INTO `sys_user_role_link` VALUES (26, 10, 2, '', '2024-09-13 23:42:22', '', '2024-09-13 23:42:22', '');
INSERT INTO `sys_user_role_link` VALUES (27, 4, 5, '1', '2024-11-19 16:08:39', '1', '2024-11-19 16:08:39', '');

SET FOREIGN_KEY_CHECKS = 1;
