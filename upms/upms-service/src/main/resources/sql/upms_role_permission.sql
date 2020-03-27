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
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (1, 0, 1);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (2, 0, 2);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (3, 0, 3);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (4, 0, 4);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (5, 0, 5);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (6, 0, 6);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (7, 0, 7);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (8, 0, 8);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (9, 0, 9);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (10, 0, 10);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (11, 0, 11);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (12, 0, 12);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (13, 0, 13);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (14, 0, 14);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (15, 0, 15);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (16, 0, 16);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (17, 0, 17);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (18, 0, 18);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (19, 0, 19);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (20, 0, 20);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (21, 0, 21);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (24, 0, 22);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (25, 0, 23);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (26, 0, 24);
INSERT INTO `upms_role_permission`(`id`, `role_id`, `permission_id`) VALUES (27, 0, 25);
