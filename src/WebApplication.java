import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class WebApplication {

	//Load the configuration of web application
	ApplicationConfig config;

	public WebApplication(ApplicationConfig config) {
		this.config = config;

	}

	public ApplicationConfig getConfig() {
		return config;
	}

	public void setConfig(ApplicationConfig config) {
		this.config = config;
	}

	//Process the request and generate the Http response string i.e. Header+body
	public String processHttpRequest(HttpRequest request) {

		String responseStr = null;
		HttpResponse response = new HttpResponse();

		if (request.getHttpMethod().equals("GET") || (request.getHttpMethod().equals("POST"))) {

			// read the root directory location and append it to URI
			String fileName = config.getRootPath() + "\\" + request.getResourceName();
			System.out.println("Filename is  "+fileName);
			try {

				int readLine;
				File resourceFile = new File(fileName);
				InputStream is = new BufferedInputStream(new FileInputStream(resourceFile));

				byte[] buff = new byte[(int) resourceFile.length()];
				StringBuffer fileData = new StringBuffer();

				try {
					
					while ((readLine = is.read(buff)) != -1) {

						fileData.append(new String(buff));
					
					}
			         
					String contectType = URLConnection.guessContentTypeFromStream(is);
					responseStr = response.createResponse(fileData.toString(),contectType, 200, "OK");

				} catch (IOException e) {
					//e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				//Send error response if resource not available
				responseStr = response.createResponse(" <b> Page not available    <br/><br/>","text/html", 404, "File Not Found");
			}

		} else {
			//Handle the Post Request

		}

		return responseStr;
	}

}
