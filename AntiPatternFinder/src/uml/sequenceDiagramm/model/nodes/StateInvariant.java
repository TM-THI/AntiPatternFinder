package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein StateInvariant in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class StateInvariant extends SequenceDiagrammNode {

	public StateInvariant(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("StateInvariant");
	}
	public StateInvariant(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("StateInvariant");
	}
}
