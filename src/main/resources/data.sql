DROP SCHEMA IF EXISTS "PUBLIC" cascade; 

CREATE TABLE product 
  ( 
     product_id          INTEGER PRIMARY KEY, 
     product_name        VARCHAR(100) NOT NULL, 
     product_description VARCHAR(1000), 
     base_price          NUMERIC, 
     created_date        TIMESTAMP, 
     latest_details_id   INTEGER 
  ); 

CREATE SEQUENCE seq_product START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_product 
  ON product (product_id); 

--CREATE INDEX IND2_PRODUCT ON PRODUCT (LATEST_PRICE_ID); 
-- Create index on LATEST_PRICE_ID 
CREATE TABLE store 
  ( 
     store_id          INTEGER PRIMARY KEY, 
     store_name        VARCHAR(100), 
     store_description VARCHAR(1000), 
     created_date      TIMESTAMP 
  ); 

CREATE SEQUENCE seq_store START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_store 
  ON store (store_id); 

CREATE TABLE store_price 
  ( 
     store_price_id INTEGER PRIMARY KEY, 
     store_id       INTEGER, 
     product_id     INTEGER, 
     product_notes  VARCHAR(1000), 
     store_price    NUMERIC, 
     created_date   TIMESTAMP, 
     FOREIGN KEY (store_id) REFERENCES store(store_id), 
     FOREIGN KEY (product_id) REFERENCES product(product_id) 
  ); 

CREATE SEQUENCE seq_store_price START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_store_price 
  ON store_price (store_price_id); 

CREATE TABLE price_details_log 
  ( 
     details_id          INTEGER PRIMARY KEY, 
     product_id          INTEGER, 
     average_store_price NUMERIC, 
     lowest_price_id     INTEGER, 
     highest_price_id    INTEGER, 
     ideal_store_price   NUMERIC, 
     count_of_prices     INTEGER, 
     created_date        TIMESTAMP, 
     FOREIGN KEY (lowest_price_id) REFERENCES store_price(store_price_id), 
     FOREIGN KEY (highest_price_id) REFERENCES store_price(store_price_id), 
     FOREIGN KEY (product_id) REFERENCES product(product_id) 
  ); 

CREATE SEQUENCE seq_price_details_log START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_price_details_log 
  ON price_details_log (details_id); 

ALTER TABLE product 
  ADD FOREIGN KEY (latest_details_id) REFERENCES price_details_log(details_id); 

--ALTER TABLE PRODUCT ADD FOREIGN KEY (LATEST_PRICE_ID) REFERENCES STORE_PRICE(STORE_PRICE_ID); 
CREATE TABLE price_calculation_request 
  ( 
     calculation_request_id INTEGER UNIQUE, 
     details_id_start       INTEGER, 
     details_id_end         INTEGER, 
     requested_date         TIMESTAMP, 
     start_date             TIMESTAMP, 
     end_date               TIMESTAMP 
  ); 

CREATE SEQUENCE seq_price_calculation_request START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_price_calculation_request 
  ON price_calculation_request (calculation_request_id); 

--- Index on CALCULATION_REQUEST_ID 
INSERT INTO product 
VALUES     (next VALUE FOR seq_product, 
            'Hello', 
            'hi', 
            100, 
            CURRENT_TIMESTAMP, 
            NULL); 

INSERT INTO product 
VALUES     (next VALUE FOR seq_product, 
            'Hello2', 
            'hi', 
            100, 
            CURRENT_TIMESTAMP, 
            NULL); 

COMMIT; 