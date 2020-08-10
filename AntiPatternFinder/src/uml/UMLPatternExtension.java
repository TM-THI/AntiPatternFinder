package uml;

import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.antiPattern.NotOperator;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;

/**
 * Klasse stellt die UML-Erweiterung für die Pattern dar. <br>
 * Inklusive der folgenden Funktionen: <br>
 * - {@link Contains} <br>
 * - {@link Dictionary} <br>
 * - {@link WildCardOperator} <br>
 * - {@link NotOperator} <br>
 *
 */
public abstract class UMLPatternExtension {
	
	private boolean isPattern = false;
	
	private Contains contains;
	private Dictionary dictionary;
	private WildCardOperator wildCardOperator;
	private NotOperator notOperator;
	
	public UMLPatternExtension() {
		
	}
	public UMLPatternExtension(boolean isPattern) {
		this.isPattern = isPattern;
	}

	// Getters&Setters
	public Contains getContains() {
		return contains;
	}
	public void setContains(Contains contains) {
		this.contains = contains;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}
	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public WildCardOperator getWildCardOperator() {
		return wildCardOperator;
	}
	public void setWildCardOperator(WildCardOperator wildCardOperator) {
		this.wildCardOperator = wildCardOperator;
	}
	
	public NotOperator getNotOperator() {
		return notOperator;
	}
	public void setNotOperator(NotOperator notOperator) {
		this.notOperator = notOperator;
	}
	
	public boolean isPattern() {
		return isPattern;
	}
	public void setToPattern() {
		this.isPattern = true;
	}

}
