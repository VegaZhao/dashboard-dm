-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        10.3.7-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 dashboard 的数据库结构
CREATE DATABASE IF NOT EXISTS `dashboard` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `dashboard`;

-- 导出  表 dashboard.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_code` varchar(255) DEFAULT NULL COMMENT '归属菜单,前端判断并展示菜单使用,',
  `permission_name` varchar(255) DEFAULT NULL COMMENT '菜单中文释义',
  `action_code` varchar(150) NOT NULL COMMENT '操作权限（可使用通配符等）',
  `action_name` varchar(255) DEFAULT NULL COMMENT '操作释义',
  `required_permission` tinyint(4) DEFAULT 2 COMMENT '是否为必须权限，1为是，2为否',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  dashboard.permission 的数据：~18 rows (大约)
DELETE FROM `permission`;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` (`permission_id`, `permission_code`, `permission_name`, `action_code`, `action_name`, `required_permission`) VALUES
	(1, 'system:role_manage', '角色管理', 'system:role_manage:add', '新增角色', 2),
	(2, 'system:role_manage', '角色管理', 'system:role_manage:update', '更新角色', 2),
	(3, 'system:role_manage', '角色管理', 'system:role_manage:delete', '删除角色', 2),
	(4, 'system:role_manage', '角色管理', 'system:role_manage:list', '查询角色列表', 2),
	(5, 'system:user_manage', '用户管理', 'system:user_manage:update', '更新用户', 2),
	(6, 'system:user_manage', '用户管理', 'system:user_manage:delete', '删除用户', 2),
	(7, 'system:user_manage', '用户管理', 'system:user_manage:list', '查询用户列表', 2),
	(8, 'system:user_manage', '用户管理', 'system:user_manage:add', '新增用户', 2),
	(9, 'system:permission_manage', '权限管理', 'system:permission_manage:list', '查询权限列表', 2),
	(10, 'system:permission_manage', '权限管理', 'system:permission_manage:add', '新增权限', 2),
	(11, 'system:permission_manage', '权限管理', 'system:permission_manage:delete', '删除权限', 2),
	(12, 'system:permission_manage', '权限管理', 'system:permission_manage:update', '更新权限', 2),
	(46, 'stat:bank_stat', '银行维度统计', 'stat:bank_stat:*', '银行维度统计所有权限', 2),
	(47, 'stat:customer_stat', '贷款客户维度统计', 'stat:customer_stat:*', '贷款客户维度统计所有权限', 2),
	(48, 'stat:industry_stat', '行业维度统计', 'stat:industry_stat:*', '行业维度统计所有权限', 2),
	(49, 'stat:loan_type_stat', '贷款发放方式维度统计', 'stat:loan_type_stat:*', '贷款发放方式维度统计所有权限', 2),
	(50, 'stat:business_stat', '业务品种维度统计', 'stat:business_stat:*', '业务品种维度统计所有权限', 2),
	(51, 'stat:assure_stat', '担保方式维度统计', 'stat:assure_stat:*', '担保方式维度统计所有权限', 2);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;

-- 导出  表 dashboard.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID ',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `status` tinyint(4) DEFAULT 1 COMMENT '1 有效，2 无效',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  dashboard.role 的数据：~2 rows (大约)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`role_id`, `role_name`, `role_desc`, `status`) VALUES
	(1, 'super_admin', '超级管理员', 1),
	(4, 'Guest', '来宾用户', 1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- 导出  表 dashboard.role_permission_rel 结构
CREATE TABLE IF NOT EXISTS `role_permission_rel` (
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `delete_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否有效，1 有效，2 无效',
  PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  dashboard.role_permission_rel 的数据：~18 rows (大约)
DELETE FROM `role_permission_rel`;
/*!40000 ALTER TABLE `role_permission_rel` DISABLE KEYS */;
INSERT INTO `role_permission_rel` (`role_id`, `permission_id`, `create_time`, `update_time`, `delete_status`) VALUES
	(1, 1, NULL, '2020-08-24 11:05:39', 1),
	(1, 2, NULL, '2020-08-24 11:05:39', 1),
	(1, 3, NULL, '2020-08-24 11:05:39', 1),
	(1, 4, NULL, '2020-08-24 11:05:39', 1),
	(1, 5, NULL, '2020-08-24 11:05:39', 1),
	(1, 6, NULL, '2020-08-24 11:05:39', 1),
	(1, 7, NULL, '2020-08-24 11:05:39', 1),
	(1, 8, NULL, '2020-08-24 11:05:39', 1),
	(1, 9, NULL, '2020-08-24 11:05:39', 1),
	(1, 10, NULL, '2020-08-24 11:05:39', 1),
	(1, 11, NULL, '2020-08-24 11:05:39', 1),
	(1, 12, NULL, '2020-08-24 11:05:39', 1),
	(1, 46, NULL, '2020-08-24 11:05:39', 1),
	(1, 47, NULL, '2020-08-24 11:05:39', 1),
	(1, 48, NULL, '2020-08-24 11:05:39', 1),
	(1, 49, NULL, '2020-08-24 11:05:39', 1),
	(1, 50, NULL, '2020-08-24 11:05:39', 1),
	(1, 51, NULL, '2020-08-24 11:05:39', 1);
/*!40000 ALTER TABLE `role_permission_rel` ENABLE KEYS */;

-- 导出  表 dashboard.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `reg_ip` varchar(255) DEFAULT NULL,
  `reg_time` timestamp NULL DEFAULT NULL,
  `wx` varchar(100) DEFAULT NULL,
  `salt` varchar(45) DEFAULT NULL,
  `birthday` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` tinyint(4) DEFAULT 1 COMMENT '1有效，2无效',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- 正在导出表  dashboard.user 的数据：~2 rows (大约)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `age`, `password`, `user_name`, `address`, `area`, `avatar`, `email`, `mobile`, `tel`, `nick_name`, `qq`, `sex`, `reg_ip`, `reg_time`, `wx`, `salt`, `birthday`, `update_time`, `status`) VALUES
	(4, NULL, 'b8aad1955a71812d34fe2f63555e948b', 'admin', NULL, NULL, NULL, NULL, NULL, NULL, '管理员', NULL, NULL, NULL, '2018-08-11 22:15:24', NULL, '9508055f4ee5486ea3140b802e38e0d9', NULL, '2020-08-25 09:06:27', 1),
	(43, NULL, '28ee534a22c9fa64cee7af6d1e93c8c8', 'Guest', NULL, NULL, NULL, NULL, NULL, NULL, '李', NULL, NULL, NULL, '2018-09-28 20:53:12', NULL, '3889dc2161ce46c4a82d1a217bd562f0', NULL, '2019-05-16 14:08:20', 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- 导出  表 dashboard.user_role_rel 结构
CREATE TABLE IF NOT EXISTS `user_role_rel` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  dashboard.user_role_rel 的数据：~2 rows (大约)
DELETE FROM `user_role_rel`;
/*!40000 ALTER TABLE `user_role_rel` DISABLE KEYS */;
INSERT INTO `user_role_rel` (`user_id`, `role_id`) VALUES
	(4, 1),
	(43, 4);
/*!40000 ALTER TABLE `user_role_rel` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
