@ECHO off
SET TITLETEXT=Run Simple Web Server
TITLE %TITLETEXT%

IF DEFINED GROOVY_HOME (
  SET "PATH=%GROOVY_HOME%\bin;%PATH%"
) ELSE (
  ECHO You might want to define GROOVY_HOME as an environment variable.
)

groovy -l 8080 SimpleWebServer 

pause
