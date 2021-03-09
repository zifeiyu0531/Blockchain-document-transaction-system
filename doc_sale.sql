-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: doc_sale
-- ------------------------------------------------------
-- Server version	8.0.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_title` varchar(100) COLLATE utf8_bin NOT NULL,
  `author_id` int(11) NOT NULL,
  `article_author` varchar(40) COLLATE utf8_bin NOT NULL,
  `submission_date` varchar(40) COLLATE utf8_bin NOT NULL,
  `article_price` double NOT NULL,
  `article_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `article_md5` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `article_abstract` text COLLATE utf8_bin,
  `article_url` text COLLATE utf8_bin NOT NULL,
  `article_download` int(11) NOT NULL DEFAULT '0',
  `article_store` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`article_id`),
  KEY `ARTICLE_AUTHOR_ID_idx` (`author_id`),
  CONSTRAINT `ARTICLE_AUTHOR_ID` FOREIGN KEY (`author_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `bank_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `bank_account` varchar(45) COLLATE utf8_bin NOT NULL,
  `bank_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `phone_number` varchar(45) COLLATE utf8_bin NOT NULL,
  `id_card_number` varchar(45) COLLATE utf8_bin NOT NULL,
  `real_name` varchar(10) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`bank_account_id`),
  KEY `BANK_ACCOUNT_USER_ID_idx` (`user_id`),
  CONSTRAINT `BANK_ACCOUNT_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `CART_ARTICLE_ID_idx` (`article_id`),
  KEY `CART_USER_ID_idx` (`user_id`),
  CONSTRAINT `CART_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
  CONSTRAINT `CART_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_history`
--

DROP TABLE IF EXISTS `point_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `num` double NOT NULL,
  `type` int(11) NOT NULL,
  `date` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `POINT_HISTORY_USER_ID_idx` (`user_id`),
  CONSTRAINT `POINT_HISTORY_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `point_order`
--

DROP TABLE IF EXISTS `point_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `point_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_point` double NOT NULL DEFAULT '0',
  `order_price` double NOT NULL DEFAULT '0',
  `buy_user_id` int(11) NOT NULL,
  `sale_user_id` int(11) NOT NULL,
  `order_date` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`order_id`),
  KEY `BUY_USER_ID_idx` (`buy_user_id`),
  KEY `SALE_USER_ID_idx` (`sale_user_id`),
  CONSTRAINT `BUY_USER_ID` FOREIGN KEY (`buy_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `SALE_USER_ID` FOREIGN KEY (`sale_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `purchased_article`
--

DROP TABLE IF EXISTS `purchased_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchased_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PURCHASED_ARTICLE_ID_idx` (`article_id`),
  KEY `PURCHASED_USER_ID_idx` (`user_id`),
  CONSTRAINT `PURCHASED_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
  CONSTRAINT `PURCHASED_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rmb_history`
--

DROP TABLE IF EXISTS `rmb_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rmb_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `num` double NOT NULL,
  `type` int(11) NOT NULL,
  `date` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `RMB_HISTORY_USER_ID_idx` (`user_id`),
  CONSTRAINT `RMB_HISTORY_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_point`
--

DROP TABLE IF EXISTS `sale_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sale_point` (
  `sale_point_id` int(11) NOT NULL AUTO_INCREMENT,
  `sale_point_num` double NOT NULL DEFAULT '0',
  `sale_point_user` int(11) NOT NULL,
  `sale_point_type` int(11) NOT NULL DEFAULT '0',
  `sale_point_remain` double NOT NULL DEFAULT '0',
  `sale_point_price` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`sale_point_id`),
  KEY `SALE_POINT_USER_ID_idx` (`sale_point_user`),
  CONSTRAINT `SALE_POINT_USER_ID` FOREIGN KEY (`sale_point_user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `stored_article`
--

DROP TABLE IF EXISTS `stored_article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stored_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id_idx` (`article_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `STORED_ARTICLE_ID` FOREIGN KEY (`article_id`) REFERENCES `article` (`article_id`),
  CONSTRAINT `STORED_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) COLLATE utf8_bin NOT NULL,
  `user_password` varchar(40) COLLATE utf8_bin NOT NULL,
  `user_point` double NOT NULL DEFAULT '0',
  `user_rmb` double NOT NULL DEFAULT '0',
  `public_key` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT 'xx',
  `private_key` varchar(150) COLLATE utf8_bin NOT NULL DEFAULT 'xx',
  `user_phone` varchar(45) COLLATE utf8_bin NOT NULL DEFAULT 'xx',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-09 13:49:38
