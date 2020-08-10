package uml.sequenceDiagramm.model.nodes;

import java.util.ArrayList;
import java.util.List;

import uml.UMLObject;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

/**
 * Klasse stellt ein Knotenobjekt in einem SequenceDiagramm dar.<br>
 * Erweiterung von {@link UMLObject}
 *
 */
public abstract class SequenceDiagrammNode extends UMLObject {
	
	private String begin = "";
	private String finish = "";
	private SequenceDiagrammNode parent;
	private List<UMLObject> children = new ArrayList<>();
	private String covered = "";
	
	public SequenceDiagrammNode(String name, SequenceDiagrammNode parent) {
		this(parent);
		super.setName(name);
	}
	public SequenceDiagrammNode(String name, SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		this(parent, children);
		super.setName(name);
	}
	
	public SequenceDiagrammNode(SequenceDiagrammNode parent) {
		this.parent = parent;
		if(parent != null) {
			parent.getChildren().add(this);
		}
	}
	
	public SequenceDiagrammNode(SequenceDiagrammNode parent, SequenceDiagrammNode... children) {
		this.parent = parent;
		for(SequenceDiagrammNode child : children) {
			this.children.add(child);
		}
		parent.getChildren().add(this);
	}
	
	
	/**
	 * Funktion fügt ein Kindknoten children zu den Kindobjekten des aktuellen Objekts hinzu.
	 */
	public void addChild(SequenceDiagrammNode children) {
		this.children.add(children);
	}
	
	/**
	 * Funktion fügt eine Nachricht message zu den Kindobjekten des aktuellen Objekts hinzu.
	 * @param message
	 */
	public void addMessage(SequenceDiagrammPath message) {
		this.children.add(message);
	}
	
	/**
	 * Funktion ändert den Parent des aktuellen Objekts zu dem parent
	 * @param parent Parent der das neue Parent des Knotens darstellen soll.
	 */
	public void changeParent(SequenceDiagrammNode parent) {
		boolean finding = false;
		for(var child : parent.getChildren()) {
			if(child.equals(this)) {
				this.getChildren().forEach(c -> {
					if(c instanceof SequenceDiagrammNode) {
						((SequenceDiagrammNode) c).changeParent((SequenceDiagrammNode) child);
					} else {
						((SequenceDiagrammPath) c).changeParent((SequenceDiagrammNode) child);
					}
				});
				finding = true;
			}
		}
		if (!finding) {
			if(this.parent != null) {
				this.parent.getChildren().remove(this);
			} 
			this.parent = parent;
			parent.addChild(this);
		}
	}
	

	// Getters&Setters
	public SequenceDiagrammNode getParent() {
		return parent;
	}
	public void setParent(SequenceDiagrammNode parent) {
		this.parent = parent;
		parent.getChildren().add(this);
	}
	
	public List<UMLObject> getChildren() {
		return children;
	}
	
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	
	public String getBegin() {
		return begin;
	}
	public void setBegin(String begin) {
		this.begin = begin;
	}
	
	public String getCovered() {
		return covered;
	}
	public void setCovered(String covered) {
		this.covered = covered;
	}

	
}
