CREATE SEQUENCE cat_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "CATEGORY" (
    "ID" integer GENERATED BY DEFAULT AS SEQUENCE cat_id PRIMARY KEY,
    "NAME" character(50) NOT NULL
);

CREATE SEQUENCE dish_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "DISHES" (
    "ID" integer GENERATED BY DEFAULT AS SEQUENCE dish_id PRIMARY KEY,
    "ID_CATEGORY" integer NOT NULL,
    "PRICE" integer,
    "WEIGHT" integer,
    "ISPREPARED" boolean DEFAULT false NOT NULL,
    "ID_EMPLOYEE_PREPARED" integer
);

CREATE SEQUENCE emp_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "EMPLOYEE" (
    "ID" integer GENERATED BY DEFAULT AS SEQUENCE emp_id PRIMARY KEY,
    "FIRST_NAME" character(100) NOT NULL,
    "LAST_NAME" character(100) NOT NULL,
    "DATE_BIRTH" date NOT NULL,
    "ID_POSITION" integer NOT NULL,
    "SALARY" integer
);

COMMENT ON TABLE "EMPLOYEE" IS 'Сотрудник: сотрудник ресторана (официант, повар, менеджер и т.п.) придумайте несколько возможных должностей сотрудников';

CREATE TABLE "INGREDIENTLIST" (
    "ID_INGREDIENT" integer NOT NULL,
    "ID_DISH" integer NOT NULL
);

CREATE TABLE "INGREDIENTS" (
    "ID" integer PRIMARY KEY,
    "NAME" character(50) NOT NULL,
    "AMOUNT" INTEGER DEFAULT 0 NOT NULL
);


CREATE TABLE "MENU" (
    "ID" integer PRIMARY KEY,
    "NAME" character(50) NOT NULL
);

CREATE TABLE "MENULIST" (
    "ID_MENU" integer NOT NULL,
    "ID_DISH" integer NOT NULL
);


CREATE TABLE "ORDERS" (
    "ID" integer PRIMARY KEY,
    "ID_EMP" integer NOT NULL,
    "ID_ORDER_DISHES" integer NOT NULL,
    "TABLE_NUM" integer NOT NULL,
    "DATE" date NOT NULL
);



CREATE TABLE "ORDER_DISHES" (
    "ID" integer NOT NULL,
    "ID_DISH" integer NOT NULL
);

CREATE SEQUENCE pos_id
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE "POSITIONS" (
    "ID" integer GENERATED BY DEFAULT AS SEQUENCE pos_id PRIMARY KEY,
    "POSITION" character(50)
);


INSERT INTO "POSITIONS" ("POSITION") VALUES ('BIG_BOSS');
INSERT INTO "POSITIONS" ("POSITION") VALUES ('NEWBIE');
INSERT INTO "POSITIONS" ("POSITION") VALUES ('MANAGER');
INSERT INTO "EMPLOYEE" (FIRST_NAME, LAST_NAME, DATE_BIRTH, ID_POSITION, SALARY) VALUES ('Mary', 'Ivanova', '1998-08-12', 2, 1000);
INSERT INTO "CATEGORY" ("NAME") VALUES ('SOUPS');
INSERT INTO "INGREDIENTS" ("NAME", AMOUNT) VALUES ('Potato', 2);
INSERT INTO "INGREDIENTS" ("NAME", AMOUNT) VALUES ('Tomato', 3);