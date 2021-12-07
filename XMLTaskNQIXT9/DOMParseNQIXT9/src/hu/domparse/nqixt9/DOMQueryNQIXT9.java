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
            while(exit) {//menu rendszer
            	System.out.println("1 : Film(ek) amik több bevételt hoztak mint...\n"
            			+ "2 : Szinész(ek) akik fiatalabbak...\n3 : Studió(k) amik fiatalabbak mint....");
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
		NodeList nodeList = document.getElementsByTagName("film");//film elemek kiválasztása
		System.out.println("Adja meg mennyinél :");
		Long input = scanner.nextLong();//beolvasás
		System.out.println("\n");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//film gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				NodeList subNodeList = node.getChildNodes();
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("bevetel") )//bevetele elem keresése
						//a kritériumnak megfelelõ elem kiválasztása
						if (Long.parseLong(subNode.getTextContent()) > input)
							listSub(node, "Film");
					}
				}
			}
		}
	}
	
	public static void szineszAge(Document document,Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("szinesz");//szinesz elemek kiválasztása
		System.out.println("Adja meg mennyinél :");
		int input = scanner.nextInt();//beolvasás
		System.out.println("\n");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//szinesz gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				NodeList subNodeList = node.getChildNodes();
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("eletkor") )//eletkor elem megkeresése
						//a kritériumnak megfelelõ elem kiválasztása
						if (Integer.parseInt(subNode.getTextContent()) < input)
							listSub(node, "Szinesz");
					}
				}
			}
		}
	}
	
	public static void studioFoundation(Document document,Scanner scanner) {
		NodeList nodeList = document.getElementsByTagName("studio");//studio elemek kiválasztása
		System.out.println("Adja meg mennyinél :");
		Long input = scanner.nextLong();//beolvasás
		System.out.println("\n");
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);//studio gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				//root gyerek elemeinek a gyerek elemeinek a kiválasztása
				NodeList subNodeList = node.getChildNodes();
				for (int j = 0; j < subNodeList.getLength(); j++) {
				Node subNode = subNodeList.item(j);
				if(subNode.getNodeType() == Node.ELEMENT_NODE) {
					if (subNode.getNodeName().equals("alapitas") )//alapitas elem megkeresése
						//a kritériumnak megfelelõ elem kiválasztása
						if (year - Integer.parseInt(subNode.getTextContent()) < input)
							listSub(node, "Studio");
					}
				}
			}
		}
	}
	//kiiratás
	private static void listSub(Node node, String tag) {
		String out="";
		out +=tag + " ID : " + node.getAttributes().item(0).getTextContent()+"\n";//ID Stringbe irása
			Element element = (Element) node;
				switch(tag) {//külön kezelése a kiirásoknak
				case "Film"://film cim és bevetel String-be irása
					out += element.getElementsByTagName("cim").item(0).getNodeName() 
						+ " : " +element.getElementsByTagName("cim").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("bevetel").item(0).getNodeName()
						+ " : " +element.getElementsByTagName("bevetel").item(0).getTextContent()+"\n";
				break;
				case "Szinesz"://szinesz név és eletkor String-be irása
					out += element.getElementsByTagName("nev").item(0).getNodeName()
						+ " : " +element.getElementsByTagName("nev").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("eletkor").item(0).getNodeName()
						+ " : " +element.getElementsByTagName("eletkor").item(0).getTextContent()+"\n";
				break;
				case "Studio"://studio nev és alapitas String-be irása
					out += element.getElementsByTagName("nev").item(0).getNodeName()
						+ " : " +element.getElementsByTagName("nev").item(0).getTextContent()+"\n";
					out += element.getElementsByTagName("alapitas").item(0).getNodeName()
						+ " : " +element.getElementsByTagName("alapitas").item(0).getTextContent()+"\n";
				break;
			}
		
		System.out.println(out);//kiirás
	}
}
