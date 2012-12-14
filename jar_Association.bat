@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION
:: this script creates a file association for executable .jar files
ECHO Creating .jar file association...
ECHO JAVA_HOME is %JAVA_HOME%
IF NOT DEFINED JAVA_HOME GOTO :FAIL
REG ADD "HKCR\jarfile" /ve /t REG_SZ /d "Executable Jar File" /f
REG ADD "HKCR\jarfile\shell" /ve /f
REG ADD "HKCR\jarfile\shell\open" /ve /f
ECHO REG ADD "HKCR\jarfile\shell\open\command" /ve /t REG_SZ /d "\"%JAVA_HOME%\bin\javaw.exe\" -jar \"%%1\" %%*" /f
REG ADD "HKCR\jarfile\shell\open\command" /ve /t REG_SZ /d "\"%JAVA_HOME%\bin\javaw.exe\" -jar \"%%1\" %%**" /f
REG ADD "HKLM\jarfile" /ve /t REG_SZ /d "Executable Jar File" /f
REG ADD "HKLM\SOFTWARE\Classes\jarfile\shell" /ve /f
REG ADD "HKLM\SOFTWARE\Classes\jarfile\shell\open" /ve /f
REG ADD "HKLM\SOFTWARE\Classes\jarfile\shell\open\command" /ve /t REG_SZ /d "\"%JAVA_HOME%\bin\javaw.exe\" -jar \"%%1\" %%*" /f
ECHO Finished creating .jar file association for executable .jar files.
PAUSE
GOTO EOF
:FAIL
ECHO Script failed. JAVA_HOME not defined.
PAUSE
