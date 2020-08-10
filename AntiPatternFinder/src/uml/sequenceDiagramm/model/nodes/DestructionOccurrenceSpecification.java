package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein DestructionOccurrenceSpecification in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class DestructionOccurrenceSpecification extends SequenceDiagrammNode{

	public DestructionOccurrenceSpecification(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("DestructionOccurrenceSpecification");
	}
	public DestructionOccurrenceSpecification(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("DestructionOccurrenceSpecification");
	}
}
