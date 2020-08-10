package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein Coregion in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class Coregion extends SequenceDiagrammNode{

	public Coregion(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("Coregion");
	}
	public Coregion(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("Coregion");
	}
}
