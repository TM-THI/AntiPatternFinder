package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt eine ExecutionSpecification in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class ExecutionSpecification extends SequenceDiagrammNode {
	
	public ExecutionSpecification(String name, SequenceDiagrammNode parent) {
		super(name, parent);
		super.setObjectType("ExecutionSpecification");
	}
	public ExecutionSpecification(String name, SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(name, parent, children);
		super.setObjectType("ExecutionSpecification");
	}
	public ExecutionSpecification(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("ExecutionSpecification");
	}
	public ExecutionSpecification(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("ExecutionSpecification");
	}
	
	
}
