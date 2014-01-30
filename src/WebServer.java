import java.util.HashMap;

public class WebServer {

	public static void main(String args[]) {

		// Read the config file
		AppConfigManager confM = new AppConfigManager();

		HashMap<String, ApplicationConfig> AppsMap = confM.getHmUrlApp();

		HashMap<String, WebApplication> webAppMap = new HashMap<String, WebApplication>();

		// creating web application on separate thread
		for (String url : AppsMap.keySet()) {
			WebApplication webApp = new WebApplication(AppsMap.get(url));
			webAppMap.put(url, webApp);
		}
		
		try {
			// Starting the HTTPListener
			HTTPListener httplistn = new HTTPListener(webAppMap);
			httplistn.start();

			// Starting the HTTPSListener (SSL)
			HTTPSListener httpslistn = new HTTPSListener(webAppMap);
			httpslistn.start();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

	}

}
