@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
SET TITLETEXT=WebDriver Grid Node
TITLE %TITLETEXT%

set CHROME_DRIVER_LOC=chromedriver.exe
SET JAR=selenium-server-standalone-2.31.0.jar
SET IEDRIVERZIP=IEDriverServer_Win32_2.31.0.zip
SET IEDRIVER=IEDriverServer.exe

ECHO *********************************************
ECHO *
ECHO * This script will start a WebDriver grid Node instance.  It requires
ECHO * that a WebDriver JSON Hub is already running, usually on port 4444.
ECHO *
ECHO *********************************************
ECHO.

IF NOT DEFINED JAVA_HOME (
  ECHO You must define a JAVA_HOME environment variable before you run this script.
  GOTO :ERROR
)
SET "PATH=.;%JAVA_HOME%\bin;%PATH%"

IF NOT EXIST %JAR% (
  "C:\Program Files (x86)\GnuWin32\bin\wget.exe" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
)

IF NOT EXIST %IEDRIVER% (
  "C:\Program Files (x86)\GnuWin32\bin\wget.exe" --dot-style=binary http://selenium.googlecode.com/files/%IEDRIVERZIP%
  jar.exe xvf %IEDRIVERZIP%
  DEL /Q %IEDRIVERZIP%
)

java.exe -version
ECHO.
ECHO.
ECHO ======================
ECHO Grid Hub status : & netstat -an | FIND "4444"
ECHO ======================
ECHO.

TITLE %TITLETEXT%
java.exe -jar %JAR% -role node -nodeConfig Node1Config.json -Dwebdriver.chrome.driver=%CHROME_DRIVER_LOC%

GOTO :END
:ERROR
pause
:END
