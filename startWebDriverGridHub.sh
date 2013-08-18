#!/bin/bash

echo "WebDriver Grid Hub on 4444"
echo ""
 
echo "*********************************************"
echo "*"
echo "* WebDriver grid Hub instance."
echo "*"
echo "*  http://localhost:4444/grid/console"
echo "*"
echo "*********************************************"
echo ""
 
jarfile="selenium-server-standalone-2.35.0.jar"
wgetbin="/usr/bin/wget"

if [ -z "${JAVA_HOME+xxx}" ]; then
  echo JAVA_HOME is not set at all;
  exit 1  
fi

echo $jarfile
echo $wgetbin
echo $JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
echo $PATH

if [ ! -f $jarfile ]; then
    echo "Jar file not found!"
    if [ -f $wgetbin ]; then
      echo "Wget binary is found!"
      echo "Downloading Selenium standalone .jar file..."
      echo ""
      $wgetbin --dot-style=binary http://selenium.googlecode.com/files/$jarfile
    else
      echo "Wget.exe is missing."
      echo ""
	  exit 1
    fi
fi

$JAVA_HOME/bin/java -jar $jarfile -role hub -hubConfig ./hubConfig.json -debug
