package uml.XMI.read;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import uml.UMLObject;
import uml.UMLPatternHelper;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.antiPattern.NotOperator;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.ExecutionOccurenceSpecification;
import uml.sequenceDiagramm.model.paths.Message;
import uml.sequenceDiagramm.model.paths.MessageOccurenceSpecification;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

/**
 * Klasse stellt Hilfsfunktionen für ReadXMI bereit.
 *
 */
public class ReadXMIHelper {
	
	private List<Dictionary> dictionarys = new ArrayList<>();
	private boolean isPattern = false;
	
	public ReadXMIHelper() {
		
	}
	
	/**
	 * Funnktion generiert Modele aus dem im enthaltenen {@link Node} model.
	 */
	public List<UMLObject> generateModel(Node model) {
		List<UMLObject> diagramms = new ArrayList<>();
		for(int i=0; i < model.getChildNodes().getLength(); i++) {
			var child = model.getChildNodes().item(i);
			if(child.getNodeName().compareTo("packagedElement") == 0) {
				var attributes = readAttributeNameIdType(child.getAttributes());
				String name = attributes.get("name");
				String id = attributes.get("id");
				String type = attributes.get("type");
				
				if(type.compareTo("uml:Interaction") == 0) {
					// SequenceDiagramm
					SequenceDiagramm sequence = new SequenceDiagramm();
					sequence.setName(name);
					sequence.setId(id);
					generateSequenceDiagramm(sequence, child);
					if(isPattern) {
						UMLPatternHelper.setAllChildrenToPattern(sequence);
					}
					diagramms.add(sequence);
				}
				
				if(type.compareTo("uml:extension") == 0) {
					// UML Extension
					dictionarys.addAll(readAllDictionarys(child));
					
				}
			}

		}
		return diagramms;
	}
	
	private Map<String, String> readAttributeNameIdType(NamedNodeMap attributes) {
		String name = "";
		String id = "";
		String type = "";
		for(int j=0; j < attributes.getLength(); j++) {
			var attribute = attributes.item(j);
			if(attribute.toString().startsWith("name")) {
				name = attribute.toString().substring(6, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("xmi:id")) {
				id = attribute.toString().substring(8, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("xmi:type")) {
				type = attribute.toString().substring(10, attribute.toString().length() - 1);
			}
		}
		Map<String, String> res = new HashMap<>();
		res.put("name", name);
		res.put("id", id);
		res.put("type", type);
		return res;
	}
	
	/**
	 * Funktion generiert ein SequenceDiagramm aus dem Node
	 * @param sequence Das SequenceDiagramm unter dem die Kindelemente aufgehängt werden sollen.
	 * @param startNode Der {@link Node} der alle Kindelemente enthält.
	 */
	private void generateSequenceDiagramm(SequenceDiagramm sequence, Node startNode) {
		Map<String, SequenceDiagrammNode> node = new HashMap<>();
		Map<String, SequenceDiagrammPath> messages = new HashMap<>();
		Map<String, List<String>> nodeEvents = new HashMap<>();
		Map<String, UMLObject> occurence = new HashMap<>();
		
		for(int i=0; i < startNode.getChildNodes().getLength(); i++) {
			var child = startNode.getChildNodes().item(i);
			if(child.getAttributes() != null) {
				var attributes = readAttributesSequenceDiagramm(child);
				String name = (String) attributes.get("name");
				String id = (String) attributes.get("id");
				String type = (String) attributes.get("type");
				List<String> coveredBy = (List<String>) attributes.get("coveredBy");
				String receiveEvent = (String) attributes.get("receiveEvent");
				String sendEvent = (String) attributes.get("sendEvent");
				String covered = (String) attributes.get("covered");
				String messageInOccurence = (String) attributes.get("message");
				String finish = (String) attributes.get("finish");

				switch(child.getNodeName().toString()) {
				case "lifeline":
					var lifeline = new Lifeline(name, sequence);
					lifeline.setId(id);
					checkNamePattern(lifeline);
					node.put(id, lifeline);
					nodeEvents.put(id, coveredBy);
					break;
				case "fragment":
					if(type.compareTo("uml:MessageOccurrenceSpecification") == 0) {
						var messageOccurence = new MessageOccurenceSpecification();
						messageOccurence.setName(name);
						messageOccurence.setId(id);
						messageOccurence.setMessage(messageInOccurence);
						occurence.put(id, messageOccurence);
					}
					if(type.compareTo("uml:ActionExecutionSpecification") == 0 || type.compareTo("uml:BehaviorExecutionSpecificationn") == 0) {
						var exec = new ExecutionSpecification(name, null);
						exec.setId(id);
						exec.setFinish(finish);
						checkNamePattern(exec);
						node.put(id, exec);
					}
					if(type.compareTo("uml:ExecutionOccurrenceSpecification") == 0) {
						var execOccurence = new ExecutionOccurenceSpecification();
						execOccurence.setName(name);
						execOccurence.setId(id);
						occurence.put(id, execOccurence);
					}

					break;
				case "message":
					var message = new Message(name, null, null);
					message.setId(id);
					message.setReceiveEvent(receiveEvent);
					message.setSendEvent(sendEvent);
					checkNamePattern(message);
					messages.put(id, message);
					break;
				default:

				}
			}		
		}
		buildTree(sequence, startNode, node, messages, nodeEvents, occurence);
	}
	
	/**
	 * Funktion liest alle {@link Dictionary}s unter dem {@link Node} ein
	 * @param startNode Knoten unter dem die Dictionarys eingelesen werden sollen.
	 * @return Liste von Dictionarys
	 */
	private List<Dictionary> readAllDictionarys(Node startNode) {
		List<Dictionary> res = new ArrayList<>();
		for(int i=0; i < startNode.getChildNodes().getLength(); i++) {
			var child = startNode.getChildNodes().item(i);
			if(child.getAttributes() != null) {
				var attributes = readAttributesDictionary(child);
				
				String name = (String) attributes.get("name");
				String type = (String) attributes.get("type");
				List<String> value = (List<String>) attributes.get("value");
				
				switch(child.getNodeName().toString()) {
				case "dictionary":
					var dict = new Dictionary(name, value.toArray(new String[0]));
					res.add(dict);
					break;
				default:

				}
			}		
		}
		return res;	
	}
	
	/**
	 * Überprüft das {@link UMLObject} uml ob es sich um ein Pattern handelt. Und verarbeitet die Information. 
	 * @param uml Object das Überprüft werden soll.
	 */
	private void checkNamePattern(UMLObject uml) {
		String name = uml.getName();
		if(name.isBlank()) {
			return;
		}
		switch(name.charAt(0)) {
		case '*':
			if(name.compareTo("*") == 0) {
				uml.setWildCardOperator(new WildCardOperator());
				uml.setName("");
				isPattern = true;
			}
			break;
		case '$':
			if(name.startsWith("$") && name.endsWith("$")) {
				Dictionary dict = null;
				for(var d : dictionarys) {
					if(name.substring(1, name.length() - 1).compareTo(d.getName()) == 0) {
						dict = d;
					}
				}
				if(dict == null) {
					throw new RuntimeException(String.format("No such Dictionary %s", name));
				}
				uml.setDictionary(dict);
				uml.setName("");
				isPattern = true;
			}
			break;
		case '€':
			if(name.startsWith("€") && name.endsWith("€")) {
				Contains c = null;
				if(name.substring(1).startsWith("$") && name.substring(0, name.length() - 1).endsWith("$")) {
					Dictionary dict = null;
					for(var d : dictionarys) {
						if(name.substring(2, name.length() - 2).compareTo(d.getName()) == 0) {
							dict = d;
						}
					}
					if(dict == null) {
						throw new RuntimeException(String.format("No such Dictionary %s", name));
					}
					c = new Contains(dict);
				} else {
					c = new Contains(name.substring(1, name.length() - 1));
				}
				uml.setContains(c);
				uml.setName("");
				isPattern = true;
			}
			break;
		case '!':
			if(name.startsWith("!") && name.endsWith("!")) {
				if(name.substring(1).startsWith("$") && name.substring(0, name.length() - 1).endsWith("$")) {
					var nameShort = name.substring(1, name.length() - 1);
					if(nameShort.startsWith("$") && nameShort.endsWith("$")) {
						Dictionary dict = null;
						for(var d : dictionarys) {
							if(nameShort.substring(1, nameShort.length() - 1).compareTo(d.getName()) == 0) {
								dict = d;
							}
						}
						if(dict == null) {
							throw new RuntimeException(String.format("No such Dictionary %s", nameShort));
						}
						uml.setDictionary(dict);
						uml.setName("");
					}
				} else if(name.substring(1).startsWith("€") && name.substring(0, name.length() - 1).endsWith("€")) {
					var nameShort = name.substring(1, name.length() - 1);
					if(nameShort.startsWith("€") && nameShort.endsWith("€")) {
						Contains c = null;
						if(nameShort.substring(1).startsWith("$") && nameShort.substring(0, nameShort.length() - 1).endsWith("$")) {
							Dictionary dict = null;
							for(var d : dictionarys) {
								if(nameShort.substring(2, nameShort.length() - 2).compareTo(d.getName()) == 0) {
									dict = d;
								}
							}
							if(dict == null) {
								throw new RuntimeException(String.format("No such Dictionary %s", nameShort.substring(1, nameShort.length() - 1)));
							}
							c = new Contains(dict);
						} else {
							c = new Contains(nameShort.substring(1, nameShort.length() - 1));
						}
						uml.setContains(c);
						uml.setName("");
					}
					
				}
				uml.setNotOperator(new NotOperator());
				isPattern = true;
			}
			break;
		default:
				
		}
	}
	
	/**
	 * Funktion List die Attribute eines Dictionarys ein.
	 * @param node Knoten dessen Eigenschaften analysiert werden sollen.
	 * @return Gefundenden Eigenschaften.
	 */
	private Map<String, Object> readAttributesDictionary(Node node) {
		Map<String, Object> res = new HashMap<>();
		String name = "";
		String type = "";
		List<String> value = new ArrayList<>();
		for(int j=0; j < node.getAttributes().getLength(); j++) {
			var attribute = node.getAttributes().item(j);
			if(attribute.toString().startsWith("name")) {
				name = attribute.toString().substring(6, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("xmi:type")) {
				type = attribute.toString().substring(10, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("value")) {
				var cov = attribute.toString().substring(7, attribute.toString().length() - 1).strip();
				value = Arrays.asList(cov.split(" "));
			}
		}
		res.put("name", name);
		res.put("type", type);
		res.put("value", value);
		return res;
	}
	
	/**
	 * Funktion liest die Attribute eines {@link Node} ein und gibt diese als Map zurück.
	 */
	private Map<String, Object> readAttributesSequenceDiagramm(Node node) {
		Map<String, Object> res = new HashMap<>();
		String name = "";
		String id = "";
		String type = "";
		List<String> coveredBy = new ArrayList<>();
		String receiveEvent = "";
		String sendEvent = "";
		String covered = "";
		String message = "";
		String finish = "";
		for(int j=0; j < node.getAttributes().getLength(); j++) {
			var attribute = node.getAttributes().item(j);
			if(attribute.toString().startsWith("name")) {
				name = attribute.toString().substring(6, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("xmi:id")) {
				id = attribute.toString().substring(8, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("xmi:type")) {
				type = attribute.toString().substring(10, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("coveredBy")) {
				var cov = attribute.toString().substring(11, attribute.toString().length() - 1);
				coveredBy = Arrays.asList(cov.split(" "));
			}
			if(attribute.toString().startsWith("receiveEvent")) {
				receiveEvent = attribute.toString().substring(14, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("sendEvent")) {
				sendEvent = attribute.toString().substring(11, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("covered=")) {
				covered = attribute.toString().substring(9, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("message=")) {
				message = attribute.toString().substring(9, attribute.toString().length() - 1);
			}
			if(attribute.toString().startsWith("finish=")) {
				finish = attribute.toString().substring(8, attribute.toString().length() - 1);
			}
		}
		res.put("name", name);
		res.put("id", id);
		res.put("type", type);
		res.put("coveredBy", coveredBy);
		res.put("receiveEvent", receiveEvent);
		res.put("sendEvent", sendEvent);
		res.put("covered", covered);
		res.put("message", message);
		res.put("finish", finish);
		return res;
	}
	
	/**
	 * Funktion setzt die Elemente zusammen und bildet den Baum der das SequenceDiagramm darstellt.
	 */
	private void buildTree(SequenceDiagramm sequence, Node startNode, Map<String, SequenceDiagrammNode> node, 
		Map<String, SequenceDiagrammPath> messages, Map<String, List<String>> nodeEvents, Map<String, UMLObject> occurence) {
		// Events für jede Lifeline abgleichen
		for(var lifeline: nodeEvents.keySet()) {
			var lifelineElement = node.get(lifeline);
			var currentParent = lifelineElement;
			var currentFinish = "";
			List<String> events = nodeEvents.get(lifeline);
			for(var event : events) {
				if(node.containsKey(event)) {
					var currentNode = node.get(event);
					currentNode.setParent(currentParent);
					currentParent = currentNode;
					currentFinish = currentNode.getFinish();
				} else if(occurence.containsKey(event)) {
					var occure = occurence.get(event);
					if(occure instanceof MessageOccurenceSpecification) {
						var message = messages.get(((MessageOccurenceSpecification) occure).getMessage());
						if(occure.getName().toLowerCase().contains("send")) {
							message.setBeginn(currentParent);
						} else if(occure.getName().toLowerCase().contains("receive")) {
							message.setEnd(currentParent);
						}
						if(currentFinish.compareTo(message.getReceiveEvent()) == 0 ) {
							if(currentParent.getParent() != null) {
								currentParent = currentParent.getParent();
								currentFinish = currentParent.getFinish();
							}
						}
					} else if (occure instanceof ExecutionOccurenceSpecification) {
						if(occure.getName().toLowerCase().contains("finish")) {
							if(currentParent.getParent() != null) {
								currentParent = currentParent.getParent();
							} else {
//								System.out.println(currentParent.getName());
							}
						}
						
					}
				}
			}
		}
	}
}
