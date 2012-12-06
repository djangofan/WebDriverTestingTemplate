######<a href="http://github.com/djangofan/WebDriverTestingTemplate/blob/master/README.md">Link: README</a>

# TODO List

0: Convert project to a Gradle multi-project and have the .bat file handle running multiple 
    projects independently.

##Note: Some of these things might not be possible or practical to implement.

1.  Improve unit test logging. Gradle puts results in 'build/reports/test/index.html' but 
    currently this isn't working when launching tests from Eclipse.  It works when you 
	launch from the .bat script I include with this project.
2.  Unit tests read system properties that are set by Gradle runtime.  This feature could be 
    used to force running only from Gradle runtime only.
3.  Improve WebDriver code that handles waiting for html page elements.
4.  Improve the Page Object design pattern so that it is type safe and with better separation
    of script from framework.
5.  Further improve .bat  batch script to launch tests, compile, and perhaps handle Git events.
6.  Add "TODO" comments to existing code to mark where code needs improvements.
7.  Convert usage of XPath in WebDriver to using CSS selectors instead.
8.  Improve test suite so Eclipse provides a stop button during testing.
9. If parameterized data provider can't find input file, default to a non-parameterized test.
10. N/A