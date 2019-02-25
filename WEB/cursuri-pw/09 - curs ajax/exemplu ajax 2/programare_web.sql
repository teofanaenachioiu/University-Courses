-- MySQL dump 10.11
--
-- Host: localhost    Database: programare_web
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `trenuri`
--

DROP TABLE IF EXISTS `trenuri`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `trenuri` (
  `plecare` varchar(30) default NULL,
  `sosire` varchar(30) default NULL,
  `ora` int(2) default NULL,
  `minut` int(2) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `trenuri`
--

LOCK TABLES `trenuri` WRITE;
/*!40000 ALTER TABLE `trenuri` DISABLE KEYS */;
INSERT INTO `trenuri` VALUES ('Cluj-Napoca','Brasov',10,0),('Cluj-Napoca','Bucuresti',12,0),('Cluj-Napoca','Constanta',12,0),('Cluj-Napoca','Bucuresti',16,0),('Cluj-Napoca','Iasi',4,0),('Cluj-Napoca','Iasi',8,0),('Iasi','Cluj-Napoca',7,30),('Iasi','Timisoara',10,45),('Iasi','Timisoara',12,30),('Constanta','Bucuresti',7,30),('Constanta','Timisoara',12,40),('Constanta','Timisoara',21,30),('Iasi','Brasov',23,55);
/*!40000 ALTER TABLE `trenuri` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2008-12-03 20:37:40
