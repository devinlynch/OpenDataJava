CREATE DATABASE  IF NOT EXISTS `total_recall` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `total_recall`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: total_recall
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `data_type`
--

DROP TABLE IF EXISTS `data_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_type` (
  `data_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`data_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dataset`
--

DROP TABLE IF EXISTS `dataset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset` (
  `dataset_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `process_frequency` varchar(45) DEFAULT NULL,
  `last_message_time` datetime DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`dataset_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dataset_input`
--

DROP TABLE IF EXISTS `dataset_input`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_input` (
  `dataset_input_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `data_type_id` int(11) NOT NULL,
  `dataset_id` int(11) NOT NULL,
  PRIMARY KEY (`dataset_input_id`),
  KEY `dataset_input_ibfk_1_idx` (`dataset_id`),
  KEY `dataset_input_ibfk_2_idx` (`data_type_id`),
  CONSTRAINT `dataset_input_ibfk_2` FOREIGN KEY (`data_type_id`) REFERENCES `data_type` (`data_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dataset_input_ibfk_1` FOREIGN KEY (`dataset_id`) REFERENCES `dataset` (`dataset_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dataset_record`
--

DROP TABLE IF EXISTS `dataset_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_record` (
  `dataset_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `dataset_id` int(11) DEFAULT NULL,
  `creation_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`dataset_record_id`),
  KEY `dataset_record_ibfk_1_idx` (`dataset_id`),
  CONSTRAINT `dataset_record_ibfk_1` FOREIGN KEY (`dataset_id`) REFERENCES `dataset` (`dataset_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dataset_value`
--

DROP TABLE IF EXISTS `dataset_value`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dataset_value` (
  `dataset_value_id` int(11) NOT NULL AUTO_INCREMENT,
  `dataset_record_id` int(11) NOT NULL,
  `dataset_input_id` int(11) NOT NULL,
  `value` varchar(250) NOT NULL,
  `last_updated` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`dataset_value_id`),
  KEY `dataset_value_ibfk_1_idx` (`dataset_record_id`),
  KEY `dataset_value_ibfk_2_idx` (`dataset_input_id`),
  CONSTRAINT `dataset_value_ibfk_3` FOREIGN KEY (`dataset_record_id`) REFERENCES `dataset_record` (`dataset_record_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `dataset_value_ibfk_2` FOREIGN KEY (`dataset_input_id`) REFERENCES `dataset_input` (`dataset_input_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscribe`
--

DROP TABLE IF EXISTS `subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscribe` (
  `subscribe_id` int(11) NOT NULL AUTO_INCREMENT,
  `email_address` varchar(100) NOT NULL,
  `sent_date` datetime DEFAULT NULL,
  `dataset_id` int(11) NOT NULL,
  PRIMARY KEY (`subscribe_id`),
  KEY `subscribe_ibfk_2_idx` (`dataset_id`),
  CONSTRAINT `subscribe_ibfk_3` FOREIGN KEY (`dataset_id`) REFERENCES `dataset` (`dataset_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscribe_assertion`
--

DROP TABLE IF EXISTS `subscribe_assertion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscribe_assertion` (
  `subscribe_assertion_id` int(11) NOT NULL AUTO_INCREMENT,
  `value` varchar(200) NOT NULL,
  `assertion` int(11) NOT NULL DEFAULT '0',
  `subscribe_id` int(11) NOT NULL,
  PRIMARY KEY (`subscribe_assertion_id`),
  KEY `subscribe_assertion_ibfk_1_idx` (`subscribe_id`),
  CONSTRAINT `subscribe_assertion_ibfk_1` FOREIGN KEY (`subscribe_id`) REFERENCES `subscribe` (`subscribe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscribe_notified`
--

DROP TABLE IF EXISTS `subscribe_notified`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscribe_notified` (
  `subscribe_notified_id` int(11) NOT NULL AUTO_INCREMENT,
  `subscribe_id` int(11) NOT NULL,
  `dataset_record_id` int(11) NOT NULL,
  `notification_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`subscribe_notified_id`),
  UNIQUE KEY `subscribe_notified_un_1` (`subscribe_id`,`dataset_record_id`),
  KEY `subscribe_notified_ibfk_2_idx` (`dataset_record_id`),
  CONSTRAINT `subscribe_notified_ibfk_1` FOREIGN KEY (`subscribe_id`) REFERENCES `subscribe` (`subscribe_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `subscribe_notified_ibfk_2` FOREIGN KEY (`dataset_record_id`) REFERENCES `dataset_record` (`dataset_record_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-28 22:48:23
