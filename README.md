######<a href="http://github.com/djangofan/WebDriverTestingTemplate/blob/master/TODO.md">Link: TODO List</a>

# Info

This is a Java project that can be used as a template to start a WebDriver web browser testing project.  I chose not to implement most Selenium features and tried to simplify it to just WebDriver and Gradle.

I am very much interested in others forking my code and showing me how it can be improved.

# Project Layout
<br/>
 Root project 'CoreProject'<br/>
 &nbsp;&nbsp;-- Project ':core'<br/>
 &nbsp;&nbsp;&nbsp;&nbsp;---- Project ':core:bing'<br/>
 &nbsp;&nbsp;&nbsp;&nbsp;---- Project ':core:google'<br/>

# Implemented Features

1. For use only with JUnit 4.11 or higher.
2. For use with Sikuli 1.0.1 or higher to test native elements that WebDriver can't control.
3. Unit tests are parameterized from a csv file.
4. Will log output to build/test-output directory in project.
5. Uses the WebDriver "page object" design pattern.
6. Implemented multiple project build.  The root project has a subproject called "core" and all <br/>
   subprojects of "core" inherit classes from it.
7. Will generate reports of JUnit tests results in build/reports/test/index.html
8. Creates an uberJar of all projects and subprojects


# Configuration Notes

#### Eclipse
To get it working on a regular Eclipse (Juno), rather than Spring Source Suite, do the following: 
 
1. Download this file to your hard drive: http://dist.springsource.com/release/TOOLS/composite/e3.7/bookmarks.xml <br/>
2. In Eclipse, go to Help-->Install New Software then click the hyperlink to "Available Software Sites" which <br/>
   brings up a Preferences panel. On that panel , choose Import to upload the file that you downloaded in the <br/>
   previous step. 
3. Install the new software component called "Gradle Tooling API" and anything else associated with it. 
4. Create a new "Other" project of the type "Gradle" and choose type of "Java Quickstart".
5. Verify the build.gradle contains the contents matching the build.gradle in this GitHub project.   If you <br/>
   copy in the contents of the repository .zip file into the workspace directory and replace the existing <br/>
   build.gradle file, that will work also.
6. Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies
7. Right click on your project and choose "Run As-->Gradle Build".  If you see 2 items called "Gradle Build", <br/>
   choose the second one rather than the first one.  In the dialogue choose "clean" and "build" checkboxes <br/>
  (or whatever tasks you want to execute).
8. Optionally, you can run this project on the command line with "gradle identify clean build core:show <br/>
   core:clean core:build core:google:show core:google:clean core:google:build --info" and it will execute <br/>
   the unit tests.  Also, this project provides a .bat batch script that does this.

#### IntelliJ-IDEA
Intellij-IDEA has a nice Gradle plugin that is included.  The steps are otherwise similar to the Eclipse steps<br/>
above.

# Other
1. I use "GitHub GUI" to sync my local project repo to GitHub.  

# Screenshot
![Result Screenshot](https://github.com/djangofan/WebDriverTestingTemplate/blob/master/SampleResult.png)

