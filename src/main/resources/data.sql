DROP SCHEMA IF EXISTS "PUBLIC" CASCADE; 

CREATE TABLE product 
  ( 
     product_id          INTEGER PRIMARY KEY, 
     product_name        VARCHAR(100) NOT NULL, 
     product_description VARCHAR(1000), 
     base_price          NUMERIC, 
     created_date        TIMESTAMP
  );

CREATE SEQUENCE seq_product START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_product 
  ON product (product_id); 

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
     eff_sts		VARCHAR(20),
     created_date   TIMESTAMP, 
     FOREIGN KEY (store_id) REFERENCES store(store_id), 
     FOREIGN KEY (product_id) REFERENCES product(product_id) 
  ); 

CREATE SEQUENCE seq_store_price START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_store_price 
  ON store_price (store_price_id); 
  
CREATE INDEX ind2_store_price 
  ON store_price (store_id, product_id, eff_sts);  

CREATE TABLE price_details_log 
  ( 
     details_id          INTEGER PRIMARY KEY, 
     product_id          INTEGER, 
     average_store_price NUMERIC, 
     lowest_price_id     NUMERIC, 
     highest_price_id    NUMERIC, 
     ideal_store_price   NUMERIC, 
     count_of_prices     INTEGER, 
     eff_sts			 VARCHAR(20),
     created_date        TIMESTAMP, 
     FOREIGN KEY (lowest_price_id) REFERENCES store_price(store_price_id), 
     FOREIGN KEY (highest_price_id) REFERENCES store_price(store_price_id), 
     FOREIGN KEY (product_id) REFERENCES product(product_id) 
  ); 

CREATE SEQUENCE seq_price_details_log START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_price_details_log 
  ON price_details_log (details_id); 

CREATE INDEX ind2_price_details_log 
  ON price_details_log (product_id, eff_sts); 
  
CREATE TABLE price_calculation_request 
  ( 
     calculation_request_id INTEGER UNIQUE, 
     store_price_id_start       INTEGER, 
     store_price_id_end         INTEGER, 
     requested_date         TIMESTAMP, 
     start_date             TIMESTAMP, 
     end_date               TIMESTAMP,
     store_price_id_restart     INTEGER,
	 event_type 			VARCHAR(50),
     job_sts                VARCHAR(50)

  ); 

CREATE SEQUENCE seq_price_calculation_request START WITH 1 INCREMENT BY 1; 

CREATE UNIQUE INDEX ind1_price_calculation_request 
  ON price_calculation_request (calculation_request_id); 

CREATE INDEX ind2_price_calculation_request 
  ON price_calculation_request (job_sts, details_id_end); 

COMMIT; 