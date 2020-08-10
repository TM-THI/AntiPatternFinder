package uml.sequenceDiagramm.model.paths;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt eine FoundMessage in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class FoundMessage extends SequenceDiagrammPath{

	public FoundMessage(SequenceDiagrammNode end) {
		super(null, end);
		super.setObjectType("FoundMessage");
	}

}
