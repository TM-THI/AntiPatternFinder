package uml.sequenceDiagramm.model.paths;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt eine LostMessage in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class LostMessage extends SequenceDiagrammPath{

	public LostMessage(SequenceDiagrammNode begin) {
		super(begin, null);
		super.setObjectType("LostMessage");
	}
	
}
