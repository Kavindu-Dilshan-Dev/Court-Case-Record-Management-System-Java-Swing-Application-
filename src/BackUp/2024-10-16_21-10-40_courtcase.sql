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
INSERT INTO `admin` VALUES ('hambantotacourt@gmail.com','0705563138','Hambantota','Court','Hambantota',3,'admin','hambantota@123');
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
INSERT INTO `case` VALUES ('1111','2024-10-12','2232',1,2,1,2,1,1,1,1,1,'RK','2024-10-12','Not Updated'),('2222222','2024-10-12','2322',1,2,1,1,1,1,1,2,1,'rk','2024-10-12','Not Updated'),('2609/2026','2024-10-11','2025-06-11',1,6,2,7,1,2,2,1,2,'Not Updated','2024-10-11','Not Updated'),('2936/23','2024-10-11','2025-06-18',1,1,2,2,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('3333','2024-10-14','33',1,1,1,2,1,1,1,1,1,'RK','2024-10-14','Not Updated'),('3490/23','2024-10-11','Ended Case',2,17,4,4,2,2,1,1,2,'Not Updated','2024-10-11','2024-10-16'),('36995(BR740/16)','2024-10-11','Ended Case',2,1,2,2,1,2,2,2,2,'Not Updated','2024-09-11','Not Updated'),('4423/23','2024-10-11','Ended Case',2,6,2,5,1,2,2,2,1,'Not Updated','2024-10-11','Not Updated'),('555','2024-10-15','3',1,2,1,1,1,1,1,2,1,'Not Updated','2024-10-15','Not Updated'),('5555','2024-10-16','Ended Case',2,2,4,2,1,2,2,2,2,'RK','2024-10-16','Not Updated'),('55646','2024-10-11','Ended Case',2,1,2,2,2,2,1,1,1,'RK','2024-10-11','Not Updated'),('5646/24','2024-10-11','Ended Case',2,6,1,2,1,2,2,2,1,'Not Updated','2024-10-11','Not Updated'),('56571','2024-10-11','Ended Case',2,1,4,5,1,3,2,5,3,'Not Updated','2024-10-11','Not Updated'),('5662/24','2024-10-11','Ended Case',2,1,2,2,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('5663/24','2024-10-11','Ended Case',2,1,1,2,1,2,2,2,1,'Not Updated','2024-10-11','Not Updated'),('5671/24','2024-10-11','Ended Case',2,9,1,6,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('5673/24','2024-10-11','2024-12-19',1,10,1,6,1,2,2,1,2,'Not Updated','2024-10-11','Not Updated'),('5772/24','2024-10-11','2024-12-19',1,16,1,6,1,2,2,1,2,'Not Updated','2024-10-11','Not Updated'),('5774/24','2024-10-11','2024-12-19',1,9,1,6,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('5775/24','2024-10-11','2024-12-19',1,9,1,6,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('5793/24','2024-10-11','2024-11-06',1,6,1,7,1,2,2,2,2,'susanthi','2024-10-11','Not Updated'),('62294(BR1608/20)','2024-10-11','Ended Case',2,1,2,2,1,2,2,2,2,'Not Updated','2024-10-11','Not Updated'),('66699(BR1055/21)','2024-10-11','End',1,1,2,2,1,2,2,2,1,'Not Updated','2024-10-11','Not Updated'),('BR1398/17','2024-10-11','2025-02-19',1,1,1,2,1,1,2,2,2,'Not Updated','2024-10-11','Not Updated'),('BR2289/24','2024-10-11','2025-04-23',1,1,1,4,1,2,2,1,2,'Not Updated','2024-10-11','Not Updated'),('BR5619/23','2024-10-11','2025-06-18',1,1,2,1,1,1,2,1,1,'Not Updated','2024-10-11','Not Updated');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casenote`
--

LOCK TABLES `casenote` WRITE;
/*!40000 ALTER TABLE `casenote` DISABLE KEYS */;
INSERT INTO `casenote` VALUES (1,'Available'),(2,'Not Avialable');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `casestatus`
--

LOCK TABLES `casestatus` WRITE;
/*!40000 ALTER TABLE `casestatus` DISABLE KEYS */;
INSERT INTO `casestatus` VALUES (1,'Ongoing'),(2,'Ended Case');
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
INSERT INTO `casetype` VALUES (1,'Calling'),(2,'Trail'),(3,'New Paint'),(4,'Ended');
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'ODD'),(2,'PC'),(3,'NS'),(4,'E'),(5,'MT'),(6,'S'),(7,'MISC'),(8,'CO'),(9,'LB'),(10,'FM'),(11,'66'),(12,'81');
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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complainant`
--

LOCK TABLES `complainant` WRITE;
/*!40000 ALTER TABLE `complainant` DISABLE KEYS */;
INSERT INTO `complainant` VALUES (1,'HT'),(2,'SU'),(3,'SCIB'),(4,'COO'),(5,'WL'),(6,'HU'),(7,'MTT'),(8,'PHI'),(9,'AGS'),(10,'CU'),(11,'DO'),(12,'CC'),(13,'UC'),(14,'LB'),(15,'FS'),(16,'AC'),(17,'ED');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalrecords`
--

LOCK TABLES `medicalrecords` WRITE;
/*!40000 ALTER TABLE `medicalrecords` DISABLE KEYS */;
INSERT INTO `medicalrecords` VALUES (1,'Available'),(2,'Not Available');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `production`
--

LOCK TABLES `production` WRITE;
/*!40000 ALTER TABLE `production` DISABLE KEYS */;
INSERT INTO `production` VALUES (1,'Available'),(2,'Not Available');
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testerreports`
--

LOCK TABLES `testerreports` WRITE;
/*!40000 ALTER TABLE `testerreports` DISABLE KEYS */;
INSERT INTO `testerreports` VALUES (1,'Available'),(2,'Not Available');
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

-- Dump completed on 2024-10-16 21:10:41
