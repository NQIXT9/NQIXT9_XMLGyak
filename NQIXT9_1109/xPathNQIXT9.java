package xpathnqixt91109;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class xPathNQIXT9 {

	public static void main(String[] args) {
		try {
			File sourceFile = new File("studentNQIXT9.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document document = dBuilder.parse(sourceFile);
			document.getDocumentElement().normalize();
			//xPath
			XPath xPath = XPathFactory.newInstance().newXPath();
			
			//String expression = "class";
			//String expression = "/class/student";
			
			//String expression = "//student[@id='01']";
			//String expression = "//student";
			//String expression = "/class/student[2]";
			//String expression = "/class/student[kor>20]";
			//String expression = "/class/student[last()]";
			//String expression = "/class/student[last()-1]";
			
			
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
			
			for(int i=0;i<nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				System.out.println("\nAktualis elem: "+node.getNodeName());
				if(node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
					Element element = (Element) node;
					
					System.out.println("Hallgató ID: " + element.getAttribute("id"));
					
					System.out.println("Keresztnev: "+ element.getElementsByTagName("keresztnev").item(0).getTextContent());
					System.out.println("Vezeteknev: "+ element.getElementsByTagName("vezeteknev").item(0).getTextContent());
					System.out.println("Becenev: "+ element.getElementsByTagName("becenev").item(0).getTextContent());
					System.out.println("Kor: "+ element.getElementsByTagName("kor").item(0).getTextContent());
				}
			}
			
			
			
			
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(SAXException sae) {
			sae.printStackTrace();
		} catch (XPathExpressionException xpe) {
			xpe.printStackTrace();
		}
	}
	

}
