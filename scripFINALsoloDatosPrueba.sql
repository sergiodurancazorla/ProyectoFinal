-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: incidencias
-- ------------------------------------------------------
-- Server version	5.7.30-log

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
-- Dumping data for table `aula`
--

LOCK TABLES `aula` WRITE;
/*!40000 ALTER TABLE `aula` DISABLE KEYS */;
INSERT INTO `aula` VALUES (1,'Sala informatica'),(2,'Aula 1'),(3,'Aula 2'),(4,'Aula 3'),(5,'Sala de música'),(6,'Sala reuniones'),(7,'Almacen');
/*!40000 ALTER TABLE `aula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `departamento`
--

LOCK TABLES `departamento` WRITE;
/*!40000 ALTER TABLE `departamento` DISABLE KEYS */;
INSERT INTO `departamento` VALUES (1,'Administracion'),(2,'Informatica'),(3,'Filosofia'),(5,'Musica'),(6,'Frances'),(7,'Tecnologia'),(8,'Geografia '),(9,'Ingles');
/*!40000 ALTER TABLE `departamento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` VALUES (1,'Nueva'),(2,'Comunicada'),(3,'En solución'),(4,'Solucionada'),(5,'Solucion inviable');
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
INSERT INTO `incidencia` VALUES (97,4,1,'2021-06-07 00:00:00','2021-06-08 00:02:38','48711760Z',2,1,'La impresora no escanea.','Se ha arreglado actualizando el software. Modificado','48711760Z','2021-06-08 12:47:03',132423,NULL),(99,1,2,'2021-06-08 00:00:00','2021-06-08 01:21:43','52723315R',1,1,'No funciona el excel. ',NULL,NULL,NULL,NULL,NULL),(100,1,2,'2021-06-08 00:00:00','2021-06-08 01:23:16','48711760Z',2,5,'El programa de editor de musica no funciona.',NULL,'52723315R',NULL,NULL,NULL),(102,1,1,'2021-06-08 00:00:00','2021-06-08 11:46:53','48711760Z',1,1,'',NULL,'52723315R',NULL,NULL,NULL),(105,4,1,'2021-06-08 00:00:00','2021-06-08 23:37:43','48711760Z',1,6,'Ordenador no enciende. ','Se ha comprado un nuevo ordenador. ','50323075S','2021-06-08 23:40:16',85216,NULL);
/*!40000 ALTER TABLE `incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `info_hardware`
--

LOCK TABLES `info_hardware` WRITE;
/*!40000 ALTER TABLE `info_hardware` DISABLE KEYS */;
INSERT INTO `info_hardware` VALUES (25,4,'HP','HP-JetPack3267',97),(27,8,'','',102),(28,2,'HP','12412321',105);
/*!40000 ALTER TABLE `info_hardware` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `permiso`
--

LOCK TABLES `permiso` WRITE;
/*!40000 ALTER TABLE `permiso` DISABLE KEYS */;
INSERT INTO `permiso` VALUES (1,'anyadir_indicencias'),(2,'modificar_borrar'),(3,'modificar_hw'),(4,'modificar_rol_permiso'),(5,'importar_exportar'),(6,'informes_incidencias');
/*!40000 ALTER TABLE `permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `profesor`
--

LOCK TABLES `profesor` WRITE;
/*!40000 ALTER TABLE `profesor` DISABLE KEYS */;
INSERT INTO `profesor` VALUES ('48711760Z','Sergio','Duran','Cazorla',1,'sergiodurancazorla@gmail.com','1',2),('50323075S','Hugo','Duran','Cazorla',2,'hugodurancazorla1eso@gmail.com','50323075S',2),('52723315R','Paula','Sanchez','Fernandez',9,'','52723315R',2),('SAI','SAI','SAI','SAI',1,'proyectofinalaplicacion@gmail.com','sai',1),('TIC','TIC','TIC','TIC',1,'proyectofinalcoordinador@gmail.com','tic',3);
/*!40000 ALTER TABLE `profesor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rol`
--

LOCK TABLES `rol` WRITE;
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` VALUES (1,'Administrador'),(2,'Profesor'),(3,'Coordinador TIC'),(4,'Directivo');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `rol_permiso`
--

LOCK TABLES `rol_permiso` WRITE;
/*!40000 ALTER TABLE `rol_permiso` DISABLE KEYS */;
INSERT INTO `rol_permiso` VALUES (1,1,1),(1,2,1),(1,3,1),(1,4,1),(1,5,1),(1,6,1),(2,1,1),(2,2,1),(2,3,0),(2,4,0),(2,5,0),(2,6,1),(3,1,1),(3,2,1),(3,3,1),(3,4,1),(3,5,0),(3,6,1),(4,1,1),(4,2,1),(4,3,1),(4,4,1),(4,5,0),(4,6,1);
/*!40000 ALTER TABLE `rol_permiso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tipo`
--

LOCK TABLES `tipo` WRITE;
/*!40000 ALTER TABLE `tipo` DISABLE KEYS */;
INSERT INTO `tipo` VALUES (1,'Hardware'),(2,'Software');
/*!40000 ALTER TABLE `tipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tipo_harware`
--

LOCK TABLES `tipo_harware` WRITE;
/*!40000 ALTER TABLE `tipo_harware` DISABLE KEYS */;
INSERT INTO `tipo_harware` VALUES (1,'Servidor'),(2,'Ordenador'),(3,'Monitor'),(4,'Impresora'),(5,'Router'),(6,'Switch'),(7,'Proyector'),(8,'Otro tipo');
/*!40000 ALTER TABLE `tipo_harware` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-09  1:08:12
