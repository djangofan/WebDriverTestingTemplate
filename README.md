# Info

This is a Java project that can be used as a template (or archetype) to start a WebDriver web browser testing<br/>
project.  I chose to simplify and and implement using simply WebDriver and Gradle.<br/>

I am very much interested in others forking my code and/or letting me know how it can be improved.

The idea here is to replace your existing automation framework, such as "HP Quality Center", "XStudio", or "Rational<br/>
Functional Tester", with a pure Java solution managed by the Gradle build system.   I use Jenkins to schedule my tests<br/>
and run them.

# Versions

Version 1.0 - March 16, 2013


# Project Layout

    Eclipse Root
    +--- Project ':root'
    +--- Project ':sub-project'
    +--- Project ':commonlib'
    +--- Project ':google'
    +--- Project ':parallelwebdriver'
    +--- ...

# Overview

    1. Project "sub-project" is a project you add yourself, if you want.
    2. Project "google" is a RemoteWebDriver JUnit test-suite using a local Grid server that is capable of running
       multiple threads of web browser tests.
    3. Project "parallelwebdriver" is a test of a  multi-window multi-threaded run using a static local website.
    4. Project "commonlib" is a sub-project containing methods shared between projects.
    5. Project "root" is an 'includeFlat' Gradle-layout-alias to Eclipse project-root-dir 'WebDriverTestingTemplate'.

# Quick Start
Normally, this project would be ran through the Gradle plugin for Eclipse IDE, but I have tried to make it easier by<br/>
including a method to run dynamically and directly from the .zip distribution on the command line.

To try this project without requiring a Java IDE, just make sure you download Gradle 1.4, configure your
GRADLE_HOME<br/> environment variable, add %GRADLE_HOME%\bin to your PATH, and then download the .zip distribution
of this<br/> project, unzip it, and run the included <b>root/runProjectMenu.bat</b> script.

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
    <th>Parallel runner<br/>using JUnit</th>
    <td>A parallel runner using the Gradle maxParallelForks method.</td>
  </tr>
  <tr>
    <th>Native automation support</th>
    <td>For use with Sikuli 1.0.1 or higher to test native elements that WebDriver "Action" is<br/> unable to control. This dependency is configured in the Gradle build script.  If you implement<br/> this however, you may not be able to use the remote webdriver option in<br/> your project. </td>
  </tr>
    <tr>
    <th>Uses RemoteWebDriver<br/>JSON Hub Server</th>
    <td>I have included an implementation of a WebDriverServer class that starts a RemoteWebDriver<br/> JSON Hub server instance in the BeforeClass method of tests. This server is a static member <br/>of the utility class that the tests extend. </td>
  </tr>
  <tr>
    <th>Parameterized data <br/>driven capability</th>
    <td>Unit tests are parameterized from a csv file.  Can also load tests from XML, XLS, a database, etc.</td>
  </tr>
  <tr>
    <th>Logging and Reporting</th>
    <td>Logs test output to console and to a file using SLF4j/LogBack API, and configured by<br/> a <b>logback.xml</b> file. Will generate reports of JUnit test results at<br/> <b>build/reports/test/index.html</b> .  Will place a junit.log file at <b>build/logs/junit.log</b> .</td>
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
    <td>You have three different options for running the tests: via the Gradle GUI, via your IDE<br/>Gradle
    plugin, or via Gradle command line. To run with<br/>the JUnit runner in your IDE, 
    you would need to manually export your project <br/>as a normal Java project, because this template does
    not support that.</td>
  </tr>
  <tr>
    <th>Core utility package</th>
    <td>All projects inherit from a "core" project that contains classes where you can store methods<br/>
        that all of your projects can share between them.</td>
  </tr>
</table>

# Un-implemented Features
<table>
  <tr>
    <th>Feature</th>
    <th>Description</th>
  </tr>
  <tr>
    <th>Gradle Wrapper</th>
    <td>Did not choose to implement the Gradle wrapper because I believe that downloading Gradle and<br/>
       configuring GRADLE_HOME and PATH are easy enough.  Also, a manual setup of Gradle gives<br/>us more
       control using a batch script.  Also, the development IDE is usually configured to use the <br/>
       statically defined Gradle home.</td>
  </tr>
  <tr>
    <th>Jar executable option</th>
    <td>Creates an uberJar of all projects and subprojects that can be ran by double clicking<br/>
       the .jar file.  If you don't have the file association supporting it, we include a <br/>
       jarAssociation.bat file to setup the file association on your Windows system.  I was planning<br/>
       to implement this but currently having trouble getting it to work.</td>
  </tr>
</table>

# Configuration And Setup

#### Eclipse
To get it working on a regular Eclipse 4.2.1 or later, follow these steps:
 
    1. Using the "Eclipse Marketplace" settings panel under the Eclipse "Help" menu, install the Gradle tooling
       functionality.  You can do it through the "Install New Software" menu, but it isn't recommended.
    2. Download the .zip archive of this GitHub project distribution and unzip it to your workspace.  An example
       might be:  "C:\Eclipse32\workspace\WebDriverTestingTemplate\" .
    3. Use the Eclipse "Import" function from the Eclipse "File menu" to import a "Project" of type "Gradle".
    4. Browse using the import wizard to your projects "root" sub-directory described in Step #3 (above).  Then 
      click the "Build model" button.  We do this because of the "includeFlat" Gradle layout.
    5. Check all checkboxes .  You could also choose to add all to your "working set" if you like but it isn't 
       required.
    6. Rebuild the dependencies by right clicking on the project and then choose Gradle-->Refresh All Dependencies
    7. Right click on your project and choose "Run As-->External Tools Configuration".  Configure a new "clean" 
       and "build" configuration for running a sub-project (or whatever tasks you want to execute).
    8. Optionally, you can run this project on the command line with "gradle identify google:show google:clean
       google:build --info" and it will execute the project unit tests.  Also, this project provides a .bat batch
       script that does this and provides a menu of other actions you can execute.

#### IntelliJ-IDEA
The required Gradle functionality is already built into IntelliJ-IDEA 12.1+ .  I think using IDEA is more difficult
but go ahead if you are familiar with it.

#### Notes
Website of this project:<br/>
http://djangofan.github.com/WebDriverTestingTemplate/<br/>
<br/>

# FAQ

    1. If the intellisense in Eclipse doesn't work, make sure you have added all the .class directories to
       your Eclipse project classpath.  (See the included .classpath file.)
    2. I use "GitHub GUI" to sync my local project repo to GitHub.  If you fork my project, I would recommend
       doing it this way unless you are a Git expert and prefer another way.
    3.

