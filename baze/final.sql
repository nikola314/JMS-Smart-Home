-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: is1baze
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `alarmi`
--

DROP TABLE IF EXISTS `alarmi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `alarmi` (
  `idalarm` int(11) NOT NULL,
  `vreme` datetime NOT NULL,
  `intervalper` int(11) NOT NULL,
  PRIMARY KEY (`idalarm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `alarmi`
--

LOCK TABLES `alarmi` WRITE;
/*!40000 ALTER TABLE `alarmi` DISABLE KEYS */;
INSERT INTO `alarmi` VALUES (1,'2019-01-23 17:00:00',30),(2,'2019-01-23 17:20:00',0),(3,'2019-01-23 20:00:00',0),(4,'2019-01-23 16:41:00',30),(5,'2019-01-23 17:00:00',30),(6,'2019-01-23 17:50:00',30),(7,'2019-01-23 17:59:01',30),(8,'2019-01-23 18:24:00',120),(9,'2019-01-23 20:45:00',120),(10,'2019-01-23 21:00:00',0),(11,'2019-01-23 20:52:01',0),(12,'2019-01-23 19:56:00',120),(13,'2019-01-23 21:10:00',0),(14,'2019-01-23 22:15:00',0),(15,'2019-01-23 20:00:00',0),(16,'2019-01-23 21:25:00',0),(17,'2019-01-23 21:35:00',0),(18,'2019-01-23 21:13:00',120),(19,'2019-01-23 21:22:00',50),(20,'2019-01-23 21:37:00',60),(21,'2019-01-23 21:48:00',180),(22,'2019-01-23 21:45:00',120),(23,'2019-01-23 21:55:00',70),(24,'2019-01-23 22:03:00',180),(25,'2019-01-23 22:38:00',60),(26,'2019-01-23 22:59:00',0),(27,'2019-01-23 23:10:00',0),(28,'2019-01-24 00:07:52',0),(29,'2019-01-23 23:20:00',300),(30,'2019-01-23 23:29:00',0),(31,'2019-01-24 05:27:41',0),(32,'2019-01-24 19:20:00',0),(33,'2019-01-24 19:45:00',0),(34,'2019-01-24 20:03:00',0);
/*!40000 ALTER TABLE `alarmi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dogadjaji`
--

DROP TABLE IF EXISTS `dogadjaji`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `dogadjaji` (
  `iddogadjaji` int(11) NOT NULL AUTO_INCREMENT,
  `datumvreme` datetime NOT NULL,
  `podsetnik` datetime DEFAULT NULL,
  `nazivdogadjaja` varchar(45) NOT NULL,
  `destinacija` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`iddogadjaji`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dogadjaji`
--

LOCK TABLES `dogadjaji` WRITE;
/*!40000 ALTER TABLE `dogadjaji` DISABLE KEYS */;
INSERT INTO `dogadjaji` VALUES (1,'2019-01-23 17:00:00','2019-01-23 15:00:00','Ispit','Maroko'),(2,'2019-01-24 19:15:00',NULL,'testic','belgrade'),(3,'2019-01-24 18:50:00','2019-01-24 17:50:00','test2','sombor'),(4,'2019-01-24 19:55:00','2019-01-24 18:55:00','neko ime','serbia'),(7,'2019-01-24 20:20:00','2019-01-24 19:20:00','aweniofw','banjaluka'),(9,'2019-01-24 23:30:00','2019-01-24 20:03:00','ime','');
/*!40000 ALTER TABLE `dogadjaji` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `korisnik` (
  `idkorisnik` int(11) NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  PRIMARY KEY (`idkorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Nikola'),(2,'Marko'),(3,'Milorad'),(4,'Nemanja'),(5,'Dimitrije'),(6,'Dusan'),(7,'Milos');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pustanepesme`
--

DROP TABLE IF EXISTS `pustanepesme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `pustanepesme` (
  `idpustanepesme` int(11) NOT NULL AUTO_INCREMENT,
  `nazivpesme` varchar(45) NOT NULL,
  `idkorisnik` int(11) NOT NULL,
  PRIMARY KEY (`idpustanepesme`),
  KEY `idkorisnik_idx` (`idkorisnik`),
  CONSTRAINT `idkorisnik` FOREIGN KEY (`idkorisnik`) REFERENCES `korisnik` (`idkorisnik`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pustanepesme`
--

LOCK TABLES `pustanepesme` WRITE;
/*!40000 ALTER TABLE `pustanepesme` DISABLE KEYS */;
INSERT INTO `pustanepesme` VALUES (1,'Boze boze',1),(2,'Alors on dance',3),(3,'Autotune Zoran Sumadinac',1),(4,'Johnny b goodie',5),(5,'Infinity',1),(6,'With or without you',2),(7,'American pie',3),(8,'In the air',2),(9,'If i cant',4),(10,'Candy shop',5);
/*!40000 ALTER TABLE `pustanepesme` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-25  0:37:43
