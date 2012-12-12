######<a href="http://github.com/djangofan/WebDriverTestingTemplate/blob/master/TODO.md">Link: TODO List</a>

# Info

This is a Java project that can be used as a template to start a WebDriver web browser testing project.  I chose not to implement most Selenium features and tried to simplify it to just WebDriver and Gradle.

I am very much interested in others forking my code and showing me how it can be improved.


# Implemented Features

1. For use only with JUnit 4.11 or higher.
2. For use with Sikuli 1.0.1 or higher to test native elements that WebDriver can't control.
3. Unit tests are parameterized from a csv file.
4. Will log output to build/test-output directory in project.
5. Uses the WebDriver "page object" design pattern.
6. Implemented multiple project build.  The root project has a subproject called "core" and all subprojects of "core" inherit classes from it.
7. Will generate reports of JUnit tests results in build/reports/test/index.html
8. Creates an uberJar of all projects and subprojects


# Configuration Notes

To get it working on a regular Eclipse (Juno), rather than Spring Source Suite, do the following: 
 
1. Download this file to your hard drive: http://dist.springsource.com/release/TOOLS/composite/e3.7/bookmarks.xml 
2. In Eclipse, go to Help-->Install New Software then click the hyperlink to "Available Software Sites" which brings up a Preferences panel. On that panel , choose Import to upload the file that you downloaded in the previous step. 
3. Install the new software component called "Gradle Tooling API" and anything else associated with it. 
4. Create a new "Other" project of the type "Gradle" and choose type of "Java Quickstart".
5. Install EGit into Eclipse (or GitHub GUI outside of Eclipse) and checkout from this read-only repo into your project directory.  EGit might not allow you to checkout into an non-empty directory: if so, you can figure it out.   When I get stuck I sometimes use GitHub GUI to sync my local project repo.  
5. Verify the build.gradle contains the contents matching the build.gradle in this GitHub project. 
6. Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies
7. Right click on your project and choose "Run As-->Gradle Build".  If you see 2 items called "Gradle Build", choose the second one rather than the first one.  In the dialogue choose "clean" and "build" checkboxes.
8. Optionally, you can run this project on the command line with "gradle.bat identify clean build core:show core:clean core:build core:google:show core:google:clean core:google:build --info" and it will execute the unit tests.  Also, this project provides a .bat batch script that does this.


# Screenshot
![Result Screenshot](https://github.com/djangofan/WebDriverTestingTemplate/blob/master/SampleResult.png)

