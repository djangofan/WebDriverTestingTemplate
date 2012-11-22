README (Markdown Format)
---------------------------
Selenium WebDriver Testing Template
---------------------------
## Features:
1. For use with JUnit 4.11 or higher
2. For use with Sikuli 1.0.1 or higher
3. Unit tests are parameterized
4. 

---------------------------
## TODO List:

Note: Some of these things might not be possible to implement.

# Log JUnit report results to SQLite DB
# Unit tests read system properties that are set by Gradle (which might force running only from Gradle)
# Display HTML report of JUnit results from Gradle command line by using a XSL transformation of the result.xml file
# Improve code that handles waiting for elements
# Email test results (similar to TestNG emailable report)
# Add .bat  batch script to launch tests and check it into root of project
# Move TODO list to a separate TODO file
# Improve README
# Add "TODO" annotations to existing code to mark where code needs improvements
# Convert project to a Gradle multi-project and have the .bat file handle running multiple projects independently

 
----------------------------
## Configuration Notes:

To get it working on a regular Eclipse (Juno), rather than Spring Source Suite, do the following: 
 
# Download this file to your hard drive: 
http://dist.springsource.com/release/TOOLS/composite/e3.7/bookmarks.xml 
# In Eclipse, go to Help-->Install New Software then click the hyperlink to "Available Software Sites" which brings up a Preferences panel. On that panel , choose Import and import the file downloaded in step #1. 
# Install the new software as Gradle Tooling API 
# Create a new "Other" project of the type "Gradle" and choose type of "Java Quickstart".  
# Edit the build.gradle to what I posted above 
# Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies 
 
Run this project with "gradle clean build --info" and it will execute the unit tests. 
 
Will log output to build/test-output 
 
