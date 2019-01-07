-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Янв 04 2019 г., 21:22
-- Версия сервера: 5.5.25
-- Версия PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `restaurant`
--

-- --------------------------------------------------------

--
-- Структура таблицы `items`
--

CREATE TABLE IF NOT EXISTS `items` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) NOT NULL,
  `cost` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `items`
--

INSERT INTO `items` (`id`, `name`, `description`, `cost`) VALUES
(1, 'sushi', 'hearty and big', 6),
(2, 'soup', 'This is borscht', 5),
(3, 'nuggets', 'juicy and tasty', 7),
(4, 'burger', 'big and tasty', 6),
(5, 'cola', 'cold and refreshing', 3);

-- --------------------------------------------------------

--
-- Структура таблицы `items_orders`
--

CREATE TABLE IF NOT EXISTS `items_orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `orders_id` int(10) NOT NULL,
  `items_dictionary_id` int(10) NOT NULL,
  `quantity` int(2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Дамп данных таблицы `items_orders`
--

INSERT INTO `items_orders` (`id`, `orders_id`, `items_dictionary_id`, `quantity`) VALUES
(1, 1, 3, 2),
(2, 1, 4, 2),
(3, 1, 2, 3),
(4, 2, 5, 2),
(5, 2, 3, 2),
(6, 3, 2, 2),
(7, 4, 5, 3),
(8, 4, 3, 2),
(9, 4, 1, 3),
(10, 5, 1, 2);

-- --------------------------------------------------------

--
-- Структура таблицы `officiants`
--

CREATE TABLE IF NOT EXISTS `officiants` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) NOT NULL,
  `second_name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `officiants`
--

INSERT INTO `officiants` (`id`, `first_name`, `second_name`) VALUES
(1, 'Jonathan', 'Adam'),
(2, 'Jayden', 'Thomas'),
(3, 'Seth', 'Alejandro'),
(4, 'James', 'Alexander'),
(5, 'Cameron', 'Tyler');

-- --------------------------------------------------------

--
-- Структура таблицы `orders`
--

CREATE TABLE IF NOT EXISTS `orders` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `officiant_id` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Дамп данных таблицы `orders`
--

INSERT INTO `orders` (`id`, `date`, `officiant_id`) VALUES
(1, '2018-10-05', 1),
(2, '2018-10-05', 3),
(3, '2018-10-04', 2),
(4, '2018-10-04', 5),
(5, '2018-10-03', 3);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
