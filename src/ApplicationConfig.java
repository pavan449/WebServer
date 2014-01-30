public class ApplicationConfig {

	private String rootPath;
	private int port;
	private String host;
	private String indexhandler;
	private String webAppName;
	
	
	public String getWebAppName() {
		return webAppName;
	}
	public void setWebAppName(String webAppName) {
		this.webAppName = webAppName;
	}
	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getIndexhandler() {
		return indexhandler;
	}
	public void setIndexhandler(String indexhandler) {
		this.indexhandler = indexhandler;
	}


	
}
