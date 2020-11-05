CREATE TABLE `upms_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `login_password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `is_enable` bit(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `edit_time` datetime NOT NULL,
  `editor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_blyyljcvmmqokx6t10jvmcj98` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
INSERT INTO `upms_user`(`id`, `login_name`, `login_password`, `salt`, `username`, `icon`, `mobile`, `email`, `is_enable`, `create_time`, `creator_id`, `edit_time`, `editor_id`) VALUES (1, 'admin', '75ea15c2ea9ff3440779e3451c389e39', 'gyy5hQDn', '超级管理员', NULL, NULL, NULL, b'1', '2019-12-12 13:50:57', -1, '2019-12-12 13:51:07', -1);
