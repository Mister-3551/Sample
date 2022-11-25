
CREATE TABLE `levels` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `map` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `levels` (`id`, `name`, `map`) VALUES
(1, 'Training 1', 'Map1'),
(2, 'Training 2', 'Map2'),
(3, 'Training 3', 'Map3'),
(4, 'V Is For Village', 'Map4'),
(5, 'Baddie Bunker 1', 'map5'),
(6, 'Nature Calls', 'map6'),
(7, 'Hostage Hotel', 'map7'),
(8, 'Monkey Abaut', 'map8'),
(9, 'Bad Medicine', 'map9'),
(10, 'Baddie Bunker 2', 'map10');

CREATE TABLE `levels_users` (
  `id` int(11) NOT NULL,
  `id_levels` int(11) NOT NULL,
  `id_users` int(11) NOT NULL,
  `completed` enum('0','1','2') COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `levels_users` (`id`, `id_levels`, `id_users`, `completed`) VALUES
(1, 1, 3, '1'),
(2, 2, 3, '1'),
(3, 1, 1, '0'),
(4, 1, 2, '0'),
(5, 1, 4, '0'),
(6, 1, 5, '0'),
(7, 3, 3, '2');

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL,
  `kills` int(11) NOT NULL,
  `deaths` int(11) NOT NULL,
  `wins` int(11) NOT NULL,
  `losses` int(11) NOT NULL,
  `highest_streak` int(11) NOT NULL,
  `most_kills` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `statistics` (`id`, `kills`, `deaths`, `wins`, `losses`, `highest_streak`, `most_kills`, `user_id`) VALUES
(1, 107, 25, 20, 2, 11, 12, 1),
(2, 55, 33, 10, 5, 7, 7, 2),
(3, 31, 10, 4, 1, 10, 11, 3),
(4, 0, 0, 0, 0, 0, 0, 4),
(5, 0, 0, 0, 0, 0, 0, 5),
(6, 0, 0, 0, 0, 0, 0, 6),
(7, 0, 0, 0, 0, 0, 0, 7),
(8, 0, 0, 0, 0, 0, 0, 8),
(9, 0, 0, 0, 0, 0, 0, 9);

CREATE TABLE `units` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `health_points` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `units` (`id`, `name`, `health_points`) VALUES
(1, 'Gun Master', 100),
(2, 'Rifle Guy', 120),
(3, 'Veteran', 200),
(4, 'Tank Master', 500),
(5, 'Commando', 350),
(6, 'Helli-G', 400),
(7, 'Jeep Master', 250);

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `rank` int(11) NOT NULL,
  `current_xp` int(11) NOT NULL,
  `next_level_xp` int(11) NOT NULL,
  `token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email_confirmed` enum('0','1') COLLATE utf8_unicode_ci NOT NULL,
  `email_token` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `game_token` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `users` (`id`, `name`, `username`, `email`, `password`, `rank`, `current_xp`, `next_level_xp`, `token`, `email_confirmed`, `email_token`, `game_token`) VALUES
(1, 'Janez Novak', 'janeznovak', 'janez@example.com', 'qwqwqwqw', 6, 35, 600, '1664117750574289e426a-8baf-4918-b1ae-c35b83622fb9', '0', '', ''),
(2, 'Mr junk', 'Mr-junk', 'mrjunk@example.com', 'qwqwqwqw', 3, 75, 300, '16641190235013e08f52b-d916-44d6-b57a-a9adf716cf98', '0', '', ''),
(3, 'Jan Novak', 'jannovak4', 'jan@example.com', 'qwqwqwqw', 2, 55, 200, '166413814992602e2dacf-b7dd-49a3-b5b6-a094ce39a19f', '0', '', '1668081456663b5c2098a-ca4d-4832-83b4-0c79f52ee2f4'),
(4, 'Furi Naturi', 'furi-naturi', 'furinaturi@example.com', 'qwqwqwqw', 1, 80, 100, '166413899274794aaafa7-ef54-44e7-b23f-7521dde8adbb', '0', '', ''),
(5, 'Jaki Kakaki', 'Jaki-Kakaki', 'jakikakaki@example.com', 'qwqwqwqw', 1, 0, 100, '1664139254431fb5ef7ad-f8b2-4c08-abac-c98cf852d353', '0', '', ''),
(6, 'Mali Balani', 'malibalani', 'malibalani@example.com', 'qwqwqwqw', 1, 0, 100, '166413945506164228b49-249f-4b70-9207-f41473777615', '0', '', ''),
(7, 'Vampirus Disurius', 'vampirus12', 'vampirus@example.com', 'qwqwqwqw', 1, 0, 100, 'vampi16641400300146d51452e-1f23-40b9-89dc-8679540f8ba0rus12', '0', '', ''),
(8, 'Mr Favoriti', 'favoritimr', 'favoriti@example.com', 'qwqwqwqw', 1, 0, 100, 'favor166414036518438db2b21-087b-4f76-98d6-e58258097dbaitimr', '0', '', ''),
(9, 'Jato Patato', 'jatopatato123', 'jato@example.com', 'qwqwqwqw', 1, 0, 100, 'jatopa1664199100274b8447750-f54c-42a0-ae07-4851135fa8dctato123', '0', '', '');

CREATE TABLE `weapon_statistics` (
  `id` int(11) NOT NULL,
  `katana` int(11) NOT NULL,
  `compact_45` int(11) NOT NULL,
  `knife` int(11) NOT NULL,
  `hands` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `weapon_statistics` (`id`, `katana`, `compact_45`, `knife`, `hands`, `user_id`) VALUES
(1, 30, 14, 33, 30, 1),
(2, 15, 10, 13, 12, 2),
(3, 10, 6, 15, 10, 3),
(4, 0, 0, 0, 0, 4),
(5, 0, 0, 0, 0, 5),
(6, 0, 0, 0, 0, 6),
(7, 0, 0, 0, 0, 7),
(8, 0, 0, 0, 0, 8),
(9, 0, 0, 0, 0, 9);

ALTER TABLE `levels`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `levels_users`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `statistics`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `units`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `weapon_statistics`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `levels`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE `levels_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE `statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

ALTER TABLE `units`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

ALTER TABLE `weapon_statistics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;
