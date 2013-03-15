@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE WebDriver Grid Hub on 4444

ECHO *********************************************
ECHO *
ECHO * WebDriver grid Hub instance.  
ECHO *
ECHO *********************************************
ECHO.

SET JAR=selenium-server-standalone-2.31.0.jar

IF NOT DEFINED JAVA_HOME (
  ECHO You must define a JAVA_HOME environment variable before you run this script.
  GOTO :ERROR
)
SET "PATH=%JAVA_HOME%\bin;%PATH%"

IF NOT EXIST %JAR% (
  "C:\Program Files (x86)\GnuWin32\bin\wget.exe" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
)

java.exe -version
ECHO.

java.exe -jar %JAR% -role hub -hubConfig HubConfig.json -debug

::java.exe -jar %JAR% -role hub -browser "browserName=firefox,version=19,firefox_binary=C:\Program Files (x86)\Mozilla Firefox\firefox.exe ,maxInstances=5, platform=WINDOWS" -debug

GOTO :END
:ERROR
pause
:END
