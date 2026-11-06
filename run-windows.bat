@echo off
cd /d "%~dp0"
if not exist bin mkdir bin
javac -cp "biuoop-1.4.jar" -d bin src\*.java
if errorlevel 1 (pause & exit /b 1)
java -cp "bin;biuoop-1.4.jar" Ass6Game %*
pause
