CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL,
  `creator_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsait308gqqfqha9pd9vfkfdc8` (`role_id`,`permission_id`),
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (1, 0, 1, '2019-12-12 14:05:31', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (2, 0, 2, '2019-12-12 14:06:19', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (3, 0, 3, '2019-12-12 14:06:57', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (4, 0, 4, '2019-12-12 14:07:05', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (5, 0, 5, '2019-12-12 14:07:12', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (6, 0, 6, '2019-12-12 14:07:20', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (7, 0, 7, '2019-12-12 14:07:26', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (8, 0, 8, '2019-12-12 14:07:33', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (9, 0, 9, '2019-12-12 14:07:39', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (10, 0, 10, '2019-12-12 14:07:46', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (11, 0, 11, '2019-12-12 14:07:51', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (12, 0, 12, '2019-12-12 14:07:56', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (13, 0, 13, '2019-12-12 14:08:01', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (14, 0, 14, '2019-12-12 14:08:12', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (15, 0, 15, '2019-12-12 14:08:19', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (16, 0, 16, '2019-12-12 14:08:25', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (17, 0, 17, '2019-12-12 14:08:31', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (18, 0, 18, '2019-12-12 14:08:37', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (19, 0, 19, '2019-12-12 14:08:46', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (20, 0, 20, '2019-12-12 14:08:54', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (21, 0, 21, '2019-12-12 14:09:00', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (24, 0, 22, '2019-12-12 14:09:06', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (25, 0, 23, '2019-12-12 14:09:29', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (26, 0, 24, '2019-12-12 14:09:34', -1);
INSERT INTO `sys_role_permission`(`id`, `role_id`, `permission_id`, `create_time`, `creator_id`) VALUES (27, 0, 25, '2019-12-12 14:09:41', -1);
