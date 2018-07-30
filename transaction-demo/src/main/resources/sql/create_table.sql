
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_groot_etcd_modify
-- ----------------------------
DROP TABLE IF EXISTS `t_groot_etcd_modify`;
CREATE TABLE `t_groot_etcd_modify` (
  `etcd_modify_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `etcd_modify_addr` varchar(255) NOT NULL,
  `etcd_modify_key` varchar(1000) NOT NULL,
  `etcd_modify_value` varchar(3000) DEFAULT NULL,
  `modify_userid` bigint(20) NOT NULL,
  `modified_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  `modify_type` tinyint(4) NOT NULL,
  `etcd_modify_ttl` bigint(20) DEFAULT NULL,
  `is_dir` tinyint(4) NOT NULL,
  PRIMARY KEY (`etcd_modify_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_groot_user
-- ----------------------------
DROP TABLE IF EXISTS `t_groot_user`;
CREATE TABLE `t_groot_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(10) DEFAULT NULL COMMENT '用户名',
  `user_phone` varchar(11) NOT NULL COMMENT '用户电话',
  `user_password` varchar(255) NOT NULL COMMENT '密码',
  `user_salt` varchar(255) DEFAULT NULL COMMENT '加密盐',
  `last_login_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `login_times` bigint(20) DEFAULT NULL COMMENT '登录次数',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `is_deleted` tinyint(2) DEFAULT NULL COMMENT '是否删除0：使用中；1：已删除',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `pass_word` varchar(255) NOT NULL,
  `reg_time` varchar(255) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`),
  UNIQUE KEY `UK_lqjrcobrh9jc8wpcar64q1bfh` (`user_name`),
  UNIQUE KEY `UK_d2ia11oqhsynodbsi46m80vfc` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8;
