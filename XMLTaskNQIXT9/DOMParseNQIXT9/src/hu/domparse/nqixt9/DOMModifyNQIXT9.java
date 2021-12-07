package hu.domparse.nqixt9;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMModifyNQIXT9 {

	public static void main(String[] args){
		// A DOM létrehozása az XML dokumentumból
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("XMLNQIXT9.xml"));
            document.getDocumentElement().normalize();//normalizálás
            Scanner scanner = new Scanner(System.in);
            boolean exit=true;
            while(exit) {//menu rendszer
            	System.out.println("1 : Adja meg egy film bevetelet\n2 : Adja meg egy szinész életkorát\n"
            			+ "3 : Adja meg egy studio új vezetőjének a nevét");
            	switch(scanner.nextInt()) {
            	case 1 : filmIncome(document, scanner);break;
            	case 2 : szineszAge(document, scanner);break;
            	case 3 : studioBoss(document, scanner);break;
            	default : exit=false;
            	}
            }
            scanner.close();
            writeToXml(document);
        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
	}
	
	public static void filmIncome(Document document, Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("film");//film elemek kiválasztása
		System.out.println("Adja meg a film ID-ját :");
		String input = scanner.next();//beolvasás
		for	(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				//a megfelelő ID megkeresése
				if (node.getAttributes().getNamedItem("FID").getTextContent().equals(input)) {
					NodeList subNodeList = node.getChildNodes();
					for (int j = 0; j < subNodeList.getLength(); j++) {
						Node subNode = subNodeList.item(j);
						if (subNode.getNodeName().equals("bevetel")) {//bevetel elem keresése
							System.out.println("Adja meg a film bevételét :");
							//új bevetel bekérése és beírása az xml-be
							subNode.setTextContent(scanner.next());
						}
					}
				}
			}
		}
	}
	
	public static void szineszAge(Document document, Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("szinesz");//szinesz elemek kiválasztása
		System.out.println("Adja meg a szinesz ID-ját :");
		String input = scanner.next();//beolvasás
		for	(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				//a megfelelő ID megkeresése
				if (node.getAttributes().getNamedItem("SzID").getTextContent().equals(input)) {
					NodeList subNodeList = node.getChildNodes();
					for (int j = 0; j < subNodeList.getLength(); j++) {
						Node subNode = subNodeList.item(j);
						if (subNode.getNodeName().equals("eletkor")) {//eletkor elem keresése
							//új kor bekérése és beírása az xml-be
							System.out.println("Adja meg a szinesz új életkorát :");
							subNode.setTextContent(scanner.next());
						}
					}
				}
			}
		}
	}
	
	public static void studioBoss(Document document, Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("studio");//studio elemek kiválasztása
		System.out.println("Adja meg a studio ID-ját :");
		String input = scanner.next();//beolvasás
		for	(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				//a megfelelő ID megkeresése
				if (node.getAttributes().getNamedItem("SID").getTextContent().equals(input)) {
					NodeList subNodeList = node.getChildNodes();
					for (int j = 0; j < subNodeList.getLength(); j++) {
						Node subNode = subNodeList.item(j);
						if (subNode.getNodeName().equals("vezeto")) {//vezeto elem kiválasztása
							System.out.println("Adja meg a vezeto új nevét :");
							//új név bekérése és kiirása az xml-be
							String name = scanner.next();
							name +=scanner.nextLine();
							subNode.setTextContent(name);
						}
					}
				}
			}
		}
	}
	//fájlba és konzolra kiirás
	public static void writeToXml(Document document) throws TransformerException, UnsupportedEncodingException {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		//kimeneti csatorna konzolra
		StreamResult console = new StreamResult(System.out);
		//kimeneti csatorna fájlba
		StreamResult file = new StreamResult(new File("XMLNQIXT9.out.xml"));
		// A módosítások kiírása a képernyőre
		transf.transform(source, console);
		// A módosítások kiírása új fájlba
		transf.transform(source, file);
	}
	
}