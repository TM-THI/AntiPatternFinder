package uml.sequenceDiagramm.model.paths;

import uml.UMLObject;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt ein Pathobjekt in einem SequenceDiagramm dar.<br>
 * Erweiterung von {@link UMLObject}
 *
 */
public abstract class SequenceDiagrammPath extends UMLObject{
	
	private SequenceDiagrammNode begin;
	private SequenceDiagrammNode end;
	
	private String sendEvent;
	private String receiveEvent;
	
	
	public SequenceDiagrammPath(String name, SequenceDiagrammNode begin, SequenceDiagrammNode end) {
		this(begin, end);
		super.setName(name);
	}
	
	public SequenceDiagrammPath(SequenceDiagrammNode begin, SequenceDiagrammNode end) {
		this.begin = begin;
		this.end = end;
		if(begin != null) {
			begin.addMessage(this);
		}
		if(end != null) {
			end.addMessage(this);
		}

	}

	/**
	 * Funktion ändert den Parent des aktuellen Objekts zu dem parent
	 * @param parent Parent der das neue Parent des Pfades darstellen soll.
	 */
	public void changeParent(SequenceDiagrammNode parent) {
		for(var child : parent.getChildren()) {
			if(child.equals(this)) {
				// Nachricht bereits enthalten
				return;
			}
		}
		if(begin.equals(parent)) {
			this.begin = parent;
			begin.addMessage(this);
		} else if (end.equals(parent)) {
			this.end = parent;
			end.addMessage(this);
		}
	}
	
	// Getters&Setters
	public SequenceDiagrammNode getBegin() {
		return begin;
	}
	public void setBeginn(SequenceDiagrammNode begin) {
		this.begin = begin;
		begin.addMessage(this);
	}
	
	public SequenceDiagrammNode getEnd() {
		return end;
	}
	public void setEnd(SequenceDiagrammNode end) {
		this.end = end;
		end.addMessage(this);
	}

	public String getSendEvent() {
		return sendEvent;
	}
	public void setSendEvent(String sendEvent) {
		this.sendEvent = sendEvent;
	}

	public String getReceiveEvent() {
		return receiveEvent;
	}
	public void setReceiveEvent(String receiveEvent) {
		this.receiveEvent = receiveEvent;
	}
	
	
}
