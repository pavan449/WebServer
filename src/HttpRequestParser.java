import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequestParser {

	public HttpRequest parse(String requestStr) throws WebApplicationException {

		
		validateRequest(requestStr);
		System.out.println("resuest Str    :" + requestStr);
		HttpRequest httpRequest = new HttpRequest();

		String htpMethod = requestStr.substring(0, requestStr.indexOf(" "));

		String uri = requestStr.substring(4, (requestStr.indexOf("HTTP") - 1));
		int firstIndexUrl = requestStr.indexOf("Host:") + 6;
		int lastIndexUrl = requestStr.lastIndexOf("\n");
		String serverName = requestStr.substring(firstIndexUrl, lastIndexUrl);

		// /WebApplication/index.txt
		httpRequest.setHttpMethod(htpMethod);

		httpRequest.setServerName(serverName);
		httpRequest.setResourceName(getResource(uri));
		httpRequest.setAppName(getApp(uri));
		return httpRequest;
		
		
	}

	public String getApp(String url) {

		url = url.trim();
		String appname = "/";

		if (!url.equals("/")) {

			StringTokenizer tok = new StringTokenizer(url, "/", true);
			tok.nextToken();
			appname = tok.nextToken();

		}
		return appname.trim();

	}

	public String getResource(String url) {

		String resourceName = "index.txt";
		if (!url.equals("/")) {
			resourceName = url.substring(url.lastIndexOf("/") + 1);
		}
		return resourceName.trim();

	}

	private void validateRequest(String request) {

		// Create a string reader
		Scanner requestScan = new Scanner(request);

		if (!requestScan.hasNextLine()) {
			throw new WebApplicationException(501, "Not Implemented",
					"The HTTP request is not complete.");
		}

		// First line must be the method, request URI, and protocol version
		Pattern p = Pattern.compile("^(.*) (.*) HTTP/([0-9])\\.([0-9])$");
		Matcher m = p.matcher(requestScan.nextLine());
		if (!m.matches()) {
			throw new WebApplicationException(400, "Bad Request",
					"The HTTP header provided is incomplete.");
		}
		String httpMethod = request.substring(0,request.indexOf(" "));
		if( !( (httpMethod.equals("GET") ) || (httpMethod.equals("POST") ) )){
			throw new WebApplicationException(501, "Method Not Implemented",
					"Method not implemented ");
		}
		
	}

}
