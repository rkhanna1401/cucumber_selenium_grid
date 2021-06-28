package WebdriverBase;

import java.util.concurrent.TimeUnit;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.selenium.proxy.DefaultRemoteProxy;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.server.SeleniumServer;


public class HubNodeConfiguration {

	static Hub hub;

	public static void configureServer() {

		GridHubConfiguration gridHubConfig = new GridHubConfiguration();
		gridHubConfig.role = "hub";
		gridHubConfig.host = "127.0.0.1";
		gridHubConfig.port = 4444;
		gridHubConfig.newSessionWaitTimeout = 150000;
		Hub myHub = new Hub(gridHubConfig);
		myHub.start();

		GridNodeConfiguration gridNodeConfig = new GridNodeConfiguration();
		gridNodeConfig.hub = "http://127.0.0.1:4444";
		gridNodeConfig.host = "127.0.0.1";
		gridNodeConfig.port = 5555;
		gridNodeConfig.role = GridRole.NODE.toString();
		gridNodeConfig.proxy = DefaultRemoteProxy.class.getCanonicalName();
		RegistrationRequest req = RegistrationRequest.build(gridNodeConfig);
		req.getConfiguration();
		req.validate();
		RegistrationRequest.build(gridNodeConfig);

		SelfRegisteringRemote remote = new SelfRegisteringRemote(req);
		remote.setRemoteServer(new SeleniumServer(gridNodeConfig));
		remote.startRemoteServer();
		remote.startRegistrationProcess();

		hub = getHub("localhost", gridHubConfig.port);

		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Node Registered to Hub..............");
	}

	private static Hub getHub(String host, int port) {
		GridHubConfiguration config = new GridHubConfiguration();
		config.host = host;
		config.port = port;
		return new Hub(config);
	}
	
	public static void tearDownHub() {
		hub.stop();
	}
}
