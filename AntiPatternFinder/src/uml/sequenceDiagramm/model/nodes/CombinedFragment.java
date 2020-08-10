package uml.sequenceDiagramm.model.nodes;

/**
 * Klasse stellt ein CombinedFragment in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class CombinedFragment extends SequenceDiagrammNode{
	
	public CombinedFragment(SequenceDiagrammNode parent) {
		super(parent);
		super.setObjectType("CombinedFragment");
	}
	
	public CombinedFragment(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		super(parent, children);
		super.setObjectType("CombinedFragment");
	}

}
