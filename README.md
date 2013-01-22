# Info

This is a Java project that can be used as a template (or archetype) to start a WebDriver web browser testing<br/>
project.  I chose to simplify and not to implement most Selenium "remote" features, therefore implementing a<br/>
project using simply WebDriver and Gradle.<br/>

I am very much interested in others forking my code and/or letting me know how it can be improved.

The idea here is to replace your existing automation framework, such as "HP Quality Center", "XStudio", or "Rational<br/>
Functional Tester", with a pure Java solution managed by the Gradle build system.

# Project Layout
<br/>
 Root project 'WebDriverTestingTemplate'<br/>
 &nbsp;&nbsp;-- Project ':core'<br/>
 &nbsp;&nbsp;&nbsp;&nbsp;---- Project ':core:bing'<br/>
 &nbsp;&nbsp;&nbsp;&nbsp;---- Project ':core:google'<br/>

# Quick Start
To try this project with as little effort as possible, just make sure you download Gradle 1.3 and put it in<br/>
PATH environment and then download the .zip distribution of this project, unzip it, and run the .bat script.

# Implemented Features
<table>
  <tr>
    <th>Feature</th>
    <th>Description</th>
  </tr>
  <tr>
    <th>JUnit based</th>
    <td>For use ONLY with JUnit 4.11 or higher because of the usage of the parameterized capability<br/> of JUnit. This dependency is configured by the Gradle build script.</td>
  </tr>
  <tr>
    <th>Native automation support</th>
    <td>For use with Sikuli 1.0.1 or higher to test native elements that WebDriver "Action" is<br/> unable to control. This dependency is configured in the Gradle build script.  If you implement<br/> this however, you may not be able to use the remote webdriver option in<br/> your project. </td>
  </tr>
  <tr>
    <th>Parameterized data <br/>driven capability</th>
    <td>Unit tests are parameterized from a csv file.  Can also load tests from XML, XLS, a database, etc.</td>
  </tr>
  <tr>
    <th>Logging and Reporting</th>
    <td>Logs test output to console and to a file using SLF4j/LogBack API, and configured by<br/> a <b>logback.xml</b> file. Will generate reports of JUnit tests results at<br/> <b>build/reports/test/index.html</b> .  Will place a junit.log file at <b>build/junit.log</b> .</td>
  </tr>
  <tr>
    <th>Page Object design <br/>pattern</th>
    <td>Uses the WebDriver "page object" design pattern, enhanced by the Selenium<br/> "LoadableComponent" extendable class.</td>
  </tr>
    <tr>
    <th>Fluent API design<br/>pattern</th>
    <td>Implemented examples of the <i>Fluent API</i> design pattern while retaining capability of <br/>
    the traditional page object pattern.</td>
  </tr>
  <tr>
    <th>Multi-project build<br/>configuration</th>
    <td>Implemented multiple project build.  The root project has a subproject called "core" and all <br/>
   subprojects of "core" inherit classes from it.</td>
  </tr>
  <tr>
    <th>Run Options</th>
    <td>You have three different options for running the tests: via the Gradle GUI, via your IDE<br/>Gradle plugin, via executable .jar, or via Gradle command line. To run with<br/>the JUnit runner in your IDE, you would need to manually export your project <br/>as a normal Java project, because this template does not support that.</td>
  </tr>
  <tr>
    <th>Jar executable option</th>
    <td>Creates an uberJar of all projects and subprojects that can be ran by double clicking<br/>
       the .jar file.  If you don't have the file association supporting it, we include a <br/>
       jarAssociation.bat file to setup the file association on your Windows system.</td>
  </tr>
  <tr>
    <th>Core utility package</th>
    <td>All projects inherit from a "core" project that contains classes where you can store methods<br/>
        that all of your projects can share between them.</td>
  </tr>
</table>

# Configuration And Setup

#### Eclipse
To get it working on a regular Eclipse Juno (rather than Spring Source Suite) then perform the following<br/>
steps.  If you deviate from these steps you risk importing the project incorrectly. You know you did it<br/>correctly if your imported project resembles the screenshot I provided called "layout.png".
 
1. Add the following software repositories to your Eclipse software update panel:<br/>
    a. "SpringSource Update Site for Eclipse Juno 4.2" - http://dist.springsource.com/release/TOOLS/update/e4.2 <br/>
   If you have Eclipse 3.x, then the Sprint 3.7 update site may work for you.  <br/>
   (http://www.springsource.org/STS-installation-instructions )
2. Install the new software components called "Gradle Tooling API" and "Gradle IDE". Restart Eclipse. <br/> 
3. Create a new "Java" project called "WebDriverTestingTemplate" and then make a copy of that project directory.  Don't<br/>
   create a Gradle project yet because step#6 converts it properly.<br/>
4. Copy in the contents of the repository .zip file into the root of the project (in your workspace <br/>
   directory) and replace the existing build.gradle file that may have been generated in step #3.  Alternatively, <br/>
   you can use Github for Windows to clone the project from this repo.<br/>
5. Verify the build.gradle contains the contents matching the build.gradle in this GitHub project.   If you <br/>
   followed step #4, that should have done it. <br/>
6. Right click on the project, choose "Configure" and convert to a Gradle project. This step will properly <br/>
   convert the project whereas you might see the Gradle subprojects incorrectly appear beside the main project <br/>
   in the project tree.  We don't use the "Import Gradle project" method because the "Build Model" button will<br/>
   arrange the sub-projects incorrectly in the "Project Explorer" tab.<br/>
7. Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies <br/>
8. Right click on your project and choose "Run As-->Gradle Build".  If you see 2 items called "Gradle Build", <br/>
   choose the second one rather than the first one.  In the dialogue choose "clean" and "build" checkboxes <br/>
  (or whatever tasks you want to execute).
9. Optionally, you can run this project on the command line with "gradle identify clean build core:show <br/>
   core:clean core:build core:google:show core:google:clean core:google:build --info" and it will execute <br/>
   the unit tests.  Also, this project provides a .bat batch script that does this.

#### IntelliJ-IDEA
Intellij-IDEA has a nice Gradle plugin that is included.  The steps are otherwise similar to the Eclipse steps #3-#7 <br/>
above.

#### FAQ
1.  If the intellisense in Eclipse doesn't work, make sure you have added all the .class directories to<br/>
    your Eclipse project classpath.  (See the included .classpath file.)

#### Notes
I use "GitHub GUI" to sync my local project repo to GitHub.  If you fork my project, I would recommend doing <br/>
it this way unless you are a Git expert and prefer another way.<br/>

Some related projects:<br/>
https://github.com/sebarmeli/Selenium2-Java-QuickStart-Archetype<br/>
https://github.com/sebarmeli/Selenium2-Java-Demo<br/>
http://code.google.com/p/selenium-ext/source/browse/<br/>
<br/>
Website of this project:<br/>
http://djangofan.github.com/WebDriverTestingTemplate/<br/>
<br/>
Markdown cheatsheet:<br/>
https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet<br/>
<br/>
# Screenshot
![Result Screenshot](https://github.com/djangofan/WebDriverTestingTemplate/blob/master/SampleResult.png)

