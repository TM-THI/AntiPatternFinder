package uml.sequenceDiagramm;

import java.util.ArrayList;
import java.util.List;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt ein SequenceDiagramm dar.<br>
 * Erweiterung von {@link SequenceDiagrammNode}
 *
 */
public class SequenceDiagramm extends SequenceDiagrammNode {

	private List<SequenceDiagrammNode> nodes;
	
	public SequenceDiagramm() {
		super(null);
		this.nodes = new ArrayList<>();
		super.setObjectType("SequenceDiagramm");
	}
	
	public SequenceDiagramm(SequenceDiagrammNode... nodes) {
		this();
		for(SequenceDiagrammNode child : nodes) {
			this.nodes.add(child);
		}
	}
	
	// Getters&Setters
	public List<SequenceDiagrammNode> getNodes() {
		return nodes;
	}
}
