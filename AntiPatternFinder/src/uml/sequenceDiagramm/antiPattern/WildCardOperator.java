package uml.sequenceDiagramm.antiPattern;

import uml.UMLPatternExtension;

/**
 * Klasse stellt die WildCard Funktionalität von {@link UMLPatternExtension} dar.
 *
 */
public class WildCardOperator {
	
	public WildCardOperator() {
		
	}
	
	
	/**
	 * Funktion überprüft den Namen ob dieser durch die WildCardFunktion abgedeckt ist.
	 * @param name Name der überprüft werden soll.
	 * @return Immer true, da jeder Name durch WildCard Funktion enthalten ist.
	 */
	public boolean checkName(String name) {
		return true;
	}
	
}
