CREATE TABLE `books_to_authors` (
  `BOOK_ID` BINARY(16) NOT NULL,
  `AUTHOR_ID` BINARY(16) NOT NULL,
  KEY `FK_gkqva844hy3bdgdkodri35g6o` (`AUTHOR_ID`),
  KEY `FK_1axc18jsubyvp41j2na9gk796` (`BOOK_ID`),
  CONSTRAINT `FK_1axc18jsubyvp41j2na9gk796` FOREIGN KEY (`BOOK_ID`) REFERENCES `books` (`ID`),
  CONSTRAINT `FK_gkqva844hy3bdgdkodri35g6o` FOREIGN KEY (`AUTHOR_ID`) REFERENCES `authors` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;