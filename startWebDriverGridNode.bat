@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
SET TITLETEXT=WebDriver Grid Node
TITLE %TITLETEXT%
 
SET CHROMEDRIVERZIP=chromedriver_win_26.0.1383.0.zip
SET CHROMEDRIVER=chromedriver.exe
SET JAR=selenium-server-standalone-2.31.0.jar
SET IEDRIVERZIP=IEDriverServer_Win32_2.31.0.zip
SET IEDRIVER=IEDriverServer.exe
SET "WGET=C:\Program Files (x86)\GnuWin32\bin\wget.exe"
 
ECHO *********************************************
ECHO *
ECHO * WebDriver Grid Node
ECHO * It requires that a WebDriver JSON Hub is already running, usually on port 5555.
ECHO * You can run more than one of these if each has its own JSON config file.
ECHO *
ECHO *********************************************
ECHO.
 
IF NOT DEFINED JAVA_HOME (
ECHO You must define a JAVA_HOME environment variable before you run this script.
GOTO :ERROR
)
SET "PATH=.;%JAVA_HOME%\bin;%PATH%"
 
IF NOT EXIST %JAR% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%JAR%
) ELSE (
ECHO Wget.exe is missing. Install GNU utils. & GOTO :ERROR
)
)
 
IF NOT EXIST %IEDRIVER% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary http://selenium.googlecode.com/files/%IEDRIVERZIP%
jar.exe xvf %IEDRIVERZIP%
DEL /Q %IEDRIVERZIP%
) ELSE (
ECHO Wget.exe is missing. & GOTO :ERROR
)
)
 
IF NOT EXIST %CHROMEDRIVER% (
IF EXIST "%WGET%" (
"%WGET%" --dot-style=binary --no-check-certificate https://chromedriver.googlecode.com/files/%CHROMEDRIVERZIP%
jar.exe xvf %CHROMEDRIVERZIP%
DEL /Q %CHROMEDRIVERZIP%
) ELSE (
ECHO Wget.exe is missing. & GOTO :ERROR
)
)
 
ECHO.
ECHO ======================
ECHO Grid Hub status : & netstat -an | FIND "5555"
ECHO ======================
ECHO.
 
TITLE %TITLETEXT%
java.exe -jar %JAR% -role node -nodeConfig node1Config.json -Dwebdriver.chrome.driver=%CHROMEDRIVER%
 
GOTO :END
:ERROR
pause
:END