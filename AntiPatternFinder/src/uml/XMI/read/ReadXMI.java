package uml.XMI.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import uml.UMLObject;

/**
 * Klasse stellt statische Fuktionen zum einlesen von XMI-Dateien (.uml) bereit.
 *
 */
public class ReadXMI {
	
	private ReadXMI() {
		// kein Konstruktor da nur statische Methoden bereitgestellt werden.
	}
	
	/**
	 * Funktion liest die XMI-Datei am Speicherort path ein und gibt eine List mit enthaltenen {@link UMLObject} zurück.
	 */
	public static List<UMLObject> readFile(String path) {
		Document document;
		try {
			document = initDocument(path);
			List<UMLObject> res = transform(document);
			
			return res;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	/**
	 * Initialisiert ein neues {@link Document} an dem Pfad path ein.
	 */
	private static Document initDocument(String path) throws ParserConfigurationException, SAXException, IOException {
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder = factory.newDocumentBuilder();
		 Document document = builder.parse(new File(path));
		 return document;
	}
	
	/**
	 * Funktion transformiert ein {@link Document} zu einer Liste von enthaltenen {@link UMLObject}
	 */
	private static List<UMLObject> transform(Document document) {
		List<UMLObject> resList = new ArrayList<>();
		NodeList nodeList = document.getChildNodes();
		
		for(int i=0; i < nodeList.getLength(); i++) {
			if(nodeList.item(i).getNodeName().compareTo("uml:Model") == 0) {
				ReadXMIHelper helper = new ReadXMIHelper();
				resList.addAll(helper.generateModel(nodeList.item(i)));
			}
		}
		
		return resList;
	}
}
