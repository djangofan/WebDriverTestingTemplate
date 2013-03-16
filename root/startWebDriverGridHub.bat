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
SET "WGET=C:\Program Files (x86)\GnuWin32\bin\wget.exe"
 
IF NOT DEFINED JAVA_HOME (
ECHO You must define a JAVA_HOME environment variable before you run this script.
GOTO :ERROR
)
SET "PATH=%JAVA_HOME%\bin;%PATH%"
 
IF NOT EXIST %JAR% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
) ELSE (
ECHO Wget.exe is missing. Install GNU Utils. & GOTO :ERROR
)
)
ECHO.
 
java.exe -jar %JAR% -role hub -hubConfig hubConfig.json -debug
 
::java.exe -jar %JAR% -role hub -browser "browserName=firefox,version=19,firefox_binary=C:\Program Files (x86)\Mozilla Firefox\firefox.exe ,maxInstances=5, platform=WINDOWS" -debug
 
GOTO :END
:ERROR
pause
:END