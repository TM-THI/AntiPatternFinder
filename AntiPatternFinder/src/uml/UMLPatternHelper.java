package uml;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse enthält statische Hilfsfunktionen für die Verwendung der Pattern.
 *
 */
public class UMLPatternHelper {
	
	private UMLPatternHelper() {
		// kein Konstruktor da nur statische Methoden bereitgestellt werden.
	}
	
	/**
	 * Funktion setzt rekursive alle Kindobjekte des element zu einem pattern 
	 * inklusive des elements selbst.
	 */
	public static void setAllChildrenToPattern(UMLObject element) {
		element.setToPattern();
		if(element instanceof SequenceDiagrammNode) {
			var elementNode = (SequenceDiagrammNode) element;
			for(var e : elementNode.getChildren()) {
				e.setToPattern();
			}
			elementNode.getChildren().forEach(e -> setAllChildrenToPattern(e));
		}
	}

}
