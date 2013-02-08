package what.ismyip;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class WhatIsMyIP 
{
    public static void main( String[] args ) {
    	
    	// An example of using HtmlUnitDriver
    	String msg = System.getProperty("simple.message") + "\n";
    	msg = msg + "My IP Address Is" + "\n";
        HtmlUnitDriver driver = new HtmlUnitDriver(true);
        driver.get("http://checkip.dyndns.com/");        
        String ipstring = driver.findElement( By.cssSelector("html body") ).getText();        
        msg = msg + ipstring.substring( ipstring.indexOf(": ") + 2) + "\n";
        
        System.out.println( msg );
        FileWriter outFile = null;
		try {
			outFile = new FileWriter("ip.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
        PrintWriter out = new PrintWriter(outFile);
        out.write(msg);
        out.close();
        try {
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}
