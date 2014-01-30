import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.HashMap;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class HTTPSListener extends Thread {

	private HashMap<String, WebApplication> webAppMap;

	public HTTPSListener(HashMap<String, WebApplication> webAppMap) {
		this.webAppMap = webAppMap;
	}

	public void run() {

		startListener();

	}

	public void startListener() {

		try {
		
				String jksFile = "Key/plainserver.jks";
				char clienttPass[] = "password".toCharArray();
				//keys get stored in java keystore which is password protected
				char keyStorePass[] = "password".toCharArray();
				KeyStore keyst = KeyStore.getInstance("JKS");
				keyst.load(new FileInputStream(jksFile), keyStorePass);
				KeyManagerFactory keyMngFact = KeyManagerFactory.getInstance("SunX509");
				keyMngFact.init(keyst, clienttPass);
				SSLContext sslcon = SSLContext.getInstance("TLS");
				sslcon.init(keyMngFact.getKeyManagers(), null, null);
				SSLServerSocketFactory sslfact = sslcon.getServerSocketFactory();
				SSLServerSocket mSock = (SSLServerSocket) sslfact
						.createServerSocket(8083);
				System.out.println("Server started:");

			while (true) {

				try {
					SSLSocket clientSock = (SSLSocket) mSock.accept();
					 ConnectionHandler newCon = new ConnectionHandler(clientSock,webAppMap);
					 newCon.run();
					 
				} catch (Exception e) {
					System.out.println("Hi");
					//e.printStackTrace();

				}

			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
