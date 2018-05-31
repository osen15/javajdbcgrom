CREATE TABLE ROOM(
ID NUMBER,
CONSTRAINT ROOM_PK PRIMARY KEY (ID),
HOTEL_ID NUMBER NULL,
CONSTRAINT HOTEL_FK FOREIGN KEY (HOTEL_ID) REFERENCES HOTEL(ID) ON DELETE SET NULL,
NUMBER_OF_GUESTS NUMBER NOT NULL,
PRICE NUMBER(*,2) NOT NULL,
BREAKFAST_INCLUDED NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT BREAKFAST_INCLUDED_CHECK CHECK(BREAKFAST_INCLUDED IN(0,1)),
PETS_ALLOWED NUMBER DEFAULT 0 NOT NULL,
CONSTRAINT PETS_ALLOWED_CHECK CHECK(PETS_ALLOWED IN(0,1)),
DATE_AVAILABLE_FROM DATE
);

CREATE SEQUENCE ROOM_SEQ MINVALUE 1 MAXVALUE 1000 START WITH 1 INCREMENT BY 1;