package domnqixt91026;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomWriteNQIXT9 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = factory.newDocumentBuilder();
			Document writeOut = dBuilder.newDocument();
			Element rootElement = writeOut.createElementNS("domb9zl3q","users");
			writeOut.appendChild(rootElement);
			
			rootElement.appendChild(createUser(writeOut, "1", "Péter", "Szarka", "szadista"));
			rootElement.appendChild(createUser(writeOut, "2", "Ádám", "Szivós", "mazochista"));
			rootElement.appendChild(createUser(writeOut, "3", "Imre", "Kiss", "burkoló"));

			
			TransformerFactory tranFac = TransformerFactory.newInstance();
			Transformer transf = tranFac.newTransformer();
			
			transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transf.setOutputProperty(OutputKeys.INDENT, "yes");
			transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			DOMSource source = new DOMSource(writeOut);
			
			File newXML = new File("users1NQIXT9.xml");
			
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(newXML);
			
			transf.transform(source, console);
			transf.transform(source, file);
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static Node createUser(Document doc,String id, String fname, String lname, String prof) {
		Element user = doc.createElement("user");
		
		user.setAttribute("id", id);
		user.appendChild(createUserElement(doc, "firstname", fname));
		user.appendChild(createUserElement(doc, "lastname", lname));
		user.appendChild(createUserElement(doc, "profession", prof));
		
		return user;
	}
	
	public static Node createUserElement(Document doc, String n, String i) {
		Element el = doc.createElement(n);
		el.appendChild(doc.createTextNode(i));
		
		return el;
	}

}