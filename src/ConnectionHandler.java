import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ConnectionHandler extends Thread {

	private Socket connSock;
	HashMap<String, WebApplication> webAppMap;
	private DataOutputStream outputstream;
	private BufferedReader inputstream;

	public ConnectionHandler(Socket sock,
			HashMap<String, WebApplication> webAppMap) {
		connSock = sock;
		this.webAppMap = webAppMap;

	}

	public void run() {
		try {
			inputstream = new BufferedReader(new InputStreamReader(
					connSock.getInputStream()));
			outputstream = new DataOutputStream(connSock.getOutputStream());

			String inptLine;
			String request = "";
			while ((inptLine = inputstream.readLine()) != null) {
				if (inptLine.isEmpty())
					break;
				request += inptLine + "\r\n";
			}

			String responseStr = "";
			try {
				HttpRequest requestObj = new HttpRequestParser().parse(request);
				String appName = requestObj.getApp();

				if (!webAppMap.containsKey(appName)) {
					if (appName.equals("/")) {
						responseStr = generateServerIndexResp();
					}
				} else {
					responseStr = webAppMap.get(appName).processHttpRequest(
							requestObj);

				}
			} catch (WebApplicationException wae) {
				responseStr = new HttpResponse().createResponse(
						wae.getExceptionMessage(),"text/html", wae.getCode(), "Exception");
			}

			outputstream.writeBytes(responseStr);
			outputstream.flush();
			connSock.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	private String generateServerIndexResp() {

		int readLine;
		String response = "";
		
		try {
			File serverIndex = new File("serverIndex.html");
			InputStream is = new BufferedInputStream(new FileInputStream(serverIndex));

			byte[] buff = new byte[(int) serverIndex.length()];
			StringBuffer fileData = new StringBuffer();
			while ((readLine = is.read(buff)) != -1) {

				fileData.append(new String(buff));
			
			}

			String contentType = URLConnection.guessContentTypeFromStream(is);
			response = new HttpResponse().createResponse(new String(fileData),contentType, 200, "OK");

		} catch (Exception e) {
			//e.printStackTrace();
		}
		return response;
	}
}
