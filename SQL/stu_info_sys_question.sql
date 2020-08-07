-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: stu_info_sys
-- ------------------------------------------------------
-- Server version	8.0.20

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
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int NOT NULL AUTO_INCREMENT,
  `question_content` varchar(1000) NOT NULL DEFAULT '',
  `question_title` varchar(100) NOT NULL DEFAULT '',
  `question_answer` varchar(1000) NOT NULL DEFAULT '',
  `sender` varchar(10) NOT NULL,
  `receiver` varchar(10) DEFAULT NULL,
  `send_time` varchar(100) NOT NULL,
  `answer_time` varchar(100) DEFAULT NULL,
  `flag` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'作业内容和提交形式不明','数据库原理作业','OK，我等下群里通知','U201713337','T201713337','2020-07-06 11:03:38','2020-07-06 11:06:09',1),(2,'测试OK！','测试群发内容','','admin','U201700000','2020-07-06 15:11:46',NULL,0),(3,'测试OK！','测试群发内容','','admin','T201713337','2020-07-06 15:11:46',NULL,0),(4,'测试OK！','测试群发内容','','admin','U201713337','2020-07-06 15:11:46',NULL,0),(5,'测试OK！','老师群发消息测试','','T201713337','U201713337','2020-07-06 15:12:44',NULL,0),(6,'恭喜您注册成功','王老师您好','','admin','T201700000','2020-07-06 16:24:18',NULL,0),(7,'2020.7.7','2020.7.7','OK！','U201713337','T201713337','2020-07-07 21:47:56','2020-07-07 21:50:14',1),(8,'2020.7.7','欢迎新同学','','T201713337','U201713330','2020-07-07 21:52:04',NULL,0),(9,'2020.7.7 admin','2020.7.7','','admin','T201713337','2020-07-07 21:54:29',NULL,0),(10,'2020.7.7 admin','2020.7.7','','admin','U201713330','2020-07-07 21:54:29',NULL,0),(11,'2020.7.7 admin','2020.7.7','','admin','U201713337','2020-07-07 21:54:29',NULL,0);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-07-08 10:54:30
