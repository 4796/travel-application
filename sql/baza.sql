/*
SQLyog Community v13.3.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - rmt2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rmt2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `rmt2`;

/*Table structure for table `korisnik` */

DROP TABLE IF EXISTS `korisnik`;

CREATE TABLE `korisnik` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `ime` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `JMBG` varchar(255) DEFAULT NULL,
  `pasos` varchar(255) DEFAULT NULL,
  `datumRodjenja` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `korisnik` */

insert  into `korisnik`(`id`,`username`,`password`,`ime`,`prezime`,`email`,`JMBG`,`pasos`,`datumRodjenja`) values 
(1,'a','aa','lazar','nikolic','mejl','123','1234','2000-12-13'),
(2,'b','b','bb','bb','b','222','222','2000-12-13'),
(3,'ja','ja','aa','aa','e','111','111','2000-12-13'),
(4,'rr','rrr','rr','rr','rr','2','2','2000-12-13'),
(5,'ww','ww','w','w','w','1','1','2000-12-13'),
(6,'mila','123','mila','nikolic','em','321','4321','2020-12-13'),
(7,'q','qq','q','q','e','3','3','2000-12-13');

/*Table structure for table `posetazemlje` */

DROP TABLE IF EXISTS `posetazemlje`;

CREATE TABLE `posetazemlje` (
  `putovanje` int(11) NOT NULL,
  `zemlja` varchar(255) NOT NULL,
  PRIMARY KEY (`putovanje`,`zemlja`),
  CONSTRAINT `posetazemlje_ibfk_1` FOREIGN KEY (`putovanje`) REFERENCES `putovanje` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `posetazemlje` */

insert  into `posetazemlje`(`putovanje`,`zemlja`) values 
(1,'Francuska'),
(1,'Nemacka'),
(2,'Italija'),
(3,'Spanija'),
(12,'Italija'),
(12,'Spanija'),
(13,'Italija'),
(13,'Nemacka'),
(13,'Spanija'),
(14,'Francuska'),
(14,'Italija'),
(14,'Nemacka'),
(14,'Spanija'),
(15,'Nemacka'),
(16,'Italija'),
(17,'Francuska'),
(18,'Nemacka'),
(19,'Italija'),
(20,'Italija'),
(23,'Francuska'),
(25,'Francuska'),
(26,'Francuska'),
(27,'Francuska'),
(29,'Nemacka'),
(30,'Nemacka');

/*Table structure for table `putovanje` */

DROP TABLE IF EXISTS `putovanje`;

CREATE TABLE `putovanje` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `korisnik` int(11) DEFAULT NULL,
  `datumUlaska` date DEFAULT NULL,
  `datumIzlaska` date DEFAULT NULL,
  `prevoz` varchar(255) DEFAULT NULL,
  `datumPrijave` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `korisnik` (`korisnik`),
  CONSTRAINT `putovanje_ibfk_1` FOREIGN KEY (`korisnik`) REFERENCES `korisnik` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `putovanje` */

insert  into `putovanje`(`id`,`korisnik`,`datumUlaska`,`datumIzlaska`,`prevoz`,`datumPrijave`) values 
(1,7,'2024-12-11','2024-12-15','autobus','2024-12-09'),
(2,1,'2024-12-11','2024-12-21','autobus','2024-12-09'),
(3,7,'2024-12-11','2024-12-21','autobus','2024-12-09'),
(4,7,'2024-12-22','2024-12-23','autobus','2024-12-10'),
(6,7,'2024-12-22','2024-12-25','avio_prevoz','2024-12-10'),
(11,7,'2025-01-07','2025-01-08','putnicki_automobil','2024-12-10'),
(12,7,'2025-01-07','2025-01-08','putnicki_automobil','2024-12-10'),
(13,7,'2025-01-09','2025-01-09','putnicki_automobil','2024-12-10'),
(14,7,'2025-01-13','2025-01-13','putnicki_automobil','2024-12-10'),
(15,7,'2025-01-15','2025-01-15','putnicki_automobil','2024-12-10'),
(16,7,'2025-01-15','2025-01-16','putnicki_automobil','2024-12-10'),
(17,7,'2025-01-18','2025-01-18','putnicki_automobil','2024-12-10'),
(18,7,'2025-01-18','2025-01-19','putnicki_automobil','2024-12-10'),
(19,7,'2025-01-18','2025-01-21','putnicki_automobil','2024-12-10'),
(20,7,'2024-12-12','2024-12-12','putnicki_automobil','2024-12-11'),
(23,7,'2025-01-07','2025-01-08','avio_prevoz','2024-12-12'),
(25,7,'2024-12-22','2024-12-23','avio_prevoz','2024-12-12'),
(26,7,'2024-12-25','2024-12-25','putnicki_automobil','2024-12-12'),
(27,7,'2024-12-30','2024-12-30','putnicki_automobil','2024-12-12'),
(29,7,'2025-01-07','2025-01-08','putnicki_automobil','2024-12-12'),
(30,7,'2024-12-25','2024-12-27','putnicki_automobil','2024-12-24');

/*Table structure for table `stanovnik` */

DROP TABLE IF EXISTS `stanovnik`;

CREATE TABLE `stanovnik` (
  `ime` varchar(255) DEFAULT NULL,
  `prezime` varchar(255) DEFAULT NULL,
  `JMBG` varchar(255) DEFAULT NULL,
  `pasos` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `datumRodjenja` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `stanovnik` */

insert  into `stanovnik`(`ime`,`prezime`,`JMBG`,`pasos`,`id`,`datumRodjenja`) values 
('lazar','nikolic','123','1234',1,'2000-12-13'),
('mila','nikolic','321','4321',2,'2020-12-13'),
('zeljko','obradovic','243521','2423',3,'2000-12-13'),
('aleksandar','vucic','2412','3242',4,'1900-12-13'),
('aa','aa','111','111',5,'2000-12-13'),
('bb','bb','222','222',6,'2000-12-13'),
('rr','rr','2','2',7,'2000-12-13'),
('w','w','1','1',8,'2000-12-13'),
('q','q','3','3',9,'2000-12-13');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
