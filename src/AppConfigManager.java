import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class AppConfigManager {

	private HashMap<String, ApplicationConfig> hmUrlApp = new HashMap<String, ApplicationConfig>();

	public AppConfigManager() {
		
		File resources = new File("resources/appconfig/");

		for (File f : resources.listFiles()) {
			fileReader("resources/appconfig/"+f.getName());
		}
		
	}
	
	public HashMap<String, ApplicationConfig> getHmUrlApp() {
		return hmUrlApp;
	}



	public void setHmUrlApp(HashMap<String, ApplicationConfig> hmUrlApp) {
		this.hmUrlApp = hmUrlApp;
	}



	public void  fileReader(String fileName) {

		
		
		ApplicationConfig appConfig = new ApplicationConfig();
		try {
			
			File fXmlFile = new File(fileName);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();


			NodeList nList = doc.getElementsByTagName("site");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					appConfig.setRootPath(eElement
							.getElementsByTagName("rootPath").item(0)
							.getTextContent());

					appConfig.setPort(Integer.parseInt(eElement
							.getElementsByTagName("port").item(0)
							.getTextContent()));
					appConfig.setHost(eElement.getElementsByTagName("host")
							.item(0).getTextContent());
					appConfig.setIndexhandler(eElement
							.getElementsByTagName("indexHandler").item(0)
							.getTextContent());
					
					appConfig.setWebAppName( eElement
							.getElementsByTagName("applicationName").item(0)
							.getTextContent() );
					
					hmUrlApp.put(appConfig.getWebAppName().trim(), appConfig);

				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		
	}



	}
