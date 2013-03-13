@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE GradleHttpServer

netstat -an | FIND "8080"
IF %ERRORLEVEL%==1 (
  ECHO ALL CLEAR FOR START
) ELSE (
  ECHO SERVER ALREADY RUNNING
  GOTO :END
)

SET "BUILD_HOME=%~dp0"
SET HTTPLIB=lib\httpcore-4.2.2.jar
SET "HTTPROOT=%BUILD_HOME%build\resources\test"

IF NOT EXIST "%HTTPLIB%" (
  ECHO The httpcore library may be missing.  Required to start web server.
  GOTO :END
)

IF EXIST "%HTTPROOT%" (
  ECHO Change directory to ^"%HTTPROOT%^".
  CD "%HTTPROOT%"
) ELSE (
  ECHO The directory ^"%HTTPROOT%^" does not exist.
  GOTO :END
)

SET "CLASSPATH=.;%BUILD_HOME%%HTTPLIB%;%BUILD_HOME%build\classes\main"
ECHO CLASSPATH=%CLASSPATH%
ECHO.
ECHO Starting web server...

java.exe -cp %CLASSPATH% webdriver.test.GradleHttpServer "%HTTPROOT%"

:END
ECHO End of script.  Server stopped.
::timeout 5
