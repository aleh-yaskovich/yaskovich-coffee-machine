DROP TABLE IF EXISTS INGREDIENT;
DROP TABLE IF EXISTS BEVERAGE;

CREATE TABLE INGREDIENT
(
    INGREDIENT_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    INGREDIENT_TITLE VARCHAR(50) UNIQUE,
    INGREDIENT_QUANTITY INT,
    INGREDIENT_EXPIRATION_DATE DATE,
    INGREDIENT_PRICE NUMBER(6,2),
    INGREDIENT_REQUIRED BOOLEAN
);

CREATE TABLE BEVERAGE
(
    BEVERAGE_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    BEVERAGE_TITLE VARCHAR(50) UNIQUE,
    BEVERAGE_ING_COFFEE INT(4) NOT NULL,
    BEVERAGE_ING_MILK INT(4) NOT NULL,
    BEVERAGE_ING_CHOCOLATE INT(4) NOT NULL,
    BEVERAGE_ING_WATER INT(4) NOT NULL,
    BEVERAGE_ING_SUGAR BOOLEAN NOT NULL,
    BEVERAGE_ING_SYRUP BOOLEAN NOT NULL,
    BEVERAGE_ING_CINNAMON BOOLEAN NOT NULL,
    BEVERAGE_HIDDEN BOOLEAN NOT NULL
);