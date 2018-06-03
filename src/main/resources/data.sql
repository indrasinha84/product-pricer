DROP SCHEMA IF EXISTS "PUBLIC" CASCADE;
 

CREATE TABLE PRODUCT (
PRODUCT_ID INTEGER PRIMARY KEY,
PRODUCT_NAME VARCHAR(100) NOT NULL,
PRODUCT_DESCRIPTION  VARCHAR(1000),
BASE_PRICE NUMERIC,
CREATED_DATE TIMESTAMP,
LATEST_DETAILS_ID INTEGER,
--LATEST_PRICE_ID INTEGER
);
CREATE SEQUENCE SEQ_PRODUCT START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX IND1_PRODUCT ON PRODUCT (PRODUCT_ID);
--CREATE INDEX IND2_PRODUCT ON PRODUCT (LATEST_PRICE_ID);

-- Create index on LATEST_PRICE_ID


CREATE TABLE STORE (
STORE_ID INTEGER PRIMARY KEY,
STORE_NAME VARCHAR(100),
STORE_DESCRIPTION  VARCHAR(1000),
CREATED_DATE TIMESTAMP
);
CREATE SEQUENCE SEQ_STORE START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX IND1_STORE ON STORE (STORE_ID);

CREATE TABLE STORE_PRICE (
STORE_PRICE_ID INTEGER PRIMARY KEY,
STORE_ID INTEGER,
PRODUCT_ID INTEGER,
PRODUCT_NOTES  VARCHAR(1000),
STORE_PRICE NUMERIC,
CREATED_DATE TIMESTAMP,
FOREIGN KEY (STORE_ID) REFERENCES STORE(STORE_ID),
FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);
CREATE SEQUENCE SEQ_STORE_PRICE START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX IND1_STORE_PRICE ON STORE_PRICE (STORE_PRICE_ID);




CREATE TABLE PRICE_DETAILS_LOG (
DETAILS_ID INTEGER PRIMARY KEY,
PRODUCT_ID INTEGER,
AVERAGE_STORE_PRICE NUMERIC,
LOWEST_PRICE_ID INTEGER,
HIGHEST_PRICE_ID INTEGER,
IDEAL_STORE_PRICE NUMERIC,
COUNT_OF_PRICES INTEGER,
CREATED_DATE TIMESTAMP,
FOREIGN KEY (LOWEST_PRICE_ID) REFERENCES STORE_PRICE(STORE_PRICE_ID),
FOREIGN KEY (HIGHEST_PRICE_ID) REFERENCES STORE_PRICE(STORE_PRICE_ID),
FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);

CREATE SEQUENCE SEQ_PRICE_DETAILS_LOG START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX IND1_PRICE_DETAILS_LOG ON PRICE_DETAILS_LOG (DETAILS_ID);




ALTER TABLE PRODUCT ADD FOREIGN KEY (LATEST_DETAILS_ID) REFERENCES PRICE_DETAILS_LOG(DETAILS_ID);
--ALTER TABLE PRODUCT ADD FOREIGN KEY (LATEST_PRICE_ID) REFERENCES STORE_PRICE(STORE_PRICE_ID);


CREATE TABLE PRICE_CALCULATION_REQUEST (
CALCULATION_REQUEST_ID INTEGER UNIQUE,
DETAILS_ID_START INTEGER,
DETAILS_ID_END INTEGER,
REQUESTED_DATE TIMESTAMP,
START_DATE TIMESTAMP,
END_DATE TIMESTAMP
);

CREATE SEQUENCE SEQ_PRICE_CALCULATION_REQUEST START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX IND1_PRICE_CALCULATION_REQUEST ON PRICE_CALCULATION_REQUEST (CALCULATION_REQUEST_ID);

--- Index on CALCULATION_REQUEST_ID

INSERT INTO PRODUCT VALUES(NEXT VALUE FOR SEQ_PRODUCT, 'Hello', 'hi', 100, current_timestamp , NULL);
INSERT INTO PRODUCT VALUES(NEXT VALUE FOR SEQ_PRODUCT, 'Hello2', 'hi', 100, current_timestamp , NULL);


COMMIT;