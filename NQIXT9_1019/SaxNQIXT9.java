package SaxNQIXT91019;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.*;

import org.xml.sax.SAXException;

public class SaxNQIXT9 {
	
	public static void main(String[] args) {
		try {
			
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			
			SAXParser saxParser = saxParserFactory.newSAXParser();
			
			SaxHandler handler = new SaxHandler();
			
			saxParser.parse(new File("szemelyekNQIXT9.xml"), handler);
			
		}catch(ParserConfigurationException | SAXExeption | IOExeption e) {
			e.printStackTrace();
		}
	}

}