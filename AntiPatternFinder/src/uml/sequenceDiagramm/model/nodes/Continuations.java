package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein Continuations in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class Continuations extends SequenceDiagrammNode{
	
	public Continuations(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("Continuations");
	}
	public Continuations(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("Continuations");
	}
}
