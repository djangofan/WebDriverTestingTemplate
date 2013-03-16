@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
TITLE GradleHttpServer

IF NOT DEFINED JAVA_HOME (
  ECHO You must define a valid JAVA_HOME environment variable before you run this script.
  GOTO :ERROR
)
SET "PATH=.;%JAVA_HOME%\bin;%PATH%"

netstat -an | FIND "8080"
IF %ERRORLEVEL%==1 (
  ECHO ALL CLEAR FOR START
) ELSE (
  ECHO SERVER ALREADY RUNNING
  GOTO :END
)

SET "SCRIPT_HOME=%~dp0"
SET "BUILD_HOME=%SCRIPT_HOME%build"
SET HTTPLIB=httpcore-4.2.2.jar
SET "HTTPROOT=%SCRIPT_HOME%src\test\resources"

IF NOT EXIST "%BUILD_HOME%\classes\main\webdriver\test\GradleHttpServer.class" (
  ECHO You need to compile GradleHttpServer.java before this will run.
  ECHO Expecting it at: ^"%BUILD_HOME%\classes\main\webdriver\test\GradleHttpServer.class^"
  CD "%SCRIPT_HOME%\..\root"
  gradle.bat commonlib:compileJava parallelwebdriver:compileJava
  ECHO Finished compiling.  Try script again.
  GOTO :ERROR
)

IF NOT EXIST "%SCRIPT_HOME%lib\%HTTPLIB%" (
  ECHO The httpcore library may be missing from ^"%SCRIPT_HOME%lib\%HTTPLIB%^".
  ECHO Required to start web server.
  GOTO :ERROR
)

IF EXIST "%HTTPROOT%" (
  ECHO Change directory to ^"%HTTPROOT%^".
  CD "%HTTPROOT%"
) ELSE (
  ECHO The directory ^"%HTTPROOT%^" does not exist.
  GOTO :ERROR
)

SET "CLASSPATH=.;%SCRIPT_HOME%lib\%HTTPLIB%;%BUILD_HOME%\classes\main"
ECHO CLASSPATH=%CLASSPATH%
ECHO.
ECHO Starting web server...

java.exe -cp %CLASSPATH% webdriver.test.GradleHttpServer "%HTTPROOT%"

GOTO :END

:ERROR
pause
:END
ECHO End of script.  Server stopped.
