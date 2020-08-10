package uml.SequenceDiagramm;

import uml.UMLObject;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.Message;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

/**
 * Klasse stellt statische Hilfsfunktionen für die Testausführung zur verfügung.
 *
 */
public class SequenceDiagrammTestHelper {
	
	private SequenceDiagrammTestHelper() {
		
	}
	
	private static void printer(int depth, String line) {
		String out = "";
		for(int i=0; i<depth; i++) {
			out = out + "    ";
		}
		System.out.println(out + line);
	}
	
	private static void printMessage(int depth, SequenceDiagrammNode element, SequenceDiagrammPath message) {
		String arrow = "";
		if(message.getBegin().equals(element)) {
			arrow = "  -> ";
		} else {
			arrow = "  <- ";
		}
		printer(depth, arrow + message.getName());
	}
	
	/**
	 * Fuktion printed ein SequenceDiagramm aus.
	 */
	public static void transitAndPrint(UMLObject start, int depth) {
		if(start instanceof SequenceDiagrammNode) {
			var startNode = (SequenceDiagrammNode) start;
			printer(depth, startNode.getPatternName());
			for(var m : startNode.getChildren()) {
				if(m instanceof Message) {
					printMessage(depth, startNode, ((Message) m));
				}
			}
			startNode.getChildren().forEach(c -> transitAndPrint(c, depth+1));
		}
	}
}
