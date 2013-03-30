@ECHO off
SETLOCAL ENABLEEXTENSIONS ENABLEDELAYEDEXPANSION
:: This script cleans unused WebDriver temp files from your computer
ECHO Cleaning Temp\webdriver*...
for /d %%a in ( %USERPROFILE%\AppData\Local\Temp\webdriver* ) do rmdir /s /q %%a
ECHO Temp\*webdriver-profile...
for /d %%a in ( %USERPROFILE%\AppData\Local\Temp\*webdriver-profile ) do rmdir /s /q %%a
ECHO Clean script is finished.
pause
