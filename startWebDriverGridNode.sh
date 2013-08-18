#!/bin/bash

echo "WebDriver Grid Hub on 4444"
echo ""
 
echo "*********************************************"
echo "*"
echo "* WebDriver Grid Node"
echo "* It requires that a WebDriver JSON Hub is already running, usually on port 5555."
echo "* You can run more than one of these if each has its own JSON config file."
echo "*"
echo "*********************************************"
echo ""

chromedriverzip=chromedriver_linux64_2.2.zip
chromedriverbin=chromedriver
jarfile="selenium-server-standalone-2.35.0.jar"
wgetbin="/usr/bin/wget"

if [ -z "${JAVA_HOME+xxx}" ]; then
  echo JAVA_HOME is not set at all;
  exit 1  
fi

echo JARFILE=$jarfile
echo WGET=$wgetbin
echo JAVA_HOME=$JAVA_HOME
export PATH="$JAVA_HOME/bin:$PATH"
echo PATH=$PATH

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

if [ ! -f $chromedriverbin ]; then
  if [ ! -f $chromedriverzip ]; then
    echo "Downloading Selenium chromedriver file..."
    echo ""
    $wgetbin --dot-style=binary --no-check-certificate https://chromedriver.googlecode.com/files/$chromedriverzip
  else
    echo "Chrome driver was found in current directory."
    echo ""
  fi
  $JAVA_HOME/bin/jar xvf $chromedriverzip
  rm -rf $chromedriverzip
fi

$JAVA_HOME/bin/java -jar $jarfile -role node -nodeConfig node1ConfigLinux.json -Dwebdriver.chrome.driver=$chromedriverbin
