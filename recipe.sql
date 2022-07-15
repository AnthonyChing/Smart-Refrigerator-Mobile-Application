-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2022-07-15 10:48:08
-- 伺服器版本： 10.4.21-MariaDB
-- PHP 版本： 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫: `fridge`
--

-- --------------------------------------------------------

--
-- 資料表結構 `recipe`
--

CREATE TABLE `recipe` (
  `Recipe_id` int(2) DEFAULT NULL,
  `Dish_name` varchar(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 傾印資料表的資料 `recipe`
--

INSERT INTO `recipe` (`Recipe_id`, `Dish_name`) VALUES
(1, '豆干炒肉絲'),
(2, '奶油義大利麵'),
(3, '糖醋排骨'),
(4, '涼拌海蜇頭'),
(5, '塔香蛤蜊'),
(6, '薑絲小卷'),
(7, '客家小炒'),
(8, '生炒花枝'),
(9, '豆酥蚵'),
(10, '炒蟹腳'),
(11, '芙蓉蟹'),
(12, '蒜苗魚片'),
(13, '大蒜蝦'),
(14, '胡椒蝦'),
(15, '醉雞'),
(16, '三杯中卷'),
(17, '西芹鮮魷'),
(18, '蔥爆豬肉'),
(19, '打拋豬肉'),
(20, '洋蔥豬肉'),
(21, '啤酒燉豬肉'),
(22, '葱爆鹹猪肉'),
(23, '沙茶豬肉波菜'),
(24, '紅燒肉');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
