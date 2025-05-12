CREATE DATABASE  IF NOT EXISTS `kachina_identity_service` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `kachina_identity_service`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: kachina_identity_service
-- ------------------------------------------------------
-- Server version	5.7.39-log

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
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'USER'),(2,'ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`),
  CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('1462e2a8-c2b1-452b-9557-2284b2ab57a7',1),('1896c0cd-0b30-4115-8fb4-bab0dd84e50d',1),('35fe89bf-5ddd-411a-8194-d293decf6e30',1),('4fc6517b-3c3f-4e65-8796-e3400a2d45dd',1),('74239fa5-cc43-4f66-aec3-b9bbc6161237',1),('8229ed28-e213-4521-a7f7-783d56fd1423',1),('9b5d50ba-6327-448d-9591-9a2480accc08',1),('ae8e4e89-a6f9-4ca2-9a40-8d1bb1b786c4',1),('b8fc42ca-d78a-4df2-a673-738fb5245de8',1),('d1474d5e-9c30-4bac-853d-1bd8bd18bf56',1),('f41845de-7d41-47c3-899a-8556c997e002',1),('f41845de-7d41-47c3-899a-8556c997e002',2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1462e2a8-c2b1-452b-9557-2284b2ab57a7','$2a$10$R6Y.gfhCrhdCxy.YrfqrSOuSmkBtm6vzLX.LdHwTFN/pOVOZCkpFW','thankk123',NULL),('1896c0cd-0b30-4115-8fb4-bab0dd84e50d','$2a$10$d8t3h/jM/U.zw/qPsuOBWutNrM8iQnfLz.mymQ58ww.3QZIBBK7wq','hieu235',NULL),('35fe89bf-5ddd-411a-8194-d293decf6e30','$2a$10$5U.MYGkRBY/1/PBI5YO.ieHvVFjGvth6YvrTuSOqJDF4iq5A8ZwJ6','linh2345',NULL),('4fc6517b-3c3f-4e65-8796-e3400a2d45dd','$2a$10$NRs7i0GH0j/7hudGP1VNIeent9oqcIcggTwYH8d3Bx8EeSj.MbgaC','nqhuy22',NULL),('74239fa5-cc43-4f66-aec3-b9bbc6161237','$2a$10$swMe3GUFxSuYcHnXpMYoHOjfgELSq646yCp5evL0r/N1iqYaIeLwq','thankk',NULL),('8229ed28-e213-4521-a7f7-783d56fd1423','$2a$10$3ohNd.Uru5.dewc7mVAg2u9twWcHBvG2YHv49OTMNuky/bNye6M2i','thong2346',NULL),('9b5d50ba-6327-448d-9591-9a2480accc08','$2a$10$YLxdCXZ0ETsXqyST8HBg9Oeega4g4aEYgAXK6lzDMRIVkMb2amXEO','nguyenquanghuy','nguyenquanghuylt2002@gmail.com'),('ae8e4e89-a6f9-4ca2-9a40-8d1bb1b786c4','$2a$10$0baBOo7anMXrA4bwgcKo.uQgJF2pQw.NBYiUasuwhaTti9tjHdzgO','nguyenquanghuy12345','nguyenquanghuylt2002@gmail.com'),('b8fc42ca-d78a-4df2-a673-738fb5245de8','$2a$10$fJEFel5WGjLI6yrT2.kJFO4TGye67RBCW027D/1TOAAQ0RqxoP3OS','linh23',NULL),('d1474d5e-9c30-4bac-853d-1bd8bd18bf56','$2a$10$.H4Cmk/F9ElxOWwGalDx0.GvF7AQ2B9hjlivBkSL9H7byAvg9DKV2','nguyenquanghuy123','nguyenquanghuylt2002@gmail.com'),('f41845de-7d41-47c3-899a-8556c997e002','$2a$10$CnXWLmmA3geWKbmh7SwOi.AXc5WsJxUVuMWjlTCTv.8.qiXLoaZvW','admin',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-24 12:50:42
