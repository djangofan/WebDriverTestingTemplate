package qa.webdriver.util.grid;

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
	SelfRegisteringRemote remoteFirefoxNode = null;
	SelfRegisteringRemote remoteChromeNode = null; 

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
		gridHubConfig.setHost( hubHost );
		gridHubConfig.setPort( hubPort );
		myHub = new Hub( gridHubConfig );
		try {
			myHub.start();
		} catch (Exception e) {
			System.out.println("Error starting hub on port " + gridHubConfig.getPort() );
			e.printStackTrace();
		}
		if ( browserType.equalsIgnoreCase("firefox") ) {
			int ffPort = 5555;
			System.out.println( "Connecting Firefox node on port " + ffPort + " to hub on port " + hubPort );
			try {
				remoteFirefoxNode = attachNodeToHub( DesiredCapabilities.firefox(),	GridRole.NODE, ffPort, SeleniumProtocol.WebDriver );
				System.out.println("maxInstances is " + remoteFirefoxNode.getConfiguration().get("maxInstances") );
			} catch (Exception e) {
				System.out.println("Error attaching Firefox node.");
				e.printStackTrace();
			}
		} else if ( browserType.equalsIgnoreCase( "chrome" ) ) {
			int cPort = 5556;
			System.out.println( "Connecting Chrome node to hub on port " + cPort + " to hub on port " + hubPort );
			DesiredCapabilities chrome = DesiredCapabilities.chrome();
			chrome.setBrowserName("*googlechrome");
			try {
				remoteChromeNode = attachNodeToHub( chrome, GridRole.NODE, cPort, SeleniumProtocol.Selenium );				
			} catch (Exception e) {
				System.out.println("Error attaching Chrome node.");
				e.printStackTrace();
			}
        }
	}

	public void bringUpHubAndNodeFromJSONConfig( String configFile ) {
		GridHubConfiguration gridHubConfig = new GridHubConfiguration();
		gridHubConfig.loadFromJSON( "build/resources/test/WebDriver.json" );
		myHub = new Hub( gridHubConfig );
		try {
			myHub.start();
		} catch ( Exception e ) {
			System.out.println("Error starting hub.");
			e.printStackTrace();
		}
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setBrowserName("*googlechrome");
		try {
			//TODO I am not sure why we need to start 2 separate nodes here
			// for each browser type.  This isn't my original code.
			remoteFirefoxNode = attachNodeToHub( DesiredCapabilities.firefox(),	GridRole.NODE, 5555, SeleniumProtocol.WebDriver );
			remoteChromeNode = attachNodeToHub( chrome, GridRole.NODE, 5556, SeleniumProtocol.Selenium );
		} catch (Exception e) {
			System.out.println("Error attaching node.");
			e.printStackTrace();
		}
	}

	/* Call multiple times to register more than one Node
	 * 
	 */
	private SelfRegisteringRemote attachNodeToHub( DesiredCapabilities capability, GridRole role,
			int nodePort, SeleniumProtocol protocol) throws Exception {
		SelfRegisteringRemote node = null;
		RegistrationRequest registrationRequest = new RegistrationRequest();
		capability.setCapability( "seleniumProtocol", protocol );
		registrationRequest.addDesiredCapability( capability );
		registrationRequest.setRole(role);
		registrationRequest.setConfiguration( fetchNodeConfiguration(role, nodePort, protocol) );
		node = new SelfRegisteringRemote( registrationRequest );
		node.startRemoteServer();
		node.startRegistrationProcess();
		return node;
	}

	private Map<String, Object> fetchNodeConfiguration(GridRole role, int portToRun, SeleniumProtocol protocol) 
			throws MalformedURLException {
		Map<String, Object> nodeConfiguration = new HashMap<String, Object>();
		nodeConfiguration.put( RegistrationRequest.AUTO_REGISTER, true );
		nodeConfiguration.put( RegistrationRequest.HUB_HOST, myHub.getHost() );
		nodeConfiguration.put( RegistrationRequest.HUB_PORT, myHub.getPort() );
		nodeConfiguration.put( RegistrationRequest.PORT, portToRun);
		URL remoteURL = new URL("http://" + myHub.getHost() + ":" + portToRun );
		nodeConfiguration.put( RegistrationRequest.PROXY_CLASS,	"org.openqa.grid.selenium.proxy.DefaultRemoteProxy" );
		nodeConfiguration.put( RegistrationRequest.MAX_SESSION, 5 );
		nodeConfiguration.put( RegistrationRequest.CLEAN_UP_CYCLE, 2000 );
		nodeConfiguration.put( RegistrationRequest.REMOTE_HOST, remoteURL );
		nodeConfiguration.put( RegistrationRequest.MAX_INSTANCES, 5 );
		return nodeConfiguration;
	}

	public void shutDownNodeAndHub() {
		if (remoteFirefoxNode != null) {
			remoteFirefoxNode.stopRemoteServer();
			System.out.println("Firefox Node shutdown");
		}
		if (remoteChromeNode != null) {
			remoteChromeNode.stopRemoteServer();
			System.out.println("Chrome Node shutdown");
		}
		if ( myHub != null ) {
			try {
				myHub.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Local hub shutdown");
		}
	}

}
