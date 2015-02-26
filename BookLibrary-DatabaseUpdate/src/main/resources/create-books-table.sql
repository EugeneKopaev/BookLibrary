CREATE TABLE `books` (
  `ID` BINARY(16) NOT NULL,
  `CHANGED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `CREATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ISBN` bigint(20) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `PUBLISH_YEAR` int(11) NOT NULL,
  `PUBLISHER` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;