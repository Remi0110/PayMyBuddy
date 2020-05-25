CREATE DATABASE  IF NOT EXISTS `PayMyBuddy` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `PayMyBuddy`;
-- MySQL dump 10.13  Distrib 8.0.17, for macos10.14 (x86_64)
--
-- Host: localhost    Database: PayMyBuddy
-- ------------------------------------------------------
-- Server version 8.0.17

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `type_account` varchar(2) NOT NULL,
  `code_account` varchar(255) NOT NULL,
  `date_creation` datetime DEFAULT NULL,
  `solde` double NOT NULL,
  `overdraft` double DEFAULT NULL,
  `code_user` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`code_account`),
  KEY `FKco8vayseqh6wcjrcqb7bojfge` (`code_user`),
  CONSTRAINT `FKco8vayseqh6wcjrcqb7bojfge` FOREIGN KEY (`code_user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--
SET autocommit = 0;
LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES ('BA','186cdb45-3b8a-4ff2-9fcc-19cf149c394f','2020-05-25 11:12:02',1134.01,NULL,1),('BA','43a4eb99-2627-4abb-b1c2-d82960a690f2','2020-05-25 11:13:41',1673.73,NULL,2),('PA','49bd964f-9c43-4ac5-8fce-ea5cd4549592','2020-05-25 11:16:01',88,400,3),('PA','62e1cc0a-6683-45f2-b3e5-e2543dc1185b','2020-05-25 11:13:41',0,400,2),('PA','bfc67cad-1d0c-4f69-b4c4-9428a230e85b','2020-05-25 11:12:02',-92.4,400,1),('BA','e272a607-b9b8-443e-87e0-b4c31fc27499','2020-05-25 11:16:01',1194.91,NULL,3);
COMMIT;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `number` bigint(20) NOT NULL,
  `amount` double NOT NULL,
  `date_transaction` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `code_account` varchar(255) DEFAULT NULL,
  `code_account2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `FKeic9v8a4cf6krn8f1dw0fi9mt` (`code_account`),
  KEY `FK2gqacl5q1oe3vuswjyh3eexw4` (`code_account2`),
  CONSTRAINT `FK2gqacl5q1oe3vuswjyh3eexw4` FOREIGN KEY (`code_account2`) REFERENCES `account` (`code_account`),
  CONSTRAINT `FKeic9v8a4cf6krn8f1dw0fi9mt` FOREIGN KEY (`code_account`) REFERENCES `account` (`code_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--
SET autocommit = 0;
LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,88,'2020-05-25 11:16:53','virement concert','bfc67cad-1d0c-4f69-b4c4-9428a230e85b','49bd964f-9c43-4ac5-8fce-ea5cd4549592');
COMMIT;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--
SET autocommit = 0;
LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'marie.dale@gmail.com','Marie','Dale','$2a$10$riPPPrqFK4ETbRScmr.zVe4eL0I.gYEsgRBBR4SVoTsqYrUCLACSm'),(2,'david.boyle@gmail.com','David','Boyle','$2a$10$K0IHkT9PVESLe1xWkVbJYuT2P6wbDwqmQR/AyxhRYT2GjjQFRAuMC'),(3,'samantha.fisher@gmail.com','Samantha','Fisher','$2a$10$iznftj5xEakDUiYMKuPyBeM1hN1LSqU4.pkVr/1D3DU851WsbQMMu');
COMMIT;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_friends`
--

DROP TABLE IF EXISTS `user_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_friends` (
  `adding_user` bigint(20) NOT NULL,
  `added_user` bigint(20) NOT NULL,
  KEY `FK6glv60aae89vlq4d9jtgy7bnq` (`added_user`),
  KEY `FKd2g59gfxjlmp7gs2lffes32n1` (`adding_user`),
  CONSTRAINT `FK6glv60aae89vlq4d9jtgy7bnq` FOREIGN KEY (`added_user`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKd2g59gfxjlmp7gs2lffes32n1` FOREIGN KEY (`adding_user`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_friends`
--
SET autocommit = 0;
LOCK TABLES `user_friends` WRITE;
/*!40000 ALTER TABLE `user_friends` DISABLE KEYS */;
INSERT INTO `user_friends` VALUES (1,3),(1,2);
COMMIT;
/*!40000 ALTER TABLE `user_friends` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-25 13:19:49
