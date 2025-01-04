-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: courtcase
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `email` varchar(45) NOT NULL,
  `mobile` varchar(10) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `gender_id` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`email`),
  KEY `fk_admin_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_admin_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('anuksha@gmail.com','0784568902','Anushka','Dinuk','Kataragama',4,'anushkaktg','anushka123er'),('pasindu@gmail.com','0784568901','Pasindu','Lakshan','Kandy',3,'pasindu1234','pasindu@12345');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `case`
--

DROP TABLE IF EXISTS `case`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `case` (
  `case_no` varchar(100) NOT NULL,
  `current_date` date NOT NULL,
  `nextdate` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `caseStatus_id` int NOT NULL,
  `complainant_id` int NOT NULL,
  `caseType_id` int NOT NULL,
  `category_id` int NOT NULL,
  `courtType_id` int NOT NULL,
  `testerReports_id` int NOT NULL,
  `caseNote_id` int NOT NULL,
  `production_id` int NOT NULL,
  `medicalRecords_id` int NOT NULL,
  `transferto` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `previousdate` date DEFAULT NULL,
  `motiondate` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`case_no`),
  KEY `fk_case_complainant1_idx` (`complainant_id`),
  KEY `fk_case_caseType1_idx` (`caseType_id`),
  KEY `fk_case_category1_idx` (`category_id`),
  KEY `fk_case_courtType1_idx` (`courtType_id`),
  KEY `fk_case_testerReports1_idx` (`testerReports_id`),
  KEY `fk_case_caseNote1_idx` (`caseNote_id`),
  KEY `fk_case_production1_idx` (`production_id`),
  KEY `fk_case_medicalRecords1_idx` (`medicalRecords_id`),
  KEY `fk_case_caseStatus1_idx` (`caseStatus_id`),
  CONSTRAINT `fk_case_caseNote1` FOREIGN KEY (`caseNote_id`) REFERENCES `casenote` (`id`),
  CONSTRAINT `fk_case_caseStatus1` FOREIGN KEY (`caseStatus_id`) REFERENCES `casestatus` (`id`),
  CONSTRAINT `fk_case_caseType1` FOREIGN KEY (`caseType_id`) REFERENCES `casetype` (`id`),
  CONSTRAINT `fk_case_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `fk_case_complainant1` FOREIGN KEY (`complainant_id`) REFERENCES `complainant` (`id`),
  CONSTRAINT `fk_case_courtType1` FOREIGN KEY (`courtType_id`) REFERENCES `courttype` (`id`),
  CONSTRAINT `fk_case_medicalRecords1` FOREIGN KEY (`medicalRecords_id`) REFERENCES `medicalrecords` (`id`),
  CONSTRAINT `fk_case_production1` FOREIGN KEY (`production_id`) REFERENCES `production` (`id`),
  CONSTRAINT `fk_case_testerReports1` FOREIGN KEY (`testerReports_id`) REFERENCES `testerreports` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `case`
--

LOCK TABLES `case` WRITE;
/*!40000 ALTER TABLE `case` DISABLE KEYS */;
INSERT INTO `case` VALUES ('CS002','2024-09-03','2024-11-14',3,1,2,1,1,1,2,1,2,'Kasun','2024-09-02','2024-09-20'),('CS003','2024-09-03','Ended Case',3,1,2,1,2,1,2,1,2,'Not Updated','2024-09-14','2024-09-19'),('CS004','2024-09-03','Ended Case',4,1,4,1,1,1,2,1,1,'Not Updated','2024-09-03','2024-09-19'),('CS006','2024-09-04','Ended Case',4,1,4,1,1,1,2,2,2,'Anura','2024-09-04','2024-09-19'),('CS009','2024-09-14','2024-09-24',4,1,4,1,1,1,1,1,2,'Not Updated','2022-07-18','2024-09-19'),('CS010','2024-09-15','Ended Case',4,1,4,1,2,2,1,1,1,'Anushka','2024-09-15','2024-09-19'),('CS012','2024-09-14','2024-09-24',4,1,4,1,1,1,2,1,1,'Not Updated','2024-09-15','2024-09-19'),('CS013','2024-09-16','Ended Case',4,1,4,1,1,2,1,2,1,'nadun','2024-09-14','2024-09-19'),('CS014','2024-09-14','Ended Case',3,2,1,1,1,1,1,1,1,'Not Updated','2024-09-18','2024-09-18');
/*!40000 ALTER TABLE `case` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `casenote`
--

DROP TABLE IF EXISTS `casenote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `casenote` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casenote`
--

LOCK TABLES `casenote` WRITE;
/*!40000 ALTER TABLE `casenote` DISABLE KEYS */;
INSERT INTO `casenote` VALUES (1,'Available'),(2,'Not Available'),(3,'Ended Case');
/*!40000 ALTER TABLE `casenote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `casestatus`
--

DROP TABLE IF EXISTS `casestatus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `casestatus` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casestatus`
--

LOCK TABLES `casestatus` WRITE;
/*!40000 ALTER TABLE `casestatus` DISABLE KEYS */;
INSERT INTO `casestatus` VALUES (3,'Ongoing'),(4,'Ended');
/*!40000 ALTER TABLE `casestatus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `casetype`
--

DROP TABLE IF EXISTS `casetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `casetype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casetype`
--

LOCK TABLES `casetype` WRITE;
/*!40000 ALTER TABLE `casetype` DISABLE KEYS */;
INSERT INTO `casetype` VALUES (1,'New Paint'),(2,'Calling'),(3,'Trial'),(4,'Ended Case');
/*!40000 ALTER TABLE `casetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Murder'),(2,'Land'),(3,'Vehicle');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `complainant`
--

DROP TABLE IF EXISTS `complainant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `complainant` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complainant`
--

LOCK TABLES `complainant` WRITE;
/*!40000 ALTER TABLE `complainant` DISABLE KEYS */;
INSERT INTO `complainant` VALUES (1,'Police'),(2,'RDA');
/*!40000 ALTER TABLE `complainant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courttype`
--

DROP TABLE IF EXISTS `courttype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courttype` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courttype`
--

LOCK TABLES `courttype` WRITE;
/*!40000 ALTER TABLE `courttype` DISABLE KEYS */;
INSERT INTO `courttype` VALUES (1,'MC'),(2,'DC');
/*!40000 ALTER TABLE `courttype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fine` (
  `fine_no` varchar(100) NOT NULL DEFAULT '',
  `caseNo` varchar(100) NOT NULL,
  `receivedFrom` varchar(45) NOT NULL,
  `amount` double NOT NULL DEFAULT (0),
  `date` date NOT NULL,
  PRIMARY KEY (`fine_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
INSERT INTO `fine` VALUES ('FN1726803786660','CS003','Nuwani',400,'2024-09-21'),('FN1726832414839','CS004','Anura',400,'2024-09-20'),('FN1726832436468','CS006','Nayana',250,'2024-09-20');
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (3,'Male'),(4,'Female');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `invoice` (
  `invoiceNo` varchar(100) NOT NULL,
  `date` date NOT NULL,
  `amount` varchar(45) NOT NULL,
  `fine_fine_no` varchar(100) NOT NULL,
  `caseNo` varchar(100) NOT NULL,
  PRIMARY KEY (`invoiceNo`),
  KEY `fk_invoice_fine1_idx` (`fine_fine_no`),
  CONSTRAINT `fk_invoice_fine1` FOREIGN KEY (`fine_fine_no`) REFERENCES `fine` (`fine_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES ('INVC1726853602620','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726889972664','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726903735454','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726970087942','2024-09-22','400.0','FN1726803786660','CS003'),('INVC1726970546447','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726970922583','2024-09-21','400.0','FN1726803786660','CS003'),('INVC1726971161447','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726971185495','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726971374575','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726971519695','2024-09-20','250.0','FN1726832436468','CS006'),('INVC1726971574846','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726988869237','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1726992877521','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1727011857411','2024-09-20','400.0','FN1726832414839','CS004'),('INVC1727011875707','2024-09-20','250.0','FN1726832436468','CS006');
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalrecords`
--

DROP TABLE IF EXISTS `medicalrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalrecords` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalrecords`
--

LOCK TABLES `medicalrecords` WRITE;
/*!40000 ALTER TABLE `medicalrecords` DISABLE KEYS */;
INSERT INTO `medicalrecords` VALUES (1,'Available'),(2,'Not Available'),(3,'Ended Case');
/*!40000 ALTER TABLE `medicalrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `production`
--

DROP TABLE IF EXISTS `production`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `production` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production`
--

LOCK TABLES `production` WRITE;
/*!40000 ALTER TABLE `production` DISABLE KEYS */;
INSERT INTO `production` VALUES (1,'Available'),(2,'Not Available'),(5,'Ended Case');
/*!40000 ALTER TABLE `production` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testerreports`
--

DROP TABLE IF EXISTS `testerreports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `testerreports` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testerreports`
--

LOCK TABLES `testerreports` WRITE;
/*!40000 ALTER TABLE `testerreports` DISABLE KEYS */;
INSERT INTO `testerreports` VALUES (1,'Available'),(2,'Not Available'),(3,'Ended Case');
/*!40000 ALTER TABLE `testerreports` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-22 19:12:49
