-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 01, 2001 at 09:04 AM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.5.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `testo`
--

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `role`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `test`
--

CREATE TABLE `test` (
  `id` bigint(20) NOT NULL,
  `answer` varchar(255) DEFAULT NULL,
  `question` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `score` int(11) NOT NULL,
  `status` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `test_words`
--

CREATE TABLE `test_words` (
  `test_id` bigint(20) NOT NULL,
  `words_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `address_line_one` varchar(50) NOT NULL,
  `address_line_two` varchar(50) NOT NULL,
  `city` varchar(50) NOT NULL,
  `contact_number` varchar(12) NOT NULL,
  `create_date` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_login` varchar(255) DEFAULT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `postal_code` varchar(4) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `active`, `address_line_one`, `address_line_two`, `city`, `contact_number`, `create_date`, `email`, `first_name`, `last_login`, `last_name`, `password`, `postal_code`, `profile_picture`, `token`, `username`) VALUES
(1, b'1', 'nnnnnnn', 'nnnnnn', 'nnnnnnn', '1111111111', '2001-01-01 00:46:03', 'sy@gmail.co', 'Simon ', '2001-01-01 09:17:23', 'Jack ', '$2a$10$FAy5GBNb2xH9ySJk3Zwq8ugeSm2vx6ycBXtlbtatxK8w3cLKfqC7W', '9001', 'none', NULL, 'simon');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `word`
--

CREATE TABLE `word` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `word` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `word`
--

INSERT INTO `word` (`id`, `description`, `word`) VALUES
(0, 'the height of actor''s head detemined by the position he/she is in', 'level'),
(1, 'A place where people live', 'House'),
(2, 'Food and drink that you can consume', 'Diet'),
(3, 'People who are specialists in the fields', 'experts'),
(4, 'While being threatened with a knife', 'Knife point'),
(5, 'unreal perception', 'illusion'),
(6, 'the feel or apperance of an object', 'texture'),
(7, 'a repetition of objects or shapes', 'pattern'),
(8, 'an element of art that refers to area around the companents of an artwork', 'space'),
(9, 'the area that the object of the artwork occupies', 'positive-space'),
(10, 'that area surrounding the positive space of the artwork', 'negetive-space'),
(11, 'the higness or lowness of a sound', 'pitch'),
(12, 'indicaties music notes from middle c up', 'treble clef'),
(13, 'in the opposite direction to the hands of a clock', 'anticlockwise'),
(14, 'the same direction as the hands of the clock', 'clockwise'),
(15, 'interlaced stands of ribbon plait', 'braid'),
(16, 'a traditional dance that is typical of a cultural group', 'folk-dance'),
(17, 'an english folk dance performed around a pole where dancers wind ribbons around tree pole to form a braid', 'maypole'),
(18, 'existing in your imagination as a mental image', 'imaginary'),
(19, 'indicates music notes from c down', 'bass-clef'),
(20, 'the natural home of a plant or an animal', 'habitat'),
(21, 'shell or hard covering of an animals', 'exoskeleton'),
(22, 'skeleton found inside an animals body', 'endoskeleton'),
(23, 'an animal with a bony back bone', 'vertebrate'),
(24, 'an animal that does not have a back bone made from bones', 'invertebrate'),
(25, 'something that will not let water pass throug', 'waterproof'),
(26, 'a material that is not easily damaged by fire', 'fire resistant'),
(27, 'something that can last for a very long time', 'durable'),
(28, 'a material that is not easily damaged by heat', 'heat resistant'),
(29, 'a big oven use to fire,bake and dry', 'kiln'),
(30, 'very important papers', 'documents'),
(31, 'furious,very angry', 'enraged'),
(32, 'a brother or sister', 'sibling'),
(33, 'the spirits of those who died', 'gosts'),
(34, 'a group of house holds ', 'homestead'),
(35, 'a place where the children and the wife lived in a house hold', 'house hold'),
(36, 'a place you go pray in', 'church'),
(37, 'a bent in the coastline', 'bay');

-- --------------------------------------------------------

--
-- Table structure for table `word_tests`
--

CREATE TABLE `word_tests` (
  `word_id` bigint(20) NOT NULL,
  `tests_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`),
  ADD UNIQUE KEY `UK_bjxn5ii7v7ygwx39et0wawu0q` (`role`);

--
-- Indexes for table `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKswx8tgvx4biygw60uqgjjq7a7` (`user_id`);

--
-- Indexes for table `test_words`
--
ALTER TABLE `test_words`
  ADD KEY `FKlpxg4wmk6bcowaar410bmy889` (`words_id`),
  ADD KEY `FKk0e973fgbsrm755uyfi2uolif` (`test_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);

--
-- Indexes for table `word`
--
ALTER TABLE `word`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `word_tests`
--
ALTER TABLE `word_tests`
  ADD KEY `FKm2cysif07scykxnysg3pepfdt` (`tests_id`),
  ADD KEY `FKr8tty13w5cl0al875m0s87fih` (`word_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `test`
--
ALTER TABLE `test`
  ADD CONSTRAINT `FKswx8tgvx4biygw60uqgjjq7a7` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Constraints for table `test_words`
--
ALTER TABLE `test_words`
  ADD CONSTRAINT `FKk0e973fgbsrm755uyfi2uolif` FOREIGN KEY (`test_id`) REFERENCES `test` (`id`),
  ADD CONSTRAINT `FKlpxg4wmk6bcowaar410bmy889` FOREIGN KEY (`words_id`) REFERENCES `word` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`);

--
-- Constraints for table `word_tests`
--
ALTER TABLE `word_tests`
  ADD CONSTRAINT `FKm2cysif07scykxnysg3pepfdt` FOREIGN KEY (`tests_id`) REFERENCES `test` (`id`),
  ADD CONSTRAINT `FKr8tty13w5cl0al875m0s87fih` FOREIGN KEY (`word_id`) REFERENCES `word` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
