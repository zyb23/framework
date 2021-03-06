CREATE TABLE `upms_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  `edit_time` datetime NOT NULL,
  `editor_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bqy406dtsr7j7d6fawi1ckyn1` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
INSERT INTO `upms_role`(`id`, `code`, `name`, `description`, `create_time`, `creator_id`, `edit_time`, `editor_id`) VALUES (1, 'administrator', '系统管理员', '', '2019-12-12 14:03:50', -1, '2019-12-12 14:03:56', -1);
