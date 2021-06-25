package WebdriverBase;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.grid.common.GridRole;
import org.openqa.grid.common.RegistrationRequest;
import org.openqa.grid.internal.utils.SelfRegisteringRemote;
import org.openqa.grid.internal.utils.configuration.GridHubConfiguration;
import org.openqa.grid.internal.utils.configuration.GridNodeConfiguration;
import org.openqa.grid.shared.GridNodeServer;
import org.openqa.grid.web.Hub;
import org.openqa.selenium.remote.server.SeleniumServer;





public class HubNodeConfiguration {

	public static void configureServer() {

		GridHubConfiguration gridHubConfig = new GridHubConfiguration();
		//gridHubConfig.role = "hub";
		gridHubConfig.host = "127.0.0.1";
		gridHubConfig.port = 4444;
		gridHubConfig.newSessionWaitTimeout = 150000;
		Hub myHub = new Hub(gridHubConfig);
		myHub.start();

		GridNodeConfiguration gridNodeConfig = new GridNodeConfiguration();
		gridNodeConfig.hub = "http://127.0.0.1:4444/grid/register";
		gridNodeConfig.host = "xxxx"; //my ip address
		gridNodeConfig.port = 5566;
		gridNodeConfig.role = "webdriver";
		RegistrationRequest req = RegistrationRequest.build(gridNodeConfig);
		req.getConfiguration();
		req.validate();
		RegistrationRequest.build(gridNodeConfig);

		SelfRegisteringRemote remote = new SelfRegisteringRemote(req);
		remote.setRemoteServer(new SeleniumServer(gridNodeConfig));
		remote.startRemoteServer();
		remote.startRegistrationProcess();

		System.out.println("Node Registered to Hub..............");
	}
}
