-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2022-07-15 10:47:54
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
-- 資料表結構 `fridge_supply`
--

CREATE TABLE `fridge_supply` (
  `Ingredient_name` varchar(100) NOT NULL,
  `Ingredient_id` int(11) NOT NULL,
  `Quantity` float NOT NULL,
  `Measurement` varchar(100) NOT NULL,
  `Expiration_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- 傾印資料表的資料 `fridge_supply`
--

INSERT INTO `fridge_supply` (`Ingredient_name`, `Ingredient_id`, `Quantity`, `Measurement`, `Expiration_date`) VALUES
('豬肉', 1, 250, '克', '2022-05-18'),
('豬絞肉', 58, 200, '克', '2022-05-17'),
('蛤蜊', 29, 0.2, '斤', '2022-05-17'),
('海蟹腳', 43, 0.4, '斤', '2022-05-17'),
('洋蔥', 5, 1, '顆', '2022-05-17'),
('大蒜', 8, 4, '顆', '2022-05-18'),
('雞腿', 16, 5, '支', '2022-05-19'),
('豬小排骨', 9, 500, '克', '2022-06-01');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
