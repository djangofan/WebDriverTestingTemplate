package qa.webdriver.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.common.SeleniumProtocol;
import org.openqa.grid.internal.utils.GridHubConfiguration;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Runs a Selenium Hub Server for handling RemoteWebDriver browser
 * remote control requests.  Uses JSON protocol.
 */
public class GridServer {
	
	Hub myHub = null;
	// info here: http://code.google.com/p/selenium/wiki/DesiredCapabilities
	SelfRegisteringRemote remoteWebDriverNode = null;
	SelfRegisteringRemote remoteRCNode = null; 

	/**
	 * Starts a default new instance of WebDriver Hub JSON server
	 */
	public GridServer () {
		bringUpHubAndNode( "localhost", 4444, "firefox" );		
	}
	
	/**
	 * Starts a defined new instance of WebDriver Hub JSON server
	 */
	public GridServer ( String hostName, int portNumber, String browserType ) {
		bringUpHubAndNode( hostName, portNumber, browserType );		
	}
	
	/**
	 * Starts a defined new instance of WebDriver Hub JSON server
	 */
	public GridServer ( String jsonConfigLoc ) {
		bringUpHubAndNodeFromJSONConfig( jsonConfigLoc );		
	}
	
	/**
	 * This class can be ran as a standalone server directly.
	 * @param none
	 */
	public static void main(String[] args) {
		new GridServer();		
	}
	
	public void bringUpHubAndNode( String hubHost, int hubPort, String browserType ) {
		GridHubConfiguration gridHubConfig = new GridHubConfiguration();
		gridHubConfig.setHost(hubHost);
		gridHubConfig.setPort(hubPort);
		myHub = new Hub( gridHubConfig );
		try {
			myHub.start();
		} catch (Exception e) {
			System.out.println("Error starting hub.");
			e.printStackTrace();
		}
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setBrowserName("*googlechrome");
		try {
			remoteRCNode = attachNodeToHub( chrome, GridRole.NODE, 5555,
					SeleniumProtocol.Selenium );
			remoteWebDriverNode = attachNodeToHub( DesiredCapabilities.firefox(),
					GridRole.NODE, 5556, SeleniumProtocol.WebDriver );
		} catch (Exception e) {
			System.out.println("Error attaching node.");
			e.printStackTrace();
		}		
	}
	
	public void bringUpHubAndNodeFromJSONConfig( String configFile ) {
		GridHubConfiguration gridHubConfig = new GridHubConfiguration();
		gridHubConfig.loadFromJSON( "build/resources/test/WebDriver.json" );
		myHub = new Hub( gridHubConfig );
		try {
			myHub.start();
		} catch (Exception e) {
			System.out.println("Error starting hub.");
			e.printStackTrace();
		}
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setBrowserName("*googlechrome");
		try {
			remoteRCNode = attachNodeToHub( chrome, GridRole.NODE, 5555,
					SeleniumProtocol.Selenium );
			remoteWebDriverNode = attachNodeToHub( DesiredCapabilities.firefox(),
					GridRole.NODE, 5556, SeleniumProtocol.WebDriver );
		} catch (Exception e) {
			System.out.println("Error attaching node.");
			e.printStackTrace();
		}		
	}

	private SelfRegisteringRemote attachNodeToHub( DesiredCapabilities capability, GridRole role,
			               int nodePort, SeleniumProtocol protocol) throws Exception {
		SelfRegisteringRemote node = null;
		RegistrationRequest registrationRequest = new RegistrationRequest();
		capability.setCapability("seleniumProtocol", protocol);
		registrationRequest.addDesiredCapability(capability);
		registrationRequest.setRole(role);
		registrationRequest.setConfiguration(fetchNodeConfiguration(role, nodePort, protocol));
		node = new SelfRegisteringRemote(registrationRequest);
		node.startRemoteServer();
		node.startRegistrationProcess();
		return node;
	}

	private Map<String, Object> fetchNodeConfiguration(GridRole role, int portToRun, 
			               SeleniumProtocol protocol) throws MalformedURLException {
		Map<String, Object> nodeConfiguration = new HashMap<String, Object>();
		nodeConfiguration.put(RegistrationRequest.AUTO_REGISTER, true);
		nodeConfiguration.put(RegistrationRequest.HUB_HOST, myHub.getHost());
		nodeConfiguration.put(RegistrationRequest.HUB_PORT, myHub.getPort());
		nodeConfiguration.put(RegistrationRequest.PORT, portToRun);
		URL remoteURL = new URL("http://" + myHub.getHost() + ":" + portToRun);
		nodeConfiguration.put(RegistrationRequest.PROXY_CLASS,
				"org.openqa.grid.selenium.proxy.DefaultRemoteProxy");
		nodeConfiguration.put(RegistrationRequest.MAX_SESSION, 1);
		nodeConfiguration.put(RegistrationRequest.CLEAN_UP_CYCLE, 2000);
		nodeConfiguration.put(RegistrationRequest.REMOTE_HOST, remoteURL);
		nodeConfiguration.put(RegistrationRequest.MAX_INSTANCES, 1);
		return nodeConfiguration;
	}

	public void shutDownNodeAndHub() throws Exception {
		if (remoteWebDriverNode != null) {
			remoteWebDriverNode.stopRemoteServer();
			System.out.println("WebDriver Node shutdown");
		}
		if (remoteRCNode != null) {
			remoteRCNode.stopRemoteServer();
			System.out.println("RC Node shutdown");
		}
		if ( myHub != null ) {
			myHub.stop();
			System.out.println("Local hub shutdown");
		}
	}
}
