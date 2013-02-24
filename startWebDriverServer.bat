@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
ECHO.
ECHO The projects in this template are written to instantiate the ^"WebDriver
ECHO JSON Hub Server^" as part of the unit tests.  If you really intend to
ECHO continue and start this server manually, make sure you understand the 
ECHO implications.
ECHO.
ECHO Click any key to continue and start the server...
ECHO.
pause>nul
timeout 10

java -jar selenium-server-standalone-2.28.0.jar maxInstances=5 maxSessions=5

pause

