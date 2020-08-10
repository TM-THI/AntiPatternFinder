package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt eine InteractionUse in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class InteractionUse extends SequenceDiagrammNode {

	public InteractionUse(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("InteractionUse");
	}
	public InteractionUse(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("InteractionUse");
	}
}
