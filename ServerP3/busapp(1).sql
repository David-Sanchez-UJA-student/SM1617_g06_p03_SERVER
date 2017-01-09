-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 09-01-2017 a las 17:08:41
-- Versión del servidor: 5.5.24-log
-- Versión de PHP: 5.4.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `busapp`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `billetes`
--

CREATE TABLE IF NOT EXISTS `billetes` (
  `IDbillete` int(3) NOT NULL AUTO_INCREMENT,
  `Origen` varchar(10) NOT NULL,
  `Destino` varchar(10) NOT NULL,
  `Fecha` varchar(10) NOT NULL,
  `Hora` varchar(5) NOT NULL,
  `Precio` float NOT NULL,
  PRIMARY KEY (`IDbillete`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Volcado de datos para la tabla `billetes`
--

INSERT INTO `billetes` (`IDbillete`, `Origen`, `Destino`, `Fecha`, `Hora`, `Precio`) VALUES
(1, 'Granada', 'Malaga', '18-10-2016', '20-30', 5.5),
(2, 'Jaen', 'Granada', '10-10-2016', '20-30', 4.5),
(3, 'Madrid', 'Barcelona', '10-10-2016', '10-10', 20.8),
(4, 'Valencia', 'Almeria', '10-10-2016', '10-10', 132.5),
(5, 'Almeria', 'Jaen', '10-10-2016', '10-10', 4),
(7, 'Granada', 'Malaga', '18-10-2016', '10-40', 5.5);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
