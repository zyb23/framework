CREATE TABLE `upms_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsait308gqqfqha9pd9vfkfdc8` (`role_id`,`permission_id`),
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `upms_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `upms_permission` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (1, 1, 1);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (2, 1, 2);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (3, 1, 3);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (4, 1, 4);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (5, 1, 5);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (6, 1, 6);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (7, 1, 7);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (8, 1, 8);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (9, 1, 9);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (10, 1, 10);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (11, 1, 11);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (12, 1, 12);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (13, 1, 13);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (14, 1, 14);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (15, 1, 15);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (16, 1, 16);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (17, 1, 17);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (18, 1, 18);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (19, 1, 19);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (20, 1, 20);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (21, 1, 21);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (22, 1, 22);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (23, 1, 23);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (24, 1, 24);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (25, 1, 25);
