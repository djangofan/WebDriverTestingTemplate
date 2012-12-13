######<a href="http://github.com/djangofan/WebDriverTestingTemplate/blob/master/README.md">Link: README</a>

# TODO List

####Note: Some of these things might not be possible or practical to implement.

1.  Improve unit test logging. Gradle puts results in 'build/reports/test/index.html' but 
    currently this isn't working when launching tests from Eclipse.  It only works when you 
	  launch from the .bat script I include with this project.
	  
2.  Unit tests read system properties that are set by Gradle runtime.  This feature could be 
    used to force running only from Gradle runtime only rather than from Eclipse, etc.
    
3.  Improve WebDriver code that handles waiting for html page elements.

4.  Improve the Page Object design pattern so that it is type safe and with better separation
    of script from framework.  Perhaps use a Type-Token design pattern: http://thegreenoak.blogspot.com/2012/11/a-type-token-page-object-design-pattern.html
    
5.  Convert usage of XPath in WebDriver to using CSS selectors instead.

6.  Improve test suite so Eclipse provides a stop button during testing.

7.  If parameterized data provider can't find input file, default to a non-parameterized test.

8.  Improve allJar method to also include test classes and have a Manifest with a Main-Class option.
