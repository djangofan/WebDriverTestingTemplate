@ECHO off

:: Uncomment the next line to override JAVA_HOME on your system
::set JAVA_HOME=C:\Java\jdk1.6.0_33_x32

IF NOT DEFINED JAVA_HOME (
  ECHO JAVA_HOME must be defined as an environment variable.
  GOTO :END
)

IF NOT DEFINED GROOVY_HOME (
  ECHO GROOVY_HOME must be defined as an environment variable.
  GOTO :END
)

IF NOT DEFINED GRADLE_HOME (
  ECHO GRADLE_HOME must be defined as an environment variable.
  GOTO :END
)


SET PATH=%JAVA_HOME%\bin;%GROOVY_HOME%\bin;%GRADLE_HOME%\bin;%PATH%

CALL gradle.bat show clean build run --info

:END
ECHO Closing gradle_run.bat script
FOR /l %%a in (10,-1,1) do (TITLE %TITLETEXT% -- closing in %%as&PING.exe -n 2 -w 1 127.0.0.1>nul)
