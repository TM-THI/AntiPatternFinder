package uml.sequenceDiagramm.antiPattern;

import java.util.ArrayList;
import java.util.List;

import uml.UMLPatternExtension;

/**
 * Klasse stellt die Dictionary Funktionalität von {@link UMLPatternExtension} dar.
 *
 */
public class Dictionary {
	
	private String name = "";
	private List<String> names = new ArrayList<>();
	
	public Dictionary(String... names) {
		for(var name : names) {
			this.names.add(name);
		}
	}
	
	public Dictionary(String name, String... names) {
		this(names);
		this.name = name;
	}
	
	/**
	 * Funktion überprüft den Namen ob dieser im Dictionary enthalten ist.
	 * @param name Name der überprüft werden soll.
	 * @return True falls der name im Dictionary enthalten, false falls nicht enthalten.
	 */
	public boolean checkName(String name) {
		boolean res = false;
		for(var n : names) {
			res = res || (name.toLowerCase().compareTo(n.toLowerCase()) == 0);
		}
		return res;
	}
	
	
	// Getters&Setters
	public List<String> getNames(){
		return names;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
