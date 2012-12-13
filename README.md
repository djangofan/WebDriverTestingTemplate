######<a href="http://github.com/djangofan/WebDriverTestingTemplate/blob/master/TODO.md">Link: TODO List</a>

# Info

This is a Java project that can be used as a template to start a WebDriver web browser testing project.  I chose not to implement most Selenium features and tried to simplify it to just WebDriver and Gradle.

I am very much interested in others forking my code and showing me how it can be improved.

The idea here is to replace your existing automation framework, such as "HP Quality Center", "XStudio", or "Rational Functional Tester", with a scripted Gradle/Java solution.

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
 
1. Add the following software repositories to your Eclipse software update panel:<br/>
    a. "SpringSource Update Site for Eclipse Juno 4.2" - http://dist.springsource.com/release/TOOLS/update/e4.2 <br/>
   If you have Eclipse 3.x, then the Sprint 3.7 update site may work for you.  <br/>
   (http://www.springsource.org/STS-installation-instructions )
2. Install the new software components called "Gradle Tooling API" and "Gradle IDE". Restart Eclipse. <br/> 
3. Create a new "Gradle" project of the type "Gradle" and copy in the contents of the repository .zip file <br/>
   into the workspace directory and replace the existing build.gradle file.  <br/>
4. Verify the build.gradle contains the contents matching the build.gradle in this GitHub project.   If you <br/>
   followed step #3, that should have done it. <br/>
5. Right click on the project, choose "Configure" and convert to a Gradle project. <br/>
5. Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies <br/>
6. Right click on your project and choose "Run As-->Gradle Build".  If you see 2 items called "Gradle Build", <br/>
   choose the second one rather than the first one.  In the dialogue choose "clean" and "build" checkboxes <br/>
  (or whatever tasks you want to execute).
7. Optionally, you can run this project on the command line with "gradle identify clean build core:show <br/>
   core:clean core:build core:google:show core:google:clean core:google:build --info" and it will execute <br/>
   the unit tests.  Also, this project provides a .bat batch script that does this.

#### IntelliJ-IDEA
Intellij-IDEA has a nice Gradle plugin that is included.  The steps are otherwise similar to the Eclipse steps<br/>
above.

# Other
1. I use "GitHub GUI" to sync my local project repo to GitHub.  

# Screenshot
![Result Screenshot](https://github.com/djangofan/WebDriverTestingTemplate/blob/master/SampleResult.png)

