package uml.sequenceDiagramm.antiPattern;

import uml.UMLPatternExtension;

/**
 * Klasse stellt die Contains Funktionalität von {@link UMLPatternExtension} dar.
 *
 */
public class Contains {
	
	private String subString;
	private Dictionary subDictionary;
	
	public Contains(String subString) {
		this.subString = subString;
	}
	
	public Contains(Dictionary subDictionary) {
		this.subDictionary = subDictionary;
	}
	
	public Contains(String subString, Dictionary subDictionary) {
		this.subString = subString;
		this.subDictionary = subDictionary;
	}
	
	
	/**
	 * Funktion überprüft den Namen ob dieser im Contains enthalten ist.
	 * @param name Name der überprüft werden soll.
	 * @return True falls der name im Contains enthalten, false falls nicht enthalten.
	 */
	public boolean checkName(String name) {
		boolean res = false;
		if(subString != null) {
			res = name.toLowerCase().contains(subString.toLowerCase());
		}
		if(!res && subDictionary != null) {
			for(String s : subDictionary.getNames()) {
				res = res || name.toLowerCase().contains(s.toLowerCase());
			}
		}
		return res;
	}
	
	public boolean containsDictionary() {
		if(this.subDictionary != null) {
			return true;
		}
		return false;
	}
	
	public Dictionary getSubDictionary() {
		return this.subDictionary;
	}
	
	public String getSubString() {
		return this.subString;
	}
}
