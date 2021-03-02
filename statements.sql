/*
DatabaseDumper201 JDBC using JAVAVersion:1.0
Author:Lucas Yan
Student ID: 37709313
*/
DROP TABLE IF EXISTS model;
DROP TABLE IF EXISTS features;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS owner;
CREATE TABLE "model" (
 "model_id" INTEGER,
 "manu" VARCHAR(15),
 "range" VARCHAR(15),
 "model" VARCHAR(30),
 PRIMARY KEY ( "model_id" )
),

CREATE TABLE "features" (
 "model_id" INTEGER,
 "description" VARCHAR(40),
 PRIMARY KEY ( "model_id",  "description" )
 FOREIGN KEY ("model_id") REFERENCES "model"("model_id")
),

CREATE TABLE "car" (
 "car_id" INTEGER,
 "model_id" INTEGER,
 "reg_num" VARCHAR(10),
 "mileage" INTEGER,
 PRIMARY KEY ( "car_id" )
 FOREIGN KEY ("model_id") REFERENCES "model"("model_id")
),

CREATE TABLE "employees" (
 "emp_id" INTEGER,
 "name" VARCHAR(40),
 "dept" VARCHAR(20),
 "manager_id" INTEGER,
 PRIMARY KEY ( "emp_id" )
 FOREIGN KEY ("manager_id") REFERENCES "employees"("emp_id")
),

CREATE TABLE "owner" (
 "owner_id" INTEGER,
 "name" VARCHAR(15),
 "car_id" INTEGER,
 PRIMARY KEY ( "owner_id",  "car_id" )
 FOREIGN KEY ("owner_id") REFERENCES "employees"("emp_id")
 FOREIGN KEY ("car_id") REFERENCES "car"("car_id")
),

INSERT INTO "model" VALUES(
1, "Honda", "Jazz", "S");
INSERT INTO "model" VALUES(
1, "Honda", "Jazz", "S");
INSERT INTO "model" VALUES(
1, "Honda", "Jazz", "S");
INSERT INTO "model" VALUES(
1, "Honda", "Jazz", "S");

INSERT INTO "features" VALUES(
1, "Active front headrests");
INSERT INTO "features" VALUES(
1, "Active front headrests");

INSERT INTO "car" VALUES(
1, 1, "aa111gfd", 28000);
INSERT INTO "car" VALUES(
1, 1, "aa111gfd", 28000);
INSERT INTO "car" VALUES(
1, 1, "aa111gfd", 28000);
INSERT INTO "car" VALUES(
1, 1, "aa111gfd", 28000);

INSERT INTO "employees" VALUES(
1, "Sherridan", "Design", null);
INSERT INTO "employees" VALUES(
1, "Sherridan", "Design", null);
INSERT INTO "employees" VALUES(
1, "Sherridan", "Design", null);
INSERT INTO "employees" VALUES(
1, "Sherridan", "Design", null);

INSERT INTO "owner" VALUES(
1, "Picard", 1);
INSERT INTO "owner" VALUES(
1, "Picard", 1);
INSERT INTO "owner" VALUES(
1, "Picard", 1);

null
