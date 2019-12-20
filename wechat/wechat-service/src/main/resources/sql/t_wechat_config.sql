CREATE TABLE `t_wechat_config` (
  `id` bigint(20) NOT NULL,
  `app_key` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `app_secret` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `access_token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `token` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `encoding_aes_key` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `encrypt_mode` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `is_enable` bit(1) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `edit_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;