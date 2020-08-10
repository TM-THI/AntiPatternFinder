package uml;

import uml.sequenceDiagramm.SearchSequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;

/**
 * Klasse stellt das Standard UML-Object dar.
 * Inklusive der Erweiterungen für die Pattern {@link UMLPatternExtension}
 *
 */
public abstract class UMLObject extends UMLPatternExtension{
	
	private String objectType = "";
	private String name = "";
	private String id = "";
	
	public UMLObject() {
		
	}
	
	public UMLObject(String name) {
		this.name = name;
	}
	
	
	public String getPatternName() {
		if(this.isPattern()) {
			var res = this.name;
			if(this.getWildCardOperator() != null) {
				res = "*";
			}
			if(this.getDictionary() != null) {
				res = String.format("$%s$", this.getDictionary().getName());
			}
			if(this.getContains() != null) {
				if(this.getContains().containsDictionary()) {
					res = String.format("€$%s$€", this.getContains().getSubDictionary().getName());
				} else {
					res = String.format("€%s€", this.getContains().getSubString());
				}
			}
			if(this.getNotOperator() != null) {
				res = String.format("!%s!", res);
			}
			return res;
		} else {
			return this.name;
		}
	}
	
	// Getters&Setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getObjectType() {
		return objectType;
	}
	protected void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	
	public void setId(String id) {
		if(this.id.isEmpty()) {
			this.id = id;
		}
	}
	public String getId() {
		return id;
	}
	
	
	// Override
	
	@Override
	public String toString() {
		return this.getPatternName();
	}
	
	@Override
	public boolean equals(Object o) {
		boolean res = true;
		if(!this.getClass().equals(o.getClass())) {
			res = false;
		}
		if(!(o instanceof UMLObject)) {
			return false;
		}
		UMLObject pattern = (UMLObject) o;
		if(this.getObjectType().compareTo(pattern.getObjectType())!=0){
			res = false;
		}
		if(pattern.isPattern()) {
			if(pattern.getContains()!=null && !checkContains(this.getName(), pattern.getContains())) {
				res = false;
			}
			if(pattern.getDictionary()!=null && !checkDictionary(this.getName(), pattern.getDictionary())) {
				res = false;
			}
			if(pattern.getWildCardOperator()!=null && !checkWildCardOperator(this.getName(), pattern.getWildCardOperator())) {
				res = false;
			}
		}
		if(!pattern.getName().isBlank()) {
			if(pattern.getName().compareTo(this.getName())!=0) {
				res = false;
			}
		}
		return res;
	}
	
	
	@Override
	public UMLObject clone() {
		return cloneHelper(this, null);
	}
	
	public UMLObject clone(UMLObject parent) {
		return cloneHelper(this, parent);
	}
	
	
	// Helper 
	private boolean checkContains(String name, Contains c) {
		return c.checkName(name);
	}
	private boolean checkDictionary(String name, Dictionary d) {
		return d.checkName(name);
	}
	private boolean checkWildCardOperator(String name, WildCardOperator w) {
		return w.checkName(name);
	}
	
	private UMLObject cloneHelper(UMLObject start, UMLObject parent) {
		var startClone = SearchSequenceDiagramm.createObject(this, parent);
		if(start instanceof SequenceDiagrammNode) {
			var startNode = (SequenceDiagrammNode) start;
			for(var child : startNode.getChildren()) {
				child.clone(startClone);
			}
		}
		return startClone;
	}
}
