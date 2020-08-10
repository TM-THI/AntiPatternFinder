package uml.sequenceDiagramm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uml.UMLObject;
import uml.sequenceDiagramm.model.nodes.CombinedFragment;
import uml.sequenceDiagramm.model.nodes.Continuations;
import uml.sequenceDiagramm.model.nodes.Coregion;
import uml.sequenceDiagramm.model.nodes.DestructionOccurrenceSpecification;
import uml.sequenceDiagramm.model.nodes.DurationConstraint;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Frame;
import uml.sequenceDiagramm.model.nodes.InteractionUse;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.nodes.StateInvariant;
import uml.sequenceDiagramm.model.nodes.TimeConstraint;
import uml.sequenceDiagramm.model.paths.FoundMessage;
import uml.sequenceDiagramm.model.paths.GeneralOrdering;
import uml.sequenceDiagramm.model.paths.LostMessage;
import uml.sequenceDiagramm.model.paths.Message;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

/**
 * Klasse stellt die statische Funktion zum Suchen von Pattern in einem Sequenze Diagramm bereit.
 *
 */
public class SearchSequenceDiagramm {
	
	private SearchSequenceDiagramm() {
		// kein Konstruktor da nur statische Methoden bereitgestellt werden.
	}
	
	/**
	 * Funktion sucht rekurive nach dem pattern in dem SequenzeDiagramm tree
	 * @param tree Sequence Diagramm
	 * @param pattern Pattern (Sequence Diagramm) nach dem gesucht werden soll
	 * @return Liste von gefundenen SequenceDiagrammen die dem Pattern entsprechen.
	 */
	public static List<UMLObject> searchPattern(UMLObject tree, UMLObject pattern){
		List<UMLObject> res = new ArrayList<>();
		List<UMLObject> childRes = new ArrayList<>();
		if(tree instanceof SequenceDiagrammNode) {
			var treeNode = (SequenceDiagrammNode) tree;
			for(var child : treeNode.getChildren()) {
				childRes.addAll(searchPattern(child, pattern));
			}

			Map<Boolean, List<UMLObject>> currentRes = null;
			if(tree.equals(pattern)) {
				var finding = createObject(tree, null);
				if(pattern instanceof SequenceDiagrammNode) {
					currentRes = checkLevel(treeNode.getChildren(), ((SequenceDiagrammNode) pattern).getChildren(), finding);
					if(currentRes.containsKey(Boolean.TRUE)) {
						res.add(finding);
					}
				} else {
					res.add(finding);
				}
			}
		} else {
			if(tree.equals(pattern)) {
				var finding = createObject(tree, null);
				res.add(finding);
			}
		}
		res.addAll(childRes);
		return res;
	}
	
	/**
	 * Funktion sucht rekursive für die Ebene nach den Pattern. <br>
	 * Falls ein Element dem Pattern entspricht wird rekursiv für die Kindelemente nach einer übereinstimmung gesucht
	 * bis es keine Kindelemente mehr gibt. <br>
	 * Falls nicht durch eine NOT-Finding ausgeschlossen wird für die Kindelemente nach dem aktuellen Pattern gesucht.
	 */
	private static Map<Boolean, List<UMLObject>> checkLevel(List<UMLObject> diagramm, List<UMLObject> pattern, UMLObject parent) {
		
		Map<Boolean, List<UMLObject>> res = new HashMap<>();
		List<UMLObject> resList = new ArrayList<>();
		
		var diagrammIterator = diagramm.iterator();
		var patternIterator = pattern.iterator();
		
		boolean abort = checkNotOperator(diagramm, pattern);
		boolean isNotOperatorAbort = abort;
		boolean foundPattern = false;
		boolean found = false;
		boolean isFirst = true;
		
		UMLObject currentPattern = null;
		while(patternIterator.hasNext() && !abort) {	
			if(isFirst) {
				currentPattern = patternIterator.next();
				isFirst = false;
			}
			found = false;
			while(diagrammIterator.hasNext() && !abort) {
				var currentDiagramm = diagrammIterator.next();
				var equal = currentDiagramm.equals(currentPattern);
				var notCheck = currentPattern.getNotOperator();
				if(notCheck != null) {
					if(equal == false) {
						if(patternIterator.hasNext()) {
							currentPattern = patternIterator.next();
							equal = currentDiagramm.equals(currentPattern);
						} else {
							equal = !equal;
						}
					} else {
						equal = !equal;
					}
				}
				if(equal) {
					found = true;
					UMLObject curr = createObject(currentDiagramm, parent);
					Map<Boolean, List<UMLObject>> currRes;
					if((currentPattern instanceof SequenceDiagrammNode) && !((SequenceDiagrammNode) currentPattern).getChildren().isEmpty()) {
						if((currentDiagramm instanceof SequenceDiagrammNode) && !((SequenceDiagrammNode) currentDiagramm).getChildren().isEmpty()) {
							currRes = checkLevel(((SequenceDiagrammNode) currentDiagramm).getChildren(), ((SequenceDiagrammNode) currentPattern).getChildren(), curr);
						} else {
							currRes = new HashMap<>();
							currRes.put(Boolean.FALSE, new ArrayList<>());
						}
					} else {
						currRes = new HashMap<>();
						currRes.put(Boolean.TRUE, new ArrayList<>());
					}
					if(currRes.containsKey(Boolean.TRUE)) {
						resList.add(curr);
						
					}
					
					if(currRes.containsKey(Boolean.TRUE) && patternIterator.hasNext()) {
						currentPattern = patternIterator.next();
						if(currentPattern.getName().contains("**")) {
							if(patternIterator.hasNext()) {
								currentPattern = patternIterator.next();
							} else {
								foundPattern = true;
								abort = true;
							}
						}
					} else {
						if(currRes.containsKey(Boolean.TRUE)) {
							foundPattern = true;
							abort = true;
						} else {
							((SequenceDiagrammNode) parent).getChildren().remove(curr);
							found = false;
						}
					}
				}
			}
			if (found == false) {
				abort = true;
			}
		}
		
		// search for ChildObjects
		Map<Boolean, List<UMLObject>> childrenRes = new HashMap<>();
		if(!isNotOperatorAbort) {
			for(var child : diagramm) {
				if(child instanceof SequenceDiagrammNode) {
					var childNode = (SequenceDiagrammNode) child;
					var parentClone = (SequenceDiagrammNode) parent.clone();
					var childClone = createObject(child, parentClone);
					var tmpRes = checkLevel(childNode.getChildren(), pattern, childClone);
					if(tmpRes.containsKey(Boolean.TRUE)) {
						if(childClone instanceof SequenceDiagrammNode) {
							((SequenceDiagrammNode) childClone).changeParent((SequenceDiagrammNode) parent);
						} else {
							((SequenceDiagrammPath) childClone).changeParent((SequenceDiagrammNode) parent);
						}
						if(childrenRes.containsKey(Boolean.TRUE)) {
							childrenRes.get(Boolean.TRUE).addAll(tmpRes.get(Boolean.TRUE));
						} else {
							List<UMLObject> tmpList = new ArrayList<>();
							tmpList.addAll(tmpRes.get(Boolean.TRUE));
							childrenRes.put(Boolean.TRUE, tmpList);
						}
					}
				}
			}
		}
		
		// Matching results
		if(foundPattern || childrenRes.containsKey(Boolean.TRUE)) {
			List<UMLObject> matchList = new ArrayList<>();
			if(foundPattern) {
				matchList.addAll(resList);
			}
			if(childrenRes.containsKey(Boolean.TRUE)) {
				matchList.addAll(childrenRes.get(Boolean.TRUE));
			}
			if(!matchList.isEmpty()) {
				res.put(Boolean.TRUE, matchList);
			}
		} 
		if(!foundPattern || !childrenRes.containsKey(Boolean.FALSE)) {
			List<UMLObject> matchList = new ArrayList<>();
			if(!foundPattern) {
				matchList.addAll(resList);
			}
			if(childrenRes.containsKey(Boolean.FALSE)) {
				matchList.addAll(childrenRes.get(Boolean.FALSE));
			}
			if(!matchList.isEmpty()) {
				res.put(Boolean.FALSE, matchList);
			}
		}
		return res;
	}
	
	/**
	 * Überprüft ob die aktuellen Pattern ein NotOperator beinhalten und wenn ja ob dieses auf ein Element
	 * der aktuellen Ebene zutrifft.
	 */
	private static boolean checkNotOperator(List<UMLObject> diagramm, List<UMLObject> pattern) {
		for(UMLObject p : pattern) {
			if(p.isPattern()) {
				if(p.getNotOperator() != null) {
					for(UMLObject d : diagramm) {
						if(d.equals(p)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Erstellt eine Objekt "Kopie" für das {@link UMLObject} o mit dem Parent {@link UMLObject} parent
	 * @param o Vorbildobject das kopiert werden soll.
	 * @param parent Elternobjekt des kopierten Objekts.
	 * @return Die Kopie des Objekts.
	 */
	public static UMLObject createObject(UMLObject o, UMLObject parent) {
		var search = findChildObject(o, parent);
		if(search != null) {
			return search;
		}
		if(o instanceof SequenceDiagrammNode) {
			return createNode((SequenceDiagrammNode) o, (SequenceDiagrammNode) parent);
		} else if (o instanceof SequenceDiagrammPath) {
			UMLObject res;
			SequenceDiagrammPath path = (SequenceDiagrammPath) o;
			
			if(path.getBegin() == null) {
				res = createPath(path, null, (SequenceDiagrammNode) parent);
			} else if(path.getEnd() == null) {
				res = createPath(path, (SequenceDiagrammNode) parent, null);
			} else {
				if(path.getBegin().equals(parent)) {
					res = createPath(path, (SequenceDiagrammNode) parent, (SequenceDiagrammNode) createNode(path.getEnd(), null));
				} else {
					res = createPath(path, (SequenceDiagrammNode) createNode(path.getBegin(), null), (SequenceDiagrammNode) parent);
				}
			}
			
			return res;
			
		} else {
			return null;
		}
	}
	
	/**
	 * Findet das Kindobjekt {@link UMLObject} o für das {@link UMLObject} parent und gibt das gefundene Objekt zurück.
	 * Falls kein passendes Kindobjekt gefunden wird, wird null zurück gegeben.
	 */
	private static UMLObject findChildObject(UMLObject o, UMLObject parent) {
		if(parent instanceof SequenceDiagrammNode) {
			var parentNode = (SequenceDiagrammNode) parent;
			for(var child : parentNode.getChildren()) {
				if(child.equals(o)) {
					return child;
				}
			}
		} else {
			return null;
		}
		return null;
	}
	
	/**
	 * Erstellt eine Kopie für das {@link SequenceDiagrammNode} o unter dem Parent {@link SequenceDiagrammNode} parentNode
	 */
	private static UMLObject createNode(SequenceDiagrammNode o, SequenceDiagrammNode parentNode) {
		UMLObject res;
		switch (o.getObjectType()) {
		case "CombinedFragment": {
			res = new CombinedFragment(parentNode);
			break;
		}
		case "Continuations": {
			res = new Continuations(parentNode);
			break;
		}
		case "Coregion": {
			res = new Coregion(parentNode);
			break;
		}
		case "DestructionOccurrenceSpecification": {
			res = new DestructionOccurrenceSpecification(parentNode);
			break;
		}
		case "DurationConstraint": {
			res = new DurationConstraint(parentNode);
			break;
		}
		case "ExecutionSpecification": {
			res = new ExecutionSpecification(parentNode);
			break;
		}
		case "Frame": {
			res = new Frame(parentNode);
			break;
		}
		case "InteractionUse": {
			res = new InteractionUse(parentNode);
			break;
		}
		case "Lifeline": {
			res = new Lifeline(o.getName(), parentNode);
			break;
		}
		case "StateInvarient": {
			res = new StateInvariant(parentNode);
			break;
		}
		case "TimeConstraint": {
			res = new TimeConstraint(parentNode);
			break;
		}
		case "SequenceDiagramm": {
			res = new SequenceDiagramm();
			break;
		}
		default:
			throw new RuntimeException("Invalid Object Type for SequenceDiagrammNode");
		}
		res.setName(o.getName());
		return res;
	}
	
	/**
	 * Erstellt eine Kopie für den {@link SequenceDiagrammPath} o mit dem Startpunkt {@link SequenceDiagrammNode} startNode und dem 
	 * Endpunkt {@link SequenceDiagrammNode} endNode
	 */
	private static UMLObject createPath(SequenceDiagrammPath o, SequenceDiagrammNode startNode, SequenceDiagrammNode endNode) {
		UMLObject res;
		switch (o.getObjectType()) {
		case "FoundMessage": {
			res = new FoundMessage(endNode);
			break;
		}
		case "GeneralOrdering": {
			res = new GeneralOrdering(startNode, endNode);
			break;
		}
		case "LostMessage": {
			res = new LostMessage(startNode);
			break;
		}
		case "Message": {
			res = new Message(startNode, endNode);
			break;
		}
		default: 
			throw new RuntimeException("Invalid Object Type for SequenceDiagrammPath");
		}
		res.setName(o.getName());
		return res;
	}

}
