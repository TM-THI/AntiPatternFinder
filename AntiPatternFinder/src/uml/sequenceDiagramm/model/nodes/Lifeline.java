package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt eine Lifeline in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class Lifeline extends SequenceDiagrammNode {

	public Lifeline(String name, SequenceDiagrammNode parent) {
		super(name, parent);
		super.setObjectType("Lifeline");
	}
	public Lifeline(String name, SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(name, parent, children);
		super.setObjectType("Lifeline");
	}
	
	
}
