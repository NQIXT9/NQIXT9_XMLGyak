package hu.domparse.nqixt9;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DOMQueryNQIXT9 {

	public static void main(String[] args){
		// A DOM létrehozása az XML dokumentumból
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("XMLNQIXT9.xml"));
            document.getDocumentElement().normalize();//normalizálás
            Scanner scanner = new Scanner(System.in);
            boolean exit=true;
            while(exit) {
            	System.out.println("1 : Film(ek) amik több bevételt hoztak mint...\n2 : Szinész(ek) akik fiatalabbak...\n3 : Studió(k) amik fiatalabbak mint....");
            	switch(scanner.nextInt()) {
            	case 1 : filmIncome(document, scanner);break;
            	case 2 : szineszAge(document, scanner);break;
            	case 3 : studioFoundation(document, scanner);break;
            	default : exit=false;
            	}
            }
            scanner.close();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
	}
	
	public static void filmIncome(Document document,Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("film");
		System.out.println("Adja meg mennyinél :");
		Long input = scanner.nextLong();
		System.out.println("\n");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//film gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList subNodeList = node.getChildNodes();//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("bevetel") )
						if (Long.parseLong(subNode.getTextContent()) > input)
							listSub(node, "Film");
					}
				}
			}
		}
	}
	
	public static void szineszAge(Document document,Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("szinesz");
		System.out.println("Adja meg mennyinél :");
		int input = scanner.nextInt();
		System.out.println("\n");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//film gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList subNodeList = node.getChildNodes();//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("eletkor") )
						if (Integer.parseInt(subNode.getTextContent()) < input)
							listSub(node, "Szinesz");
					}
				}
			}
		}
	}
	
	public static void studioFoundation(Document document,Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("studio");
		System.out.println("Adja meg mennyinél :");
		Long input = scanner.nextLong();
		System.out.println("\n");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//film gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				NodeList subNodeList = node.getChildNodes();//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("alapitas") )
						if (year - Integer.parseInt(subNode.getTextContent()) < input)
							listSub(node, "Studio");
					}
				}
			}
		}
	}
	
	private static void listSub(Node node, String tag) {
		String out="";
		out +=tag + " ID : " + node.getAttributes().item(0).getTextContent()+"\n";
			//NodeList nodeList = node.getChildNodes();
			Element element = (Element) node;
				switch(tag) {
				case "Film":
					//out += nodeList.item(1).getNodeName() + " : " + nodeList.item(1).getTextContent()+"\n"; \\elsõ próbálkozás whitespace miatt 0 helyett 1 kell
					out += element.getElementsByTagName("cim").item(0).getNodeName() + " : " +element.getElementsByTagName("cim").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("bevetel").item(0).getNodeName() + " : " +element.getElementsByTagName("bevetel").item(0).getTextContent()+"\n";
				break;
				case "Szinesz":
					//out += nodeList.item(1).getNodeName() + " : " + nodeList.item(1).getTextContent()+"\n"; \\elsõ próbálkozás whitespace miatt 0 helyett 1 kell
					out += element.getElementsByTagName("nev").item(0).getNodeName() + " : " +element.getElementsByTagName("nev").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("eletkor").item(0).getNodeName() + " : " +element.getElementsByTagName("eletkor").item(0).getTextContent()+"\n";
				break;
				case "Studio":
					//out += nodeList.item(1).getNodeName() + " : " + nodeList.item(1).getTextContent()+"\n"; \\elsõ próbálkozás whitespace miatt 0 helyett 1 kell
					out += element.getElementsByTagName("nev").item(0).getNodeName() + " : " +element.getElementsByTagName("nev").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("alapitas").item(0).getNodeName() + " : " +element.getElementsByTagName("alapitas").item(0).getTextContent()+"\n";
				break;
			}
		
		System.out.println(out);
	}
}
