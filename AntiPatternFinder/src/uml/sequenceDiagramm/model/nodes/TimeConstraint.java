package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt eine TimeConstraint in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class TimeConstraint extends SequenceDiagrammNode {

	public TimeConstraint(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("TimeConstraint");
	}
	public TimeConstraint(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("TimeConstraint");
	}
}
