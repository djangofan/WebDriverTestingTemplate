@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE WebDriver Grid Node

set CHROME_DRIVER_LOC=chromedriver.exe
:: place IEDriverServer.exe in current path
SET PATH=%CD%;%PATH%

ECHO *********************************************
ECHO *
ECHO * This script will start a WebDriver grid Node instance.  It requires
ECHO * that a WebDriver JSON Hub is already running, usually on port 4444.
ECHO *
ECHO *********************************************
ECHO.
java.exe -version
ECHO.
ECHO ======================
ECHO Grid Hub status : & netstat -an | FIND "4444"
ECHO ======================
ECHO.
java.exe -jar selenium-server-standalone-2.28.0.jar -role node -nodeConfig NodeConfig.json -Dwebdriver.chrome.driver=%CHROME_DRIVER_LOC%

::pause

