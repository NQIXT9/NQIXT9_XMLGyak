package hu.domparse.nqixt9;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class DOMReadNQIXT9 {

    public static void main(String[] args) {
    	// A DOM létrehozása az XML dokumentumból
    	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File("XMLNQIXT9.xml"));
            document.getDocumentElement().normalize();//normalizálás
            Node root = document.getDocumentElement();//root elem kiválasztása
            listAll(root);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    
    public static void listAll(Node root) {
    	//root elem gyerek elemeinek kiválasztása
    	NodeList nodeList = root.getChildNodes();
    	//kiiratáshoz String-ek
    	String rendezo = "Rendezok\n\n";
    	String szinesz = "Szineszek\n\n";
    	String studio = "Studiok\n\n";
    	String film = "Filmek\n\n";
    	String szereples = "Szereplesek\n\n";
    	for (int i = 0; i < nodeList.getLength(); i++) {
    		Node node = nodeList.item(i);//root gyerek elemei egyessével
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				//root gyerek elemeinek a gyerekelemeinek a kiválasztása
				NodeList subNodeList = nodeList.item(i).getChildNodes();
                for (int j = 0; j < subNodeList.getLength(); j++) {
                	//root gyerek elemeinek a gyerekelemei egyessével
                	Node subNode = subNodeList.item(j);
        			if(subNode.getNodeType() == Node.ELEMENT_NODE) {
        				//Stringekbe csoportosítás
        				rendezo += oneNode(subNode,"rendezo");
        				szinesz += oneNode(subNode,"szinesz");
        				studio += oneNode(subNode,"studio");
        				film += oneNode(subNode,"film");
        				szereples += oneNode(subNode,"szereples");
        			}
                }
			}
    	}
    	//kiiratások
    	System.out.println(rendezo);
    	System.out.println(szinesz);
    	System.out.println(studio);
    	System.out.println(film);
    	System.out.println(szereples);
    }
    
    public static String oneNode(Node subNode, String tag) {
    	String out = "";
    	if(subNode.getNodeName().equals(tag)) {//tag String-gel megegyezõ nevû node-ot keres
    		//ha van attribute-ja akkor kiirja a legelsõt a String-be
			if(subNode.getAttributes().getLength()>0)
			{
				out +=tag + " ID : " + subNode.getAttributes().item(0).getTextContent()+"\n";
			}
			NodeList subSubNodeList = subNode.getChildNodes();
			for (int k = 0; k < subSubNodeList.getLength(); k++) {
	    		Node subSubNode = subSubNodeList.item(k);//node gyerek elemei egyessével
	    		if(subSubNode.getNodeType() == Node.ELEMENT_NODE) {
	    			//az elem nevétõl függõen máshogy rakja bele a String-be
	    			switch(subSubNode.getNodeName()) {
	    			//a refID-kat tartalmazó elemeket olvasása
	    			case "RID":
	    			case "SzID":
	    			case "SID":
	    			case "FID":
	    				//az elsõ attribute-omokat rakja bele a String-be
	    				out +=subSubNode.getNodeName() + " : "
	    			+ subSubNode.getAttributes().item(0).getTextContent()+"\n";
	    			break;
	    			case "megjelenes":
	    			case "lakhely":
	    			case "szekhely":
	    				NodeList sssNode = subSubNode.getChildNodes();
	    				//a gyerekelemein for ciklussal végigmegy és úgy rakja bele a String-be
                        for (int l = 0; l < sssNode.getLength(); l++) {
                            if (sssNode.item(l).getNodeType() == Node.ELEMENT_NODE) {
                            	out += subSubNode.getNodeName() + "-"
                            + sssNode.item(l).getNodeName() + " : " + sssNode.item(l).getTextContent()+"\n";
                            }
                        }
	    			break;
	    			default:
	    				//normál esetben az elem nevet és a kontextusát rakja bele a String-be
	    				out += subSubNode.getNodeName() + " : " + subSubNode.getTextContent()+"\n";
	    			}
	    		}
			}
			out+="\n";
		}
    	return out;
    }

}