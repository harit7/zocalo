-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: zdb
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

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
-- Table structure for table `ACCOUNTS`
--

DROP TABLE IF EXISTS `ACCOUNTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCOUNTS` (
  `ACCOUNTS_ID` bigint(20) NOT NULL,
  `cash` bigint(20) NOT NULL,
  PRIMARY KEY (`ACCOUNTS_ID`),
  KEY `FKAF43ABE618085ABA` (`cash`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACCOUNTS`
--

LOCK TABLES `ACCOUNTS` WRITE;
/*!40000 ALTER TABLE `ACCOUNTS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACCOUNTS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ACCOUNT_HOLDINGS`
--

DROP TABLE IF EXISTS `ACCOUNT_HOLDINGS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCOUNT_HOLDINGS` (
  `ACCOUNTS_ID` bigint(20) NOT NULL,
  `COUPONS_ID` bigint(20) NOT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ACCOUNTS_ID`,`POSITIONS_ID`),
  KEY `FKAB0B6C024BC7993` (`COUPONS_ID`),
  KEY `FKAB0B6C023AA42D41` (`POSITIONS_ID`),
  KEY `FKAB0B6C02F513C021` (`ACCOUNTS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACCOUNT_HOLDINGS`
--

LOCK TABLES `ACCOUNT_HOLDINGS` WRITE;
/*!40000 ALTER TABLE `ACCOUNT_HOLDINGS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACCOUNT_HOLDINGS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ASKS`
--

DROP TABLE IF EXISTS `ASKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ASKS` (
  `ASKS_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`ASKS_ID`),
  KEY `FK1ECD1A38904A85` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ASKS`
--

LOCK TABLES `ASKS` WRITE;
/*!40000 ALTER TABLE `ASKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ASKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BEST_ASKS`
--

DROP TABLE IF EXISTS `BEST_ASKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BEST_ASKS` (
  `BEST_ASKS_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`BEST_ASKS_ID`),
  KEY `FKF4AB869538904A85` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BEST_ASKS`
--

LOCK TABLES `BEST_ASKS` WRITE;
/*!40000 ALTER TABLE `BEST_ASKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `BEST_ASKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BEST_BIDS`
--

DROP TABLE IF EXISTS `BEST_BIDS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BEST_BIDS` (
  `BEST_BIDS_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`BEST_BIDS_ID`),
  KEY `FKF4ABD49138904A85` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BEST_BIDS`
--

LOCK TABLES `BEST_BIDS` WRITE;
/*!40000 ALTER TABLE `BEST_BIDS` DISABLE KEYS */;
/*!40000 ALTER TABLE `BEST_BIDS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BIDS`
--

DROP TABLE IF EXISTS `BIDS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BIDS` (
  `BID_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`BID_ID`),
  KEY `FK1F1B1638904A85` (`pos`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BIDS`
--

LOCK TABLES `BIDS` WRITE;
/*!40000 ALTER TABLE `BIDS` DISABLE KEYS */;
/*!40000 ALTER TABLE `BIDS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BOOK`
--

DROP TABLE IF EXISTS `BOOK`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK` (
  `BOOK_ID` bigint(20) NOT NULL,
  `BOOK_TYPE` varchar(255) NOT NULL,
  `market` bigint(20) NOT NULL,
  `claim` bigint(20) NOT NULL,
  PRIMARY KEY (`BOOK_ID`),
  UNIQUE KEY `market` (`market`),
  KEY `FK1F32E978D5D654` (`market`),
  KEY `FK1F32E94C01F0EF` (`claim`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOOK`
--

LOCK TABLES `BOOK` WRITE;
/*!40000 ALTER TABLE `BOOK` DISABLE KEYS */;
/*!40000 ALTER TABLE `BOOK` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BOOK_OFFERS`
--

DROP TABLE IF EXISTS `BOOK_OFFERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOOK_OFFERS` (
  `BOOK_ID` bigint(20) NOT NULL,
  `SORTEDORDERS_ID` bigint(20) NOT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`BOOK_ID`,`POSITIONS_ID`),
  KEY `FK21F087ADAF241F6D` (`SORTEDORDERS_ID`),
  KEY `FK21F087AD458F91F6` (`BOOK_ID`),
  KEY `FK21F087AD3AA42D41` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOOK_OFFERS`
--

LOCK TABLES `BOOK_OFFERS` WRITE;
/*!40000 ALTER TABLE `BOOK_OFFERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `BOOK_OFFERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CASHBANKS`
--

DROP TABLE IF EXISTS `CASHBANKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CASHBANKS` (
  `CASHBANK_ID` bigint(20) NOT NULL,
  `token` bigint(20) NOT NULL,
  PRIMARY KEY (`CASHBANK_ID`),
  UNIQUE KEY `token` (`token`),
  KEY `FKD3585E44B5A0569A` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CASHBANKS`
--

LOCK TABLES `CASHBANKS` WRITE;
/*!40000 ALTER TABLE `CASHBANKS` DISABLE KEYS */;
/*INSERT INTO `CASHBANKS` VALUES (1,1);*/
/*!40000 ALTER TABLE `CASHBANKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CBANK_POS_TOKENS`
--

DROP TABLE IF EXISTS `CBANK_POS_TOKENS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CBANK_POS_TOKENS` (
  `COUPONBANK_ID` bigint(20) NOT NULL,
  `CURRENCYTOKEN_ID` bigint(20) NOT NULL,
  `positions` bigint(20) NOT NULL,
  PRIMARY KEY (`COUPONBANK_ID`,`positions`),
  KEY `FKB63B0945A95DC501` (`COUPONBANK_ID`),
  KEY `FKB63B09459E4F249B` (`positions`),
  KEY `FKB63B0945152634D3` (`CURRENCYTOKEN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CBANK_POS_TOKENS`
--

LOCK TABLES `CBANK_POS_TOKENS` WRITE;
/*!40000 ALTER TABLE `CBANK_POS_TOKENS` DISABLE KEYS */;
/*INSERT INTO `CBANK_POS_TOKENS` VALUES (1,2,1),(1,3,2);*/
/*!40000 ALTER TABLE `CBANK_POS_TOKENS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLAIMS`
--

DROP TABLE IF EXISTS `CLAIMS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLAIMS` (
  `CLAIMS_ID` bigint(20) NOT NULL,
  `CLAIM_TYPE` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `owner` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CLAIMS_ID`),
  KEY `FK76A253B7CB114E02` (`owner`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLAIMS`
--

LOCK TABLES `CLAIMS` WRITE;
/*!40000 ALTER TABLE `CLAIMS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CLAIMS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COUPONBANKS`
--

DROP TABLE IF EXISTS `COUPONBANKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COUPONBANKS` (
  `COUPONBANK_ID` bigint(20) NOT NULL,
  `minted` decimal(19,4) DEFAULT NULL,
  `redeemed` decimal(19,4) DEFAULT NULL,
  `FUNDS_ID` bigint(20) NOT NULL,
  `claim` bigint(20) NOT NULL,
  PRIMARY KEY (`COUPONBANK_ID`),
  UNIQUE KEY `FUNDS_ID` (`FUNDS_ID`),
  KEY `FKE782A5316A36D033` (`FUNDS_ID`),
  KEY `FKE782A5317E3EEE70` (`claim`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COUPONBANKS`
--

LOCK TABLES `COUPONBANKS` WRITE;
/*!40000 ALTER TABLE `COUPONBANKS` DISABLE KEYS */;
INSERT INTO `COUPONBANKS` VALUES (1,25.86,20.00,3,1);
/*!40000 ALTER TABLE `COUPONBANKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COUPONS`
--

DROP TABLE IF EXISTS `COUPONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COUPONS` (
  `COUPONS_ID` bigint(20) NOT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `token` bigint(20) NOT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`COUPONS_ID`),
  KEY `FK63E3B8ED3AA42D41` (`POSITIONS_ID`),
  KEY `FK63E3B8EDB5A0569A` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COUPONS`
--

LOCK TABLES `COUPONS` WRITE;
/*!40000 ALTER TABLE `COUPONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `COUPONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CURRENCIES`
--

DROP TABLE IF EXISTS `CURRENCIES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CURRENCIES` (
  `CURRENCY_ID` bigint(20) NOT NULL,
  `quant` decimal(10,0) DEFAULT NULL,
  `token` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`CURRENCY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CURRENCIES`
--

LOCK TABLES `CURRENCIES` WRITE;
/*!40000 ALTER TABLE `CURRENCIES` DISABLE KEYS */;
/*!40000 ALTER TABLE `CURRENCIES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CURRENCYTOKENS`
--

DROP TABLE IF EXISTS `CURRENCYTOKENS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CURRENCYTOKENS` (
  `CURRENCYTOKEN_ID` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CURRENCYTOKEN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CURRENCYTOKENS`
--

LOCK TABLES `CURRENCYTOKENS` WRITE;
/*!40000 ALTER TABLE `CURRENCYTOKENS` DISABLE KEYS */;
/*!40000 ALTER TABLE `CURRENCYTOKENS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FUNDSS`
--

DROP TABLE IF EXISTS `FUNDSS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FUNDSS` (
  `FUNDS_ID` bigint(20) NOT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `token` bigint(20) NOT NULL,
  PRIMARY KEY (`FUNDS_ID`),
  KEY `FK7C4587E5B5A0569A` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FUNDSS`
--

LOCK TABLES `FUNDSS` WRITE;
/*!40000 ALTER TABLE `FUNDSS` DISABLE KEYS */;
/*!40000 ALTER TABLE `FUNDSS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MARKET`
--

DROP TABLE IF EXISTS `MARKET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MARKET` (
  `MARKETS_ID` bigint(20) NOT NULL,
  `MARKET_TYPE` varchar(255) NOT NULL,
  `priceBetteringRequired` bit(1) DEFAULT NULL,
  `wholeShareTradingOnly` bit(1) DEFAULT NULL,
  `lastTrade` datetime DEFAULT NULL,
  `marketClosed` bit(1) DEFAULT NULL,
  `owner` bigint(20) NOT NULL,
  `couponMint` bigint(20) NOT NULL,
  `Outcome_ID` bigint(20) DEFAULT NULL,
  `claim` bigint(20) NOT NULL,
  `maxPrice` decimal(19,4) DEFAULT NULL,
  `maxPriceMax` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`MARKETS_ID`),
  UNIQUE KEY `Outcome_ID` (`Outcome_ID`),
  KEY `FK871F883CCB114E02` (`owner`),
  KEY `FK871F883C760F7B71` (`couponMint`),
  KEY `FK871F883CF5AE661E` (`Outcome_ID`),
  KEY `FK871F883C7E3EEE70` (`claim`),
  CONSTRAINT `FK871F883C760F7B71` FOREIGN KEY (`couponMint`) REFERENCES `COUPONBANKS` (`COUPONBANK_ID`),
  CONSTRAINT `FK871F883C7E3EEE70` FOREIGN KEY (`claim`) REFERENCES `CLAIMS` (`CLAIMS_ID`),
  CONSTRAINT `FK871F883CF5AE661E` FOREIGN KEY (`Outcome_ID`) REFERENCES `OUTCOME` (`OUTCOME_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MARKET`
--

LOCK TABLES `MARKET` WRITE;
/*!40000 ALTER TABLE `MARKET` DISABLE KEYS */;
/*!40000 ALTER TABLE `MARKET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MARKETMAKER`
--

DROP TABLE IF EXISTS `MARKETMAKER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MARKETMAKER` (
  `MARKETMAKER_ID` bigint(20) NOT NULL,
  `MarketMaker_TYPE` varchar(255) NOT NULL,
  `beta` decimal(19,4) DEFAULT NULL,
  `betaQuadratic` decimal(19,4) DEFAULT NULL,
  `betaSpherical` decimal(19,4) DEFAULT NULL,
  `market` bigint(20) NOT NULL,
  `accounts` bigint(20) NOT NULL,
  `probability` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`MARKETMAKER_ID`),
  UNIQUE KEY `market` (`market`),
  UNIQUE KEY `accounts` (`accounts`),
  KEY `FK9213FD6878D5D654` (`market`),
  KEY `FK9213FD68420317F3` (`accounts`),
  CONSTRAINT `FK9213FD68420317F3` FOREIGN KEY (`accounts`) REFERENCES `ACCOUNTS` (`ACCOUNTS_ID`),
  CONSTRAINT `FK9213FD6878D5D654` FOREIGN KEY (`market`) REFERENCES `MARKET` (`MARKETS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MARKETMAKER`
--

LOCK TABLES `MARKETMAKER` WRITE;
/*!40000 ALTER TABLE `MARKETMAKER` DISABLE KEYS */;
INSERT INTO `MARKETMAKER` VALUES (1,'Multi',14.43,20.00,24.14,1,3,NULL);
/*!40000 ALTER TABLE `MARKETMAKER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MM_Probabilities`
--

DROP TABLE IF EXISTS `MM_Probabilities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MM_Probabilities` (
  `MARKETMAKER_ID` bigint(20) NOT NULL,
  `probability` decimal(19,4) DEFAULT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKETMAKER_ID`,`POSITIONS_ID`),
  KEY `FK1D59E3143AA42D41` (`POSITIONS_ID`),
  KEY `FK1D59E3145AE3447D` (`MARKETMAKER_ID`),
  CONSTRAINT `FK1D59E3143AA42D41` FOREIGN KEY (`POSITIONS_ID`) REFERENCES `POSITIONS` (`POSITIONS_ID`),
  CONSTRAINT `FK1D59E3145AE3447D` FOREIGN KEY (`MARKETMAKER_ID`) REFERENCES `MARKETMAKER` (`MARKETMAKER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MM_Probabilities`
--

LOCK TABLES `MM_Probabilities` WRITE;
/*!40000 ALTER TABLE `MM_Probabilities` DISABLE KEYS */;
/*!40000 ALTER TABLE `MM_Probabilities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MM_Stocks`
--

DROP TABLE IF EXISTS `MM_Stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MM_Stocks` (
  `MARKETMAKER_ID` bigint(20) NOT NULL,
  `stock` decimal(19,4) DEFAULT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`MARKETMAKER_ID`,`POSITIONS_ID`),
  KEY `FKCF8B7BC3AA42D41` (`POSITIONS_ID`),
  KEY `FKCF8B7BC5AE3447D` (`MARKETMAKER_ID`),
  CONSTRAINT `FKCF8B7BC3AA42D41` FOREIGN KEY (`POSITIONS_ID`) REFERENCES `POSITIONS` (`POSITIONS_ID`),
  CONSTRAINT `FKCF8B7BC5AE3447D` FOREIGN KEY (`MARKETMAKER_ID`) REFERENCES `MARKETMAKER` (`MARKETMAKER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MM_Stocks`
--

LOCK TABLES `MM_Stocks` WRITE;
/*!40000 ALTER TABLE `MM_Stocks` DISABLE KEYS */;
/*!40000 ALTER TABLE `MM_Stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDERREMOVALS`
--

DROP TABLE IF EXISTS `ORDERREMOVALS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDERREMOVALS` (
  `ORDERREMOVAL_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `fullfilledQuant` decimal(19,4) DEFAULT NULL,
  `voidedQuant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`ORDERREMOVAL_ID`),
  KEY `FK3E1573538904A85` (`pos`),
  CONSTRAINT `FK3E1573538904A85` FOREIGN KEY (`pos`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDERREMOVALS`
--

LOCK TABLES `ORDERREMOVALS` WRITE;
/*!40000 ALTER TABLE `ORDERREMOVALS` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORDERREMOVALS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ORDER_TBL`
--

DROP TABLE IF EXISTS `ORDER_TBL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ORDER_TBL` (
  `ORDER_ID` bigint(20) NOT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `fullfilled` decimal(19,4) DEFAULT NULL,
  `voided` decimal(19,4) DEFAULT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ORDER_ID`),
  KEY `FK8ED4BB0DBBF8495E` (`USER_ID`),
  KEY `FK8ED4BB0D3AA42D41` (`POSITIONS_ID`),
  CONSTRAINT `FK8ED4BB0D3AA42D41` FOREIGN KEY (`POSITIONS_ID`) REFERENCES `POSITIONS` (`POSITIONS_ID`),
  CONSTRAINT `FK8ED4BB0DBBF8495E` FOREIGN KEY (`USER_ID`) REFERENCES `USERS` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ORDER_TBL`
--

LOCK TABLES `ORDER_TBL` WRITE;
/*!40000 ALTER TABLE `ORDER_TBL` DISABLE KEYS */;
/*!40000 ALTER TABLE `ORDER_TBL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `OUTCOME`
--

DROP TABLE IF EXISTS `OUTCOME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `OUTCOME` (
  `OUTCOME_ID` bigint(20) NOT NULL,
  `OUTCOME_TYPE` varchar(255) NOT NULL,
  `continuous` bit(1) DEFAULT NULL,
  `POSITIONS_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OUTCOME_ID`),
  KEY `FKE8D793323AA42D41` (`POSITIONS_ID`),
  CONSTRAINT `FKE8D793323AA42D41` FOREIGN KEY (`POSITIONS_ID`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `OUTCOME`
--

LOCK TABLES `OUTCOME` WRITE;
/*!40000 ALTER TABLE `OUTCOME` DISABLE KEYS */;
/*!40000 ALTER TABLE `OUTCOME` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `POSITIONS`
--

DROP TABLE IF EXISTS `POSITIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `POSITIONS` (
  `POSITIONS_ID` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `CLAIMS_ID` bigint(20) NOT NULL,
  `idx` int(11) DEFAULT NULL,
  PRIMARY KEY (`POSITIONS_ID`),
  KEY `FKBD89C4A66AE28D7` (`CLAIMS_ID`),
  CONSTRAINT `FKBD89C4A66AE28D7` FOREIGN KEY (`CLAIMS_ID`) REFERENCES `CLAIMS` (`CLAIMS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `POSITIONS`
--

LOCK TABLES `POSITIONS` WRITE;
/*!40000 ALTER TABLE `POSITIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `POSITIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PROBABILITY`
--

DROP TABLE IF EXISTS `PROBABILITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROBABILITY` (
  `Probability_ID` bigint(20) NOT NULL,
  `quant` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`Probability_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PROBABILITY`
--

LOCK TABLES `PROBABILITY` WRITE;
/*!40000 ALTER TABLE `PROBABILITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `PROBABILITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QUANTITY`
--

DROP TABLE IF EXISTS `QUANTITY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `QUANTITY` (
  `QUANTITY_ID` bigint(20) NOT NULL,
  `quant` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`QUANTITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QUANTITY`
--

LOCK TABLES `QUANTITY` WRITE;
/*!40000 ALTER TABLE `QUANTITY` DISABLE KEYS */;
/*!40000 ALTER TABLE `QUANTITY` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REDEMPTIONS`
--

DROP TABLE IF EXISTS `REDEMPTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `REDEMPTIONS` (
  `Redemption_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`Redemption_ID`),
  KEY `FK6170EF8838904A85` (`pos`),
  CONSTRAINT `FK6170EF8838904A85` FOREIGN KEY (`pos`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REDEMPTIONS`
--

LOCK TABLES `REDEMPTIONS` WRITE;
/*!40000 ALTER TABLE `REDEMPTIONS` DISABLE KEYS */;
/*!40000 ALTER TABLE `REDEMPTIONS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SELFDEALING`
--

DROP TABLE IF EXISTS `SELFDEALING`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SELFDEALING` (
  `SELFDEALING_ID` bigint(20) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  PRIMARY KEY (`SELFDEALING_ID`),
  KEY `FK10726B0A38904A85` (`pos`),
  CONSTRAINT `FK10726B0A38904A85` FOREIGN KEY (`pos`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SELFDEALING`
--

LOCK TABLES `SELFDEALING` WRITE;
/*!40000 ALTER TABLE `SELFDEALING` DISABLE KEYS */;
/*!40000 ALTER TABLE `SELFDEALING` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SORTEDORDERS`
--

DROP TABLE IF EXISTS `SORTEDORDERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SORTEDORDERS` (
  `SORTEDORDERS_ID` bigint(20) NOT NULL,
  `POSITIONS_ID` bigint(20) NOT NULL,
  `samplePrice` decimal(19,4) DEFAULT NULL,
  `samplePriceMax` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`SORTEDORDERS_ID`),
  KEY `FKEF719BE23AA42D41` (`POSITIONS_ID`),
  CONSTRAINT `FKEF719BE23AA42D41` FOREIGN KEY (`POSITIONS_ID`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SORTEDORDERS`
--

LOCK TABLES `SORTEDORDERS` WRITE;
/*!40000 ALTER TABLE `SORTEDORDERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `SORTEDORDERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SORTEDORDERS_POS_ORDER`
--

DROP TABLE IF EXISTS `SORTEDORDERS_POS_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SORTEDORDERS_POS_ORDER` (
  `SORTEDORDERS_ID` bigint(20) NOT NULL,
  `ORDER_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`SORTEDORDERS_ID`,`ORDER_ID`),
  UNIQUE KEY `ORDER_ID` (`ORDER_ID`),
  KEY `FKD8B35546AF241F6D` (`SORTEDORDERS_ID`),
  KEY `FKD8B355466DA91987` (`ORDER_ID`),
  CONSTRAINT `FKD8B355466DA91987` FOREIGN KEY (`ORDER_ID`) REFERENCES `ORDER_TBL` (`ORDER_ID`),
  CONSTRAINT `FKD8B35546AF241F6D` FOREIGN KEY (`SORTEDORDERS_ID`) REFERENCES `SORTEDORDERS` (`SORTEDORDERS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SORTEDORDERS_POS_ORDER`
--

LOCK TABLES `SORTEDORDERS_POS_ORDER` WRITE;
/*!40000 ALTER TABLE `SORTEDORDERS_POS_ORDER` DISABLE KEYS */;
/*!40000 ALTER TABLE `SORTEDORDERS_POS_ORDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `TRADES`
--

DROP TABLE IF EXISTS `TRADES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TRADES` (
  `TRADE_ID` bigint(20) NOT NULL,
  `TRADE_TYPE` varchar(255) NOT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `price` decimal(19,4) DEFAULT NULL,
  `priceMax` decimal(19,4) DEFAULT NULL,
  `quant` decimal(19,4) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `pos` bigint(20) NOT NULL,
  `openingPrice` decimal(19,4) DEFAULT NULL,
  `openingPriceMax` decimal(19,4) DEFAULT NULL,
  `closingPrice` decimal(19,4) DEFAULT NULL,
  `closingPriceMax` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`TRADE_ID`),
  KEY `FK93F92F8F38904A85` (`pos`),
  CONSTRAINT `FK93F92F8F38904A85` FOREIGN KEY (`pos`) REFERENCES `POSITIONS` (`POSITIONS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TRADES`
--

LOCK TABLES `TRADES` WRITE;
/*!40000 ALTER TABLE `TRADES` DISABLE KEYS */;
/*!40000 ALTER TABLE `TRADES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UNCONFIRMED_USERS`
--

DROP TABLE IF EXISTS `UNCONFIRMED_USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `UNCONFIRMED_USERS` (
  `USER_ID` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `emailAddress` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `confirmationToken` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UNCONFIRMED_USERS`
--

LOCK TABLES `UNCONFIRMED_USERS` WRITE;
/*!40000 ALTER TABLE `UNCONFIRMED_USERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `UNCONFIRMED_USERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USERS`
--

DROP TABLE IF EXISTS `USERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS` (
  `USER_ID` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `hashedPassword` tinyblob,
  `accounts` bigint(20) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `accounts` (`accounts`),
  KEY `FK4D495E8420317F3` (`accounts`),
  CONSTRAINT `FK4D495E8420317F3` FOREIGN KEY (`accounts`) REFERENCES `ACCOUNTS` (`ACCOUNTS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USERS`
--

LOCK TABLES `USERS` WRITE;
/*!40000 ALTER TABLE `USERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `USERS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-11 22:20:32