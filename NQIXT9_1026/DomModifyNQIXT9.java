package domnqixt91026;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomModifyNQIXT9 {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException  {
		// TODO Auto-generated method stub
		File sourceFile = new File("carsNQIXT9.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(sourceFile);
        doc.getDocumentElement().normalize();


        System.out.println("--------Modified File---------");
        Modify(doc);

	}
	
    public static void ModifyXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult console = new StreamResult(System.out);
        transformer.transform(source, console);
    }
    
    
    public static void Modify(Document doc) throws TransformerException {
    	NodeList nodeList = doc.getElementsByTagName("supercars");
    	
    	for (int i = 0; i < nodeList.getLength(); i++) {
    		Node nNode = nodeList.item(i);
    		Element element = (Element) nNode;

    		if(nNode.getNodeType() == Node.ELEMENT_NODE) {
    			if(element.getAttribute("company").equals("Ferrari")) {
    				
    				Node x = element.getElementsByTagName("carname").item(i);
    				Node y = element.getElementsByTagName("carname").item(1);
    				x.setTextContent("Lamborghini 001");
    				y.setTextContent("Lamborghini 002");
    				
    				element.setAttribute("company", "Lamborghini");

    			}
    		}
			
		}
    	ModifyXML(doc);
    }
}