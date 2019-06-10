/*
 Navicat Premium Data Transfer

 Source Server         : docker-mysql
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : kefu

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 08/06/2019 23:21:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_answer
-- ----------------------------
DROP TABLE IF EXISTS `tb_answer`;
CREATE TABLE `tb_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '答案编号',
  `content` text COMMENT '答案内容',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答案表';

-- ----------------------------
-- Table structure for tb_conversation
-- ----------------------------
DROP TABLE IF EXISTS `tb_conversation`;
CREATE TABLE `tb_conversation` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会话编号',
  `from_user_id` int(11) DEFAULT NULL COMMENT '发送方用户编号',
  `to_user_id` int(11) DEFAULT NULL COMMENT '接收方用户编号',
  `unread_count` int(11) DEFAULT '0' COMMENT '未读数量',
  `last_message_id` int(11) DEFAULT NULL COMMENT '最后一条消息编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='会话表';

-- ----------------------------
-- Records of tb_conversation
-- ----------------------------
BEGIN;
INSERT INTO `tb_conversation` VALUES (2, 6, 1, 0, NULL, '2019-06-08 15:12:10', '2019-06-08 15:12:10');
COMMIT;

-- ----------------------------
-- Table structure for tb_faq
-- ----------------------------
DROP TABLE IF EXISTS `tb_faq`;
CREATE TABLE `tb_faq` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '常见问题编号',
  `user_id` int(11) NOT NULL COMMENT '创建者',
  `team_id` int(11) NOT NULL COMMENT '所属团队编号',
  `title` varchar(255) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `sort` int(4) DEFAULT NULL COMMENT '排序 1开始',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='常见问题表';

-- ----------------------------
-- Records of tb_faq
-- ----------------------------
BEGIN;
INSERT INTO `tb_faq` VALUES (1, 1, 1, '测试标题', '<ol><li>催地方了建档立卡发简历上江东父老积分</li><li>地方地方加端口房价砥砺奋进</li><li>地方就到拉萨附近拉伸就地方了&nbsp;</li></ol>', 6, '2019-06-03 10:58:48', '2019-06-03 14:34:12');
INSERT INTO `tb_faq` VALUES (2, 1, 1, '测试文章', '<p>这是内容，随便写</p>', 5, '2019-06-03 11:17:15', '2019-06-03 13:21:31');
INSERT INTO `tb_faq` VALUES (3, 1, 1, '测试表啊啊啊啊', '', 4, '2019-06-03 13:00:14', '2019-06-03 13:21:28');
INSERT INTO `tb_faq` VALUES (4, 1, 1, '测试案例拉啊', '<p>sdfsdfdf 阿杜放到发</p>', 3, '2019-06-03 13:01:28', '2019-06-03 13:21:25');
INSERT INTO `tb_faq` VALUES (5, 1, 1, 'sdfdf324234', '<p>大是大非阿道夫地方&nbsp;<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\"></p>', 2, '2019-06-03 13:03:11', '2019-06-03 14:30:01');
INSERT INTO `tb_faq` VALUES (6, 1, 1, '测试啊阿杜发的123123', '<p>打的费地方第三方地方 地方发地方<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\">&nbsp;带图标</p><table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr><th>是&nbsp;</th><th>是&nbsp;</th><th>&nbsp;是</th><th>&nbsp;</th><th>&nbsp;</th></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>是&nbsp;</td><td>&nbsp;是</td><td>&nbsp;是</td></tr><tr><td>&nbsp;是</td><td>&nbsp;是</td><td>&nbsp;是s</td><td>&nbsp;是</td><td>&nbsp;</td></tr><tr><td>&nbsp;是</td><td>&nbsp;是</td><td>&nbsp;</td><td>&nbsp;是</td><td>是&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;是</td><td>&nbsp;是</td><td>&nbsp;是</td></tr></tbody></table><p><br></p>', 1, '2019-06-03 13:15:46', '2019-06-03 14:37:21');
COMMIT;

-- ----------------------------
-- Table structure for tb_login_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_log`;
CREATE TABLE `tb_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '登录日志编号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户编号',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录IP',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `address` varchar(255) DEFAULT NULL COMMENT '登录地点',
  `login_result` int(4) DEFAULT NULL COMMENT '登录结果 [0.失败 1.成功]',
  `content` varchar(255) DEFAULT NULL COMMENT '日志内容',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='登录日志表';

-- ----------------------------
-- Table structure for tb_message
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息编号',
  `content` text COMMENT '消息内容',
  `from_user_id` int(11) DEFAULT NULL COMMENT '发送方用户编号',
  `to_user_id` int(11) DEFAULT NULL COMMENT '接收方用户编号',
  `type` int(4) DEFAULT NULL COMMENT '消息类型 [1.文字 2.图片]',
  `status` int(4) DEFAULT '1' COMMENT '消息状态 [1.未读 2.已读]',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='聊天信息表';

-- ----------------------------
-- Records of tb_message
-- ----------------------------
BEGIN;
INSERT INTO `tb_message` VALUES (1, '测试下', 1, 5, 1, 1, '2019-06-08 05:44:01', '2019-06-08 05:44:01');
INSERT INTO `tb_message` VALUES (2, '测试', 5, 1, 1, 1, '2019-06-08 05:58:57', '2019-06-08 05:58:57');
INSERT INTO `tb_message` VALUES (3, '测试', 5, 1, 1, 1, '2019-06-08 06:02:58', '2019-06-08 06:02:58');
INSERT INTO `tb_message` VALUES (4, '测试啊', 5, 5, 1, 1, '2019-06-08 06:05:58', '2019-06-08 06:05:58');
INSERT INTO `tb_message` VALUES (5, '测试啊', 5, 1, 1, 1, '2019-06-08 06:06:19', '2019-06-08 06:06:19');
INSERT INTO `tb_message` VALUES (6, '测试啊 ', 5, 1, 1, 1, '2019-06-08 06:21:01', '2019-06-08 06:21:01');
INSERT INTO `tb_message` VALUES (7, '测试啊 ', 5, 1, 1, 1, '2019-06-08 06:23:01', '2019-06-08 06:23:01');
INSERT INTO `tb_message` VALUES (8, '发送信息', 6, 1, 1, 1, '2019-06-08 15:19:34', '2019-06-08 15:19:34');
COMMIT;

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限编号',
  `parent_id` int(11) NOT NULL COMMENT '上级编号',
  `path` varchar(255) DEFAULT NULL COMMENT '路由路径,path',
  `component` varchar(255) DEFAULT NULL COMMENT '路由组件,component',
  `resources` varchar(255) DEFAULT NULL COMMENT '权限标识,meta.resources',
  `name` varchar(255) NOT NULL COMMENT '权限名称,meta.title或按钮名称',
  `icon` varchar(255) DEFAULT NULL COMMENT '权限图标,meta.icon',
  `type` varchar(255) NOT NULL DEFAULT 'menu' COMMENT '权限类型 [menu.菜单 button.按钮]',
  `sort` int(4) DEFAULT NULL COMMENT '目录排序 1开始',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
BEGIN;
INSERT INTO `tb_permission` VALUES (1, 0, 'system', '', '', '系统管理', 'system', 'menu', 1, '系统管理员菜单', NULL, '2019-06-02 05:58:56');
INSERT INTO `tb_permission` VALUES (2, 1, 'user', 'system/user/index', 'system:user:view', '用户管理', 'peoples', 'menu', 1, '系统管理员菜单', NULL, '2019-06-02 06:10:29');
INSERT INTO `tb_permission` VALUES (3, 1, 'role', 'system/role/index', 'system:role:view', '角色管理', 'role', 'menu', 2, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (4, 1, 'permission', 'system/permission/index', 'system:permission:view', '权限管理', 'permission', 'menu', 3, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (5, 1, 'team', 'system/team/index', 'system:team:view', '团队管理', 'team', 'menu', 4, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (6, 1, 'faq', 'system/faq/index', 'system:faq:view', '常见问题管理', 'faq', 'menu', 5, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (7, 1, 'asklib', 'system/asklib/index', 'system:asklib:view', '问答库管理', 'asklib', 'menu', 6, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (8, 0, 'monitor', '', '', '监控管理', 'monitor', 'menu', 2, '系统管理员菜单', NULL, '2019-06-02 06:05:50');
INSERT INTO `tb_permission` VALUES (9, 8, 'online', 'monitor/online/index', 'monitor:online:view', '在线用户', 'online-user', 'menu', 1, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (10, 8, 'data', 'monitor/data/index', 'monitor:data:view', '数据监控', 'data-monitor', 'menu', 2, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (11, 8, 'loginlog', 'monitor/loginlog/index', 'monitor:loginlog:view', '登录日志', 'loginlog', 'menu', 3, '系统管理员菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (12, 0, 'chat', '', '', '访客咨询', 'chat', 'menu', 3, '用户菜单', NULL, '2019-06-02 05:59:12');
INSERT INTO `tb_permission` VALUES (13, 12, 'chat', 'chat/chat/index', 'chat:chat:view', '访客咨询', 'chat', 'menu', 1, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (14, 0, 'team', '', '', '团队管理', 'team', 'menu', 4, '用户菜单', NULL, '2019-06-02 05:59:19');
INSERT INTO `tb_permission` VALUES (15, 14, 'team', 'team/team/index', 'team:team:view', '我的团队', 'my-team', 'menu', 1, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (16, 14, 'chat', 'team/chat/index', 'team:chat:view', '聊天记录', 'chat-record', 'menu', 2, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (17, 14, 'visitor', 'team/visitor/index', 'team:visitor:view', '访客管理', 'visitor', 'menu', 3, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (18, 0, 'knowledge', '', '', '知识库', 'knowledge', 'menu', 5, '用户菜单', NULL, '2019-06-02 05:59:26');
INSERT INTO `tb_permission` VALUES (19, 18, 'faq', 'knowledge/faq/index', 'knowledge:faq:view', '常见问题', 'faq', 'menu', 1, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (20, 18, 'asklib', 'knowledge/asklib/index', 'knowledge:asklib:view', '问答库', 'asklib', 'menu', 2, '用户菜单', NULL, NULL);
INSERT INTO `tb_permission` VALUES (21, 2, '', '', 'system:user:add', '用户新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (22, 2, NULL, NULL, 'system:user:edit', '用户修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (23, 2, NULL, NULL, 'system:user:delete', '用户删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (24, 3, NULL, NULL, 'system:role:add', '角色新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (25, 3, NULL, NULL, 'system:role:edit', '角色修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (26, 3, NULL, NULL, 'system:role:delete', '角色删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (27, 4, NULL, NULL, 'system:permission:add', '权限新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (28, 4, NULL, NULL, 'system:permission:edit', '权限修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (29, 4, NULL, NULL, 'system:permission:delete', '权限删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (30, 5, NULL, NULL, 'system:team:add', '团队新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (31, 5, NULL, NULL, 'system:team:edit', '团队修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (32, 5, NULL, NULL, 'system:team:delete', '团队删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (33, 6, NULL, NULL, 'system:faq:add', '常见问题新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (34, 6, NULL, NULL, 'system:faq:edit', '常见问题修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (35, 6, NULL, NULL, 'system:faq:delete', '常见问题删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (36, 7, NULL, NULL, 'system:asklib:add', '问答库新增', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (37, 7, NULL, NULL, 'system:asklib:edit', '问答库修改', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
INSERT INTO `tb_permission` VALUES (38, 7, NULL, NULL, 'system:asklib:delete', '问答库删除', NULL, 'button', NULL, '系统管理员按钮', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_question`;
CREATE TABLE `tb_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题编号',
  `content` varchar(255) DEFAULT NULL COMMENT '问题内容',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题表';

-- ----------------------------
-- Table structure for tb_question_answer
-- ----------------------------
DROP TABLE IF EXISTS `tb_question_answer`;
CREATE TABLE `tb_question_answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题与答案关系编号',
  `question_id` int(11) DEFAULT NULL COMMENT '问题编号',
  `answer` int(11) DEFAULT NULL COMMENT '答案编号',
  `hit_count` int(11) DEFAULT NULL COMMENT '匹配次数',
  `success_count` int(11) DEFAULT NULL COMMENT '匹配成功次数',
  `team_id` int(11) DEFAULT NULL COMMENT '团队编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='问题与答案关联表';

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色编号',
  `name_en` varchar(255) NOT NULL COMMENT '角色英文名称',
  `name_zh` varchar(255) NOT NULL COMMENT '角色中文名称',
  `level` int(4) DEFAULT NULL COMMENT '角色级别 [级别越低，权限越高 最低1开始]',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name_en` (`name_en`) USING BTREE COMMENT '角色英文名称唯一'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, 'ROLE_ROOT_ADMIN', '超级管理员', 1, '超级管理员', '2019-05-20 06:32:29', '2019-05-20 06:32:29');
INSERT INTO `tb_role` VALUES (2, 'ROLE_TEAM_ADMIN', '团队管理员', 2, '团队的管理员', '2019-05-22 10:55:24', '2019-05-22 10:55:24');
INSERT INTO `tb_role` VALUES (3, 'ROLE_TEAM_USER', '普通客服', 3, '普通客服', '2019-05-22 10:56:19', '2019-05-22 10:56:19');
INSERT INTO `tb_role` VALUES (4, 'ROLE_VISITOR', '访客', 4, '访问网页自动创建的角色', '2019-06-07 14:56:47', '2019-06-07 14:56:47');
COMMIT;

-- ----------------------------
-- Table structure for tb_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_permission`;
CREATE TABLE `tb_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色与资源关系编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `permission_id` int(11) NOT NULL COMMENT '权限编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COMMENT='角色与资源关联表';

-- ----------------------------
-- Records of tb_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `tb_role_permission` VALUES (1, 1, 1, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (2, 1, 2, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (3, 1, 3, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (4, 1, 4, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (5, 1, 5, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (6, 1, 6, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (7, 1, 7, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (8, 1, 8, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (9, 1, 9, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (10, 1, 10, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (11, 1, 11, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (35, 1, 21, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (36, 1, 22, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (37, 1, 23, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (38, 1, 24, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (39, 1, 25, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (40, 1, 26, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (41, 1, 27, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (42, 1, 28, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (43, 1, 29, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (44, 1, 30, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (45, 1, 31, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (46, 1, 32, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (47, 1, 33, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (48, 1, 34, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (49, 1, 35, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (50, 1, 36, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (51, 1, 37, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (52, 1, 38, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (53, 2, 12, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (54, 2, 13, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (55, 2, 14, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (56, 2, 15, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (57, 2, 16, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (58, 2, 17, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (59, 2, 18, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (60, 2, 19, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (61, 2, 20, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (62, 3, 12, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (63, 3, 13, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (64, 3, 14, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (65, 3, 15, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (66, 3, 16, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (67, 3, 17, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (68, 3, 18, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (69, 3, 19, NULL, NULL);
INSERT INTO `tb_role_permission` VALUES (70, 3, 20, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for tb_team
-- ----------------------------
DROP TABLE IF EXISTS `tb_team`;
CREATE TABLE `tb_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '团队编号',
  `name` varchar(255) NOT NULL COMMENT '团队/公司名称',
  `industry` varchar(255) DEFAULT NULL COMMENT '所在行业',
  `province` varchar(255) DEFAULT NULL COMMENT '所在省',
  `city` varchar(255) DEFAULT NULL COMMENT '所在市',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='团队表';

-- ----------------------------
-- Records of tb_team
-- ----------------------------
BEGIN;
INSERT INTO `tb_team` VALUES (1, '广州无卡数据', '', '内蒙古自治区', '包头市', NULL, '2019-06-03 09:00:40');
INSERT INTO `tb_team` VALUES (3, '测试团队2', 'O2O平台', '内蒙古自治区', '赤峰市', NULL, '2019-06-03 08:30:08');
INSERT INTO `tb_team` VALUES (5, '测试团队4', '', '山西省', '朔州市', NULL, '2019-06-03 08:57:28');
INSERT INTO `tb_team` VALUES (6, '测试团队5', '', '山西省', '大同市', NULL, '2019-06-03 08:17:05');
INSERT INTO `tb_team` VALUES (7, '测试团队6', '', '山西省', '大同市', NULL, '2019-06-03 08:55:08');
INSERT INTO `tb_team` VALUES (8, '广州无卡数据', '', '内蒙古自治区', '赤峰市', NULL, '2019-06-03 09:00:45');
INSERT INTO `tb_team` VALUES (9, '测试团队7', '', '山西省', '大同市', NULL, '2019-06-03 08:49:20');
INSERT INTO `tb_team` VALUES (10, '测试团队9', '互联网/软件', '山西省', '大同市', NULL, '2019-06-03 08:08:08');
INSERT INTO `tb_team` VALUES (11, '测试团队10', '电子商务', '内蒙古自治区', '包头市', NULL, '2019-06-03 08:05:26');
INSERT INTO `tb_team` VALUES (12, '测试团队11', '电子商务', '重庆市', '重庆市', '2019-06-03 09:10:36', '2019-06-03 09:10:36');
INSERT INTO `tb_team` VALUES (13, '测试团队', '游戏', '山西省', '大同市', '2019-06-03 09:14:31', '2019-06-03 09:14:31');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `gender` int(4) NOT NULL DEFAULT '2' COMMENT '性别 [0.女 1.男 2.未知]',
  `status` int(4) NOT NULL DEFAULT '1' COMMENT '状态 [0.禁用 1.正常 2.删除]',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `open_id` varchar(255) DEFAULT NULL COMMENT '微信open id',
  `team_id` int(11) DEFAULT NULL COMMENT '团队编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`) USING BTREE COMMENT '用户名唯一',
  UNIQUE KEY `email` (`email`) USING BTREE COMMENT '邮箱唯一'
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (1, '周剑峰', 'images/avatar1.png', 'admin@6ag.cn', NULL, 2, 1, 'admin', '$2a$10$ei/NpOr5Q80vDXQ.TMPHduYkwdtG9vXd00EKN0lVX8pxxWmHhZrHu', '', 1, '2019-05-21 04:27:51', '2019-06-04 04:20:49');
INSERT INTO `tb_user` VALUES (2, '周晓华', '', '44334512@6ag.cn', NULL, 2, 1, 'a44334512', '$2a$10$O7mMdCAcQNA7n9hwmTIWjOaS7GWm/qv5pB8oQFxwWw7d72wz.yW5C', '', NULL, NULL, '2019-06-04 04:03:51');
INSERT INTO `tb_user` VALUES (3, '周小红', '', '44334513@6ag.cn', NULL, 1, 1, 'b44334512', '$2a$10$V0bt7m8L.fL2t1NROV9vKO5jYJwabEpx.qyd2bvLSkHbo3zEtxwx6', '', NULL, NULL, '2019-05-29 05:59:56');
INSERT INTO `tb_user` VALUES (4, '测试1', '', '18727365789@163.com', NULL, 2, 1, 'a324234234', '123456', '', NULL, '2019-05-29 03:17:37', '2019-05-29 06:12:34');
INSERT INTO `tb_user` VALUES (6, NULL, NULL, NULL, NULL, 2, 1, '2618-1559913185472-85143', NULL, NULL, 1, '2019-06-08 14:55:40', '2019-06-08 14:55:40');
COMMIT;

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户与角色关系编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='用户与角色关联表';

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_user_role` VALUES (10, 3, 3, '2019-05-29 02:59:37', '2019-05-29 02:59:37');
INSERT INTO `tb_user_role` VALUES (12, 4, 3, '2019-05-29 05:39:20', '2019-05-29 05:39:20');
INSERT INTO `tb_user_role` VALUES (13, 2, 2, '2019-06-04 04:03:51', '2019-06-04 04:03:51');
INSERT INTO `tb_user_role` VALUES (14, 2, 3, '2019-06-04 04:03:51', '2019-06-04 04:03:51');
INSERT INTO `tb_user_role` VALUES (15, 1, 1, '2019-06-04 04:20:49', '2019-06-04 04:20:49');
INSERT INTO `tb_user_role` VALUES (16, 1, 2, '2019-06-04 04:20:49', '2019-06-04 04:20:49');
INSERT INTO `tb_user_role` VALUES (17, 1, 3, '2019-06-04 04:20:49', '2019-06-04 04:20:49');
INSERT INTO `tb_user_role` VALUES (18, 5, 4, '2019-06-07 15:59:23', '2019-06-07 15:59:23');
INSERT INTO `tb_user_role` VALUES (19, 6, 4, '2019-06-08 14:55:39', '2019-06-08 14:55:39');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
