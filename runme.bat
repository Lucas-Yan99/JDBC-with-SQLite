@echo off

ECHO Running...

java -cp "sqlite-jdbc-3.7.2.jar;." Coursework University.db > statements.sql

ECHO Compiled with all results written to the statements.sql file, please check the statements.sql file for updates.