import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class HTTPListener extends Thread {

	private HashMap<String, WebApplication> webAppMap;

	public HTTPListener(HashMap<String, WebApplication> webAppMap) {
		this.webAppMap = webAppMap;
	}

	public void run() {
		startListener();
	}

	public void startListener() {
		ServerSocket mSock;

		try {

			// The main Webserver runs on fixed port
			mSock = new ServerSocket(8082);

			while (true) {

				// New Connection established
				System.out.println("New HTTP Connection");
				try {

					Socket clientSock = mSock.accept();

					// Delegate the request and then listen for the new
					// connection
					ConnectionHandler newCon = new ConnectionHandler(
							clientSock, webAppMap);
					newCon.start();
				} catch (Exception e) {
					System.out.println("exceptipn");
				}

			}

		}

		catch (Exception e) {
		//	e.printStackTrace();
		}

	}

}
