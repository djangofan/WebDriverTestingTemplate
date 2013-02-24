@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE WebDriver Grid Hub on 4444
ECHO *********************************************
ECHO *
ECHO * WebDriver grid Hub instance.  
ECHO *
ECHO *********************************************
ECHO.
java.exe -version
ECHO.
java.exe -jar selenium-server-standalone-2.28.0.jar -role hub -hubConfig HubConfig.json

::pause

