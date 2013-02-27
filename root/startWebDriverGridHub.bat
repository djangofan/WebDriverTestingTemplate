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
java.exe -jar selenium-server-standalone-2.30.0.jar -role hub -hubConfig HubConfig.json -debug

::java.exe -jar selenium-server-standalone-2.30.0.jar -role hub -browser "browserName=firefox,version=19,firefox_binary=C:\Program Files (x86)\Mozilla Firefox\firefox.exe ,maxInstances=5, platform=WINDOWS" -debug

::pause

