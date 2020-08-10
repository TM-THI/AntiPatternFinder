package uml.sequenceDiagramm.model.paths;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt eine Message in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class Message extends SequenceDiagrammPath {
	
	public Message(SequenceDiagrammNode begin, SequenceDiagrammNode end) {
		super(begin, end);
		super.setObjectType("Message");
	}
	
	public Message(String name, SequenceDiagrammNode begin, SequenceDiagrammNode end) {
		super(name, begin, end);
		super.setObjectType("Message");
	}
	
}
