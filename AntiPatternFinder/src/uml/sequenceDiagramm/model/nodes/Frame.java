package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt eine Frame in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class Frame extends SequenceDiagrammNode{
	
	public Frame(String name, SequenceDiagrammNode parent) {
		super(name, parent);
		super.setObjectType("Frame");
	}
	public Frame(String name, SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(name, parent, children);
		super.setObjectType("Frame");
	}
	public Frame(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("Frame");
	}
	public Frame(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("Frame");
	}

}
