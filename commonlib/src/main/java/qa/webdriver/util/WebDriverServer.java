package qa.webdriver.util;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.webapp.WebAppContext;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.remote.server.DriverServlet;

public class WebDriverServer {
	
	// this is all a mess.  i cant figure out how to get this to work
	
  private Server server = new Server();

  public WebDriverServer() {
	  
    WebAppContext context = new WebAppContext();
    context.setContextPath("");    
    context.setWar( new File(".").getAbsolutePath() );
    server.setHandler( context );
    try {
		WebAppContext.addWebApplications(server, "org.mortbay.jetty.servlet.DefaultServlet", "/", true, true);
	} catch (IOException e) {
		e.printStackTrace();
	}
    //context.addServlet( DriverServlet.class, "/wd/*");
    //SelectChannelConnector connector = new SelectChannelConnector();
    //connector.setPort(4444);
    //server.(connector);
    
    try {
		server.start();
	} catch (Exception e) {
		e.printStackTrace();
	}  //requires servlet-api 3+ ???
  

  }
  
}
