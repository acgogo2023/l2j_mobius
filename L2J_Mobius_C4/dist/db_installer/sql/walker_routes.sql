-- ---------------------------------
-- Table structure for walker_routes
-- ---------------------------------
DROP TABLE IF EXISTS `walker_routes`;
CREATE TABLE `walker_routes` (
  `route_id` int(11) NOT NULL default '0',
  `npc_id` int(11) NOT NULL default '0',
  `move_point` int(9) NOT NULL,
  `chatText` varchar(255) default NULL,
  `move_x` int(9) NOT NULL default '0',
  `move_y` int(9) NOT NULL default '0',
  `move_z` int(9) NOT NULL default '0',
  `delay`  int(9) NOT NULL default '0',
  `running`  tinyint(1) NOT NULL default '0',
   PRIMARY KEY (`route_id`,`npc_id`,`move_point`)
);

--
-- Dumping data for table `walker_routes`
--

INSERT INTO `walker_routes` VALUES 
(1, 8361, 1, NULL, 22418, 10249, -3648, 61, 1),
(1, 8361, 2, NULL, 23423, 11165, -3720, 0, 1),
(1, 8361, 3, NULL, 20182, 11101, -3720, 0, 1),
(1, 8361, 4, NULL, 17327, 13603, -3728, 1, 1),
(1, 8361, 5, 'The mass of darkness will start in a couple of days. Pay more attention to the guard!', 17410, 13038, -3736, 10, 1),
(1, 8361, 6, NULL, 20176, 12902, -3712, 0, 1),
(1, 8361, 7, NULL, 21669, 13378, -3616, 0, 1),
(1, 8361, 8, NULL, 20675, 10401, -3712, 0, 1),
(2, 8360, 1, NULL, 10820, 14770, -4240, 62, 0),
(2, 8360, 2, NULL, 10947, 14642, -4240, 0, 0),
(2, 8360, 3, NULL, 11301, 15844, -4584, 0, 0),
(2, 8360, 4, NULL, 12107, 16431, -4584, 0, 0),
(2, 8360, 5, 'You''re a hard worker, Rayla!', 15097, 15662, -4376, 6, 0),
(2, 8360, 6, NULL, 15269, 16281, -4376, 7, 0),
(2, 8360, 7, NULL, 12336, 16921, -4584, 0, 0),
(2, 8360, 8, NULL, 11786, 17663, -4564, 0, 0),
(2, 8360, 9, NULL, 11246, 17650, -4568, 0, 0),
(2, 8360, 10, NULL, 10649, 17284, -4584, 0, 0),
(2, 8360, 11,'You''re a hard worker!',7692,18041,-4376,6,0),
(2, 8360, 12, NULL, 10549, 16780, -4584, 0, 0),
(2, 8360, 13, NULL, 10722, 16538, -4584, 0, 0),
(2, 8360, 14, NULL, 11008, 15927, -4584, 0, 0),
(3, 8362, 1, 'Mr. Lid, Murdoc, and Airy! How are you doing?', 114847, -180066, -877, 30, 0),
(3, 8362, 2, NULL, 114834, -179685, -877, 2, 0),
(3, 8362, 3, NULL, 116122, -179457, -1068, 1, 0),
(3, 8362, 4, NULL, 116798, -180391, -1200, 2, 0),
(3, 8362, 5, NULL, 116324, -181564, -1384, 2, 0),
(3, 8362, 6, NULL, 115797, -181563, -1336, 0, 0),
(3, 8362, 7, 'Care to go a round?', 116054, -181575, -1352, 0, 0),
(3, 8362, 8, NULL, 116506, -181478, -1384, 2, 0),
(3, 8362, 9, NULL, 116634, -180029, -1160, 1, 0),
(3, 8362, 10, NULL, 115347, -178623, -928, 1, 0),
(3, 8362, 11, NULL, 115763, -177591, -888, 2, 0),
(3, 8362, 12, 'Have a nice day, Mr. Garita and Mion!', 115801, -177342, -880, 1, 0),
(3, 8362, 13, NULL, 115869, -177340, -880, 15, 0),
(3, 8362, 14, NULL, 115788, -177482, -880, 3, 0),
(3, 8362, 15, NULL, 115124, -179821, -885, 1, 0),
(3, 8362, 16, NULL, 115103, -180065, -877, 1, 0),
(4, 8363, 1, 'Where is that fool hiding?', 116731, -182477, -1512, 10, 1),
(4, 8363, 2, NULL, 115870, -183280, -1472, 0, 1),
(4, 8363, 3, NULL, 115746, -183428, -1472, 1, 1),
(4, 8363, 4, NULL, 115870, -183280, -1472, 1, 1),
(4, 8363, 5, NULL, 115999, -183246, -1480, 0, 1),
(4, 8363, 6, NULL, 116094, -183113, -1480, 1, 1),
(4, 8363, 7, 'Have you seen Torocco today?', 116584, -184294, -1568, 11, 1),
(4, 8363, 8, NULL, 116392, -184100, -1560, 1, 1),
(4, 8363, 9, NULL, 117093, -182524, -1528, 1, 1),
(4, 8363, 10, 'Have you seen Torocco?', 117789, -182540, -1528, 11, 1),
(5, 8359, 1, NULL, 45744, 50561, -3065, 61, 0),
(5, 8359, 2, NULL, 46444, 49742, -3065, 2, 0),
(5, 8359, 3, 'How can we save the Mother Tree?', 46103, 48798, -3065, 5, 0),
(5, 8359, 4, 'The Mother Tree is slowly dying', 45403, 48436, -3065, 5, 0),
(5, 8359, 5, NULL, 44444, 49078, -3065, 0, 0),
(5, 8359, 6, NULL, 44414, 50025, -3065, 0, 0),
(5, 8359, 7, NULL, 44957, 50568, -3065, 0, 0),
(5, 8359, 8, NULL, 44414, 50025, -3065, 0, 0),
(5, 8359, 9, NULL, 44444, 49078, -3065, 0, 0),
(5, 8359, 10, 'The Mother Tree is slowly dying', 45403, 48436, -3065, 5, 0),
(5, 8359, 11, 'How can we save the Mother Tree?', 46103, 48798, -3065, 5, 0),
(5, 8359, 12, NULL, 46444, 49742, -3065, 2, 0),
(6, 8358, 1, 'Lady Mirabel, may the peace of the lake be with you!', 47015, 51278, -2992, 65, 0),
(6, 8358, 2, NULL, 47437, 50441, -2992, 0, 0),
(6, 8358, 3, NULL, 47509, 49038, -2992, 0, 0),
(6, 8358, 4, NULL, 46725, 47755, -2992, 0, 0),
(6, 8358, 5, 'The Mother Tree is always so gorgeous!', 45319, 47339, -2992, 5, 0),
(6, 8358, 6, NULL, 43998, 47672, -2992, 0, 0),
(6, 8358, 7, NULL, 43037, 49310, -2992, 0, 0),
(6, 8358, 8, NULL, 43310, 50382, -2992, 0, 0),
(6, 8358, 9, NULL, 43896, 51060, -2992, 0, 0),
(6, 8358, 10, NULL, 43312, 50362, -2992, 0, 0),
(6, 8358, 11, NULL, 43040, 49311, -2992, 0, 0),
(6, 8358, 12, NULL, 44018, 47645, -2992, 0, 0),
(6, 8358, 13, 'The Mother Tree is always so gorgeous!', 45301, 47340, -2992, 5, 0),
(6, 8358, 14, NULL, 46693, 47752, -2992, 0, 0),
(6, 8358, 15, NULL, 47489, 48976, -2992, 0, 0),
(6, 8358, 16, NULL, 47441, 50455, -2992, 0, 0),
(7, 8357, 1, 'Where did he go?', -86328, 241120, -3734, 60, 0),
(7, 8357, 2, NULL, -86505, 240727, -3704, 0, 0),
(7, 8357, 3, NULL, -86081, 240402, -3712, 0, 0),
(7, 8357, 4, 'Have you seen Windawood?', -86078, 240853, -3720, 15, 0),
(7, 8357, 5, NULL, -85957, 241389, -3728, 0, 0),
(7, 8357, 6, 'Where has he gone?', -83993, 242766, -3728, 10, 0),
(7, 8357, 7, NULL, -82952, 244461, -3728, 0, 0),
(7, 8357, 8, NULL, -82370, 244919, -3720, 0, 0),
(7, 8357, 9, NULL, -82129, 245020, -3720, 0, 0),
(7, 8357, 10, NULL, -82198, 245350, -3712, 0, 0),
(7, 8357, 11, NULL, -82554, 245137, -3716, 1, 0),
(7, 8357, 12, NULL, -82198, 245350, -3712, 0, 0),
(7, 8357, 13, NULL, -82129, 245020, -3720, 0, 0),
(7, 8357, 14, NULL, -82370, 244919, -3720, 0, 0),
(7, 8357, 15, NULL, -82952, 244461, -3728, 0, 0),
(7, 8357, 16, 'Where has he gone?', -83993, 242766, -3728, 10, 0),
(7, 8357, 17, NULL, -85957, 241389, -3728, 0, 0),
(7, 8357, 18, 'Have you seen Windawood?', -86078, 240853, -3720, 15, 0),
(7, 8357, 19, NULL, -86081, 240402, -3712, 0, 0),
(7, 8357, 20, NULL, -86505, 240727, -3704, 0, 0),
(8, 8356, 1, 'A delivery for Mr. Lector? Very good!', -81681, 243384, -3712, 61, 1),
(8, 8356, 2, NULL, -81915, 243870, -3712, 1, 1),
(8, 8356, 3, NULL, -82084, 243659, -3712, 0, 1),
(8, 8356, 4, NULL, -83148, 243731, -3728, 0, 1),
(8, 8356, 5, 'I need a break!', -84497, 243241, -3728, 6, 1),
(8, 8356, 6, NULL, -85212, 243184, -3728, 0, 1),
(8, 8356, 7, NULL, -86161, 242898, -3728, 0, 1),
(8, 8356, 8, NULL, -86281, 242963, -3720, 0, 1),
(8, 8356, 9, 'Hello, Mr. Lector! Long time no see, Mr. Jackson!', -86356, 243201, -3720, 7, 1),
(8, 8356, 10, NULL, -86491, 242781, -3720, 0, 1),
(8, 8356, 11, NULL, -86497, 242585, -3728, 0, 1),
(8, 8356, 12, NULL, -86114, 241587, -3728, 0, 1),
(8, 8356, 13, NULL, -85184, 240679, -3728, 0, 1),
(8, 8356, 14, 'Lulu!', -84075, 241282, -3728, 3, 1),
(8, 8356, 15, 'Lulu!', -84075, 241282, -3728, 3, 1),
(8, 8356, 16, 'Lulu!', -83709, 241238, -3728, 0, 1),
(8, 8356, 17, NULL, -83182, 241141, -3728, 1, 1),
(8, 8356, 18, NULL, -82383, 242926, -3720, 1, 1),
(9, 8364, 1, NULL, -46506, -109402, -238, 10, 0),
(9, 8364, 2, NULL, -45752, -111652, -240, 0, 0),
(9, 8364, 3, NULL, -44028, -112688, -240, 0, 0),
(9, 8364, 4, NULL, -44049, -114536, -240, 0, 0),
(9, 8364, 5, NULL, -45540, -115415, -240, 0, 0),
(9, 8364, 6, NULL, -46526, -117731, -240, 0, 0),
(9, 8364, 7, NULL, -45540, -115415, -240, 0, 0),
(9, 8364, 8, NULL, -44049, -114536, -240, 0, 0),
(9, 8364, 9, NULL, -44028, -112688, -240, 0, 0),
(9, 8364, 10, NULL, -45752, -111652, -240, 0, 0),
(10, 8365, 1, NULL, -48807, -113489, -241, 61, 0),
(10, 8365, 2, NULL, -48821, -113746, -232, 1, 0),
(10, 8365, 3, NULL, -48823, -113505, -232, 61, 0),
(10, 8365, 4, NULL, -47339, -113581, -232, 1, 0),
(10, 8365, 5, NULL, -45740, -113562, -240, 0, 0),
(10, 8365, 6, NULL, -44851, -112965, -240, 0, 0),
(10, 8365, 7, NULL, -44308, -113207, -240, 0, 0),
(10, 8365, 8, NULL, -44204, -113743, -240, 0, 0),
(10, 8365, 9, NULL, -44659, -114171, -240, 0, 0),
(10, 8365, 10, NULL, -45167, -114034, -224, 0, 0),
(10, 8365, 11, NULL, -45658, -113633, -240, 0, 0),
(10, 8365, 12, NULL, -47363, -113621, -224, 0, 0),
(10, 8365, 13, NULL, -48829, -113746, -232, 0, 0),
(10, 8365, 14, NULL, -47363, -113621, -224, 0, 0),
(10, 8365, 15, NULL, -45658, -113633, -240, 0, 0),
(10, 8365, 16, NULL, -45167, -114034, -224, 0, 0),
(10, 8365, 17, NULL, -44659, -114171, -240, 0, 0),
(10, 8365, 18, NULL, -44204, -113743, -240, 0, 0),
(10, 8365, 19, NULL, -44308, -113207, -240, 0, 0),
(10, 8365, 20, NULL, -44851, -112965, -240, 0, 0),
(10, 8365, 21, NULL, -45740, -113562, -240, 1, 0);