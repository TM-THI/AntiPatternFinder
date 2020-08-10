package uml.sequenceDiagramm.model.paths;

import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt ein GeneralOrdering in einem SequenceDiagramms dar.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class GeneralOrdering extends SequenceDiagrammPath{

	public GeneralOrdering(SequenceDiagrammNode begin, SequenceDiagrammNode end) {
		super(begin, end);
		super.setObjectType("GeneralOrdering");
	}
}
