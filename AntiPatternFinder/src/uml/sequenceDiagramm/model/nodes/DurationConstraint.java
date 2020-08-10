package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein DurationConstraint in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class DurationConstraint extends SequenceDiagrammNode {

	public DurationConstraint(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("DurationConstraint");
	}
	public DurationConstraint(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("DurationConstraint");
	}
}
