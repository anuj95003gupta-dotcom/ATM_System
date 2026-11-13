@echo off
if not exist out mkdir out
javac -cp "mysql-connector-java-8.0.28.jar;jcalendar-tz-1.3.3-4.jar" -d out src\*.java
java -cp "out;src;mysql-connector-java-8.0.28.jar;jcalendar-tz-1.3.3-4.jar" bank.management.system.Login
pause
