-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 02-05-2024 a las 21:08:56
-- Versión del servidor: 8.2.0
-- Versión de PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight`
--

DROP TABLE IF EXISTS `flight`;
CREATE TABLE IF NOT EXISTS `flight` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `destination` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `flight_code` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `flight_price` double DEFAULT NULL,
  `is_complete` bit(1) DEFAULT NULL,
  `origin` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `seat_type` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `seats_available` int DEFAULT NULL,
  `seatsq` int DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight`
--

INSERT INTO `flight` (`id`, `date`, `destination`, `flight_code`, `flight_price`, `is_complete`, `origin`, `seat_type`, `seats_available`, `seatsq`, `status`) VALUES
(1, '2024-10-15', 'Madrid', 'MIMA-1420', 500, b'0', 'Miami', 'Economy', 5, 5, b'1'),
(2, '2024-10-19', 'Miami', 'MAMI-2420', 4320, b'0', 'Madrid', 'Business', 20, 20, b'1'),
(3, '2024-10-18', 'Barcelona', 'MIBA-1235', 650, b'0', 'Miami', 'Economy', 18, 20, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `flight_booking`
--

DROP TABLE IF EXISTS `flight_booking`;
CREATE TABLE IF NOT EXISTS `flight_booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date DEFAULT NULL,
  `destination` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `origin` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `peopleq` int DEFAULT NULL,
  `seat_type` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `flight_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3uiklmjy1d7ba6rrjp6iq08kq` (`flight_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `flight_booking`
--

INSERT INTO `flight_booking` (`id`, `date`, `destination`, `origin`, `peopleq`, `seat_type`, `status`, `total_price`, `flight_id`) VALUES
(1, '2024-10-18', 'Barcelona', 'Miami', 1, 'Economy', b'1', 650, 3),
(2, '2024-10-18', 'Barcelona', 'Miami', 1, 'Economy', b'1', 650, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

DROP TABLE IF EXISTS `hotel`;
CREATE TABLE IF NOT EXISTS `hotel` (
  `hotel_code` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `place` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `status` bit(1) NOT NULL,
  PRIMARY KEY (`hotel_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`hotel_code`, `name`, `place`, `status`) VALUES
('AR-0002', 'Atlantis Resort', 'Miami', b'1'),
('AR-0003', 'Atlantis Resort 2', 'Miami', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person`
--

DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `dni` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `person`
--

INSERT INTO `person` (`dni`, `email`, `last_name`, `name`, `status`) VALUES
('11111111D', 'analopez@gmail.com', 'Lopez', 'Ana', b'1'),
('22222222D', 'juanperez@gmail.com', 'Perez', 'Juan', b'1'),
('33333333D', 'josediaz@gmail.com', 'Diaz', 'Jose', b'1'),
('44444444D', 'rickymartin@gmail.com', 'Martin', 'Ricky', b'1'),
('55555555D', 'ladygaga@gmail.com', 'Gaga', 'Lady', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person_flight_booking`
--

DROP TABLE IF EXISTS `person_flight_booking`;
CREATE TABLE IF NOT EXISTS `person_flight_booking` (
  `flight_booking_id` bigint NOT NULL,
  `person_id` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  KEY `FKntpnt1whj99aaahksije5cfa5` (`person_id`),
  KEY `FK3ft08hg19j4x7nndthr6q14ip` (`flight_booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `person_flight_booking`
--

INSERT INTO `person_flight_booking` (`flight_booking_id`, `person_id`) VALUES
(1, '55555555D'),
(2, '33333333D');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `person_room_booking`
--

DROP TABLE IF EXISTS `person_room_booking`;
CREATE TABLE IF NOT EXISTS `person_room_booking` (
  `room_booking_id` bigint NOT NULL,
  `person_id` varchar(255) COLLATE utf8mb4_spanish_ci NOT NULL,
  KEY `FKh4ysjgtrp09tqjantnpltk7gd` (`person_id`),
  KEY `FKccmkihfoayap0rc3cjgptdmaw` (`room_booking_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `person_room_booking`
--

INSERT INTO `person_room_booking` (`room_booking_id`, `person_id`) VALUES
(1, '11111111D'),
(1, '22222222D');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `is_booked` bit(1) DEFAULT NULL,
  `room_code` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `room_price` double NOT NULL,
  `room_type` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `hotel_id` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdosq3ww4h9m2osim6o0lugng8` (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room`
--

INSERT INTO `room` (`id`, `is_booked`, `room_code`, `room_price`, `room_type`, `status`, `hotel_id`) VALUES
(1, b'0', '101S', 60, 'Single', b'1', 'AR-0002'),
(2, b'0', '111S', 60, 'Single', b'1', 'AR-0003'),
(3, b'1', '102D', 80, 'Double', b'1', 'AR-0002');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `room_booking`
--

DROP TABLE IF EXISTS `room_booking`;
CREATE TABLE IF NOT EXISTS `room_booking` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `nights` int NOT NULL,
  `peopleq` int DEFAULT NULL,
  `place` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `room_type` varchar(255) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `total_price` double NOT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiwt0ws97ta91ukd4xonewjbxl` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

--
-- Volcado de datos para la tabla `room_booking`
--

INSERT INTO `room_booking` (`id`, `date_from`, `date_to`, `nights`, `peopleq`, `place`, `room_type`, `status`, `total_price`, `room_id`) VALUES
(1, '2024-05-01', '2024-05-03', 2, 2, 'Miami', 'Double', b'1', 160, 3);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `flight_booking`
--
ALTER TABLE `flight_booking`
  ADD CONSTRAINT `FK3uiklmjy1d7ba6rrjp6iq08kq` FOREIGN KEY (`flight_id`) REFERENCES `flight` (`id`);

--
-- Filtros para la tabla `person_flight_booking`
--
ALTER TABLE `person_flight_booking`
  ADD CONSTRAINT `FK3ft08hg19j4x7nndthr6q14ip` FOREIGN KEY (`flight_booking_id`) REFERENCES `flight_booking` (`id`),
  ADD CONSTRAINT `FKntpnt1whj99aaahksije5cfa5` FOREIGN KEY (`person_id`) REFERENCES `person` (`dni`);

--
-- Filtros para la tabla `person_room_booking`
--
ALTER TABLE `person_room_booking`
  ADD CONSTRAINT `FKccmkihfoayap0rc3cjgptdmaw` FOREIGN KEY (`room_booking_id`) REFERENCES `room_booking` (`id`),
  ADD CONSTRAINT `FKh4ysjgtrp09tqjantnpltk7gd` FOREIGN KEY (`person_id`) REFERENCES `person` (`dni`);

--
-- Filtros para la tabla `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `FKdosq3ww4h9m2osim6o0lugng8` FOREIGN KEY (`hotel_id`) REFERENCES `hotel` (`hotel_code`);

--
-- Filtros para la tabla `room_booking`
--
ALTER TABLE `room_booking`
  ADD CONSTRAINT `FKiwt0ws97ta91ukd4xonewjbxl` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
