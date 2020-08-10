package uml.XMI.write;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import uml.UMLObject;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.ExecutionOccurenceSpecification;
import uml.sequenceDiagramm.model.paths.Message;
import uml.sequenceDiagramm.model.paths.MessageOccurenceSpecification;

/**
 * Klasse stellt Hilfsfunktionen für WriteXMI bereit.
 *
 */
public class WriteXMIHelper {
	
	private List<String> result = new ArrayList<>();
	private int start = 0;
	
	private Map<String, ExecutionSpecification> exec = new HashMap<>();
	private Map<String, ExecutionOccurenceSpecification> execOccur = new HashMap<>();
	private Map<String, Message> message = new HashMap<>();
	private Map<String, MessageOccurenceSpecification> msgOccur = new HashMap<>();
	
	private List<Dictionary> dictionarys = new ArrayList<>();
	
	public WriteXMIHelper() {
		
	}
	
	/**
	 * Funktion erzeugt eine .uml Datei. Die einzelnen Zeilen werden als Liste zurück gegeben.
	 * 
	 * @param uml Das Modell das gespeichert werden soll.
	 * @return Liste von Zeilen die eine UML Datei darstellen.
	 */
	public List<String> getOutput(UMLObject uml){
		
		generateHeader("findings", generateID());
		
		generatePackageImport(generateID());
		
		findAllDictionarys(uml);
		if(!dictionarys.isEmpty()) {
			generateExtensionDictionary(dictionarys);
		}
		
		if(uml instanceof SequenceDiagramm) {
			generateSequenceDiagramm((SequenceDiagramm) uml);
		}
		return result;
	}
	
	/**
	 * Erzeugt den Header einer UML Datei.
	 */
	private void generateHeader(String modelName, String modelId) {
		String topHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		String modelHeader = String.format("<uml:Model xmi:version=\"20131001\" xmlns:xmi=\"http://www.omg.org/spec/XMI/20131001\" xmlns:uml=\"http://www.eclipse.org/uml2/5.0.0/UML\" xmi:id=\"%s\" name=\"%s\">", modelId, modelName);
		String modelEnd = "</uml:Model>";
		
		result.add(topHeader);
		start++;
		result.add(start, modelEnd);
		result.add(start, modelHeader);
		start++;
		
	}
	
	/**
	 * Erzeugt die Imports für eine UML Datei.
	 */
	private void generatePackageImport(String id) {
		String packageImport = String.format("<packageImport xmi:type=\"uml:PackageImport\" xmi:id=\"%s\">", id);
		String packageImportEnd = "</packageImport>";
		String importLib = "<importedPackage xmi:type=\"uml:Model\" href=\"pathmap://UML_LIBRARIES/UMLPrimitiveTypes.library.uml#_0\"/>";
		
		result.add(start, packageImportEnd);
		result.add(start, packageImport);
		start++;
		result.add(start, importLib);
		start++;
		
		start++;
	}
	
	/**
	 * Erzeugt die Dictionarys für eine UML Datei.
	 */
	private void generateExtensionDictionary(List<Dictionary> dictionarys) {
		String extensionDictHeader = "<packagedElement xmi:type=\"uml:extension\" xmi:id=\"extensionDictionary\" name=\"Dictionary\">";
		String extensionDictEnd = "</packagedElement>";
		
		List<String> dictionaryList = new ArrayList<>();
		for(Dictionary dict : dictionarys) {
			String content = "";
			for(String name : dict.getNames()) {
				content = String.format("%s %s", content, name);
			}
			String current = String.format("<dictionary xmi:type=\"uml:extension:Dictionary\" name=\"%s\" value=\"%s\"/>", dict.getName(), content);
			dictionaryList.add(current);
		}
		
		result.add(start, extensionDictEnd);
		result.add(start, extensionDictHeader);
		start++;
		for(String dict : dictionaryList) {
			result.add(start, dict);
			start++;
		}
		
		start++;
	}
	
	/**
	 * Erzeugt das Sequence Diagramm in einer UML Datei.
	 */
	private void generateSequenceDiagramm(SequenceDiagramm sequenceDiagramm) {
		
		checkId(sequenceDiagramm);
		
		String header = String.format("<packagedElement xmi:type=\"uml:Interaction\" xmi:id=\"%s\" name=\"%s\">", sequenceDiagramm.getId(), sequenceDiagramm.getName());
		String end = "</packagedElement>";
		
		result.add(start, end);
		result.add(start, header);
		start++;
		
		generateSequenceDiagrammObjects(sequenceDiagramm);
		
		start++;
		
	}
	
	/**
	 * Koordiniert die gernerierung eines Sequence Diagramms in einer UML Datei.
	 */
	private void generateSequenceDiagrammObjects(SequenceDiagramm sequenceDiagramm) {
		
		List<Lifeline> lifelines = findLifeline(sequenceDiagramm);
		
		for(var life : lifelines) {
			organizeLifeline(life);
		}
		
		printLifelines(lifelines);
		
		for(var life : lifelines) {
			printOrder(life);
		}
		for(var msg : message.values()) {
			printMessage(msg);
		}
		
	}
	
	/**
	 * Koordiniert die Reihenfolge für das einfügen einer Lifeline.
	 */
	private void printOrder(Lifeline lifeline) {
		for(String id : lifeline.getCovered().split(" ")) {
			if(execOccur.containsKey(id)) {
				var ex = execOccur.get(id);
				printExecOccurence(ex);
				if(ex.getName().contains(":Start")) {
					printExecution(exec.get(ex.getExecution()));
				}
			} else if(msgOccur.containsKey(id)) {
				printMessageOccurence(msgOccur.get(id));
			}
		}
	}
	
	/**
	 * Fügt eine {@link Lifeline} in das UML Modell ein.
	 */
	private void printLifelines(List<Lifeline> lifelines) {
		for(var life : lifelines) {
			String lifeString = String.format("<lifeline xmi:type=\"uml:Lifeline\" xmi:id=\"%s\" name=\"%s\" coveredBy=\"%s\"/>" , life.getId(), life.getPatternName(), life.getCovered());
			result.add(start, lifeString);
			start++;
		}
	}
	
	/**
	 * Fügt eine {@link ExecutionOccurenceSpecification} in das UML Modell ein.
	 */
	private void printExecOccurence(ExecutionOccurenceSpecification e) {
		String execOccurString = String.format("<fragment xmi:type=\"uml:ExecutionOccurrenceSpecification\" xmi:id=\"%s\" name=\"%s\" covered=\"%s\" execution=\"%s\"/>" , e.getId(), e.getPatternName(), e.getCovered(), e.getExecution());
		result.add(start, execOccurString);
		start++;
	}
	
	/**
	 * Fügt eine {@link ExecutionSpecification} in das UML Modell ein.
	 */
	private void printExecution(ExecutionSpecification e) {
		String execString = String.format("<fragment xmi:type=\"uml:ActionExecutionSpecification\" xmi:id=\"%s\" name=\"%s\" covered=\"%s\" finish=\"%s\" start=\"%s\"/>" , e.getId(), e.getPatternName(), e.getCovered(), e.getFinish(), e.getBegin());
		result.add(start, execString);
		start++;
	}

	/**
	 * Fügt eine {@link MessageOccurenceSpecification} in das UML Modell ein.
	 */
	private void printMessageOccurence(MessageOccurenceSpecification msg) {
		String msgOccurString = String.format("<fragment xmi:type=\"uml:MessageOccurrenceSpecification\" xmi:id=\"%s\" name=\"%s\" covered=\"%s\" message=\"%s\"/>" , msg.getId(), msg.getPatternName(), msg.getCovered(), msg.getMessage());
		result.add(start, msgOccurString);
		start++;
	}

	/**
	 * Fügt eine {@link Message} in das UML Modell ein.
	 */
	private void printMessage(Message msg) {
		String msgOccurString = String.format("<message xmi:type=\"uml:Message\" xmi:id=\"%s\" name=\"%s\" receiveEvent=\"%s\" sendEvent=\"%s\"/>" , msg.getId(), msg.getPatternName(), msg.getReceiveEvent(), msg.getSendEvent());
		result.add(start, msgOccurString);
		start++;
	}
	
	/**
	 * Überprüft ob ein {@link UMLObject} und alle Kindobjekte eine ID haben. Falls nicht wird eine erzeugt.
	 */
	private void checkId(UMLObject node) {
		if(node.getId().isBlank()) {
			node.setId(generateID());
		}
		if(node instanceof SequenceDiagrammNode) {
			((SequenceDiagrammNode) node).getChildren().forEach(c -> checkId(c));
		}
	}
	
	/**
	 * Funktion findet alle {@link Lifeline}s in einem {@link SequenceDiagramm}
	 */
	private List<Lifeline> findLifeline(SequenceDiagramm sequenceDiagramm) {
		List<Lifeline> lifelines = new ArrayList<>();
		for(UMLObject uml : sequenceDiagramm.getChildren()) {
			if(uml instanceof Lifeline) {
				lifelines.add((Lifeline) uml);
			}
		}
		return lifelines;
	}
	
	/**
	 * Funktion organisiert eine Lifeline.
	 */
	private void organizeLifeline(Lifeline lifeline) {
		for(var child : lifeline.getChildren()) {
			organizeNode(lifeline, child, lifeline.getId());
		}
		lifeline.setCovered(createCoveredBy(lifeline, lifeline));
	}
	
	/**
	 * Funktion organisiert einen Node
	 */
	private void organizeNode(Lifeline lifeline, UMLObject node, String oldNode) {
		if(node instanceof ExecutionSpecification) {
			var execOccurence = new ExecutionOccurenceSpecification();
			execOccurence.setId(generateID());
			execOccurence.setName(node.getPatternName() + ":Start");
			execOccurence.setCovered(lifeline.getId());
			execOccurence.setExecution(node.getId());
			((ExecutionSpecification) node).setBegin(execOccurence.getId());
			
			((ExecutionSpecification) node).getChildren().forEach(n -> organizeNode(lifeline, n, node.getId()));
			
			var execOccurenceEnd = new ExecutionOccurenceSpecification();
			execOccurenceEnd.setId(generateID());
			execOccurenceEnd.setName(node.getPatternName() + ":Finish");
			execOccurenceEnd.setCovered(lifeline.getId());
			execOccurenceEnd.setExecution(node.getId());
			
			((ExecutionSpecification) node).setFinish(execOccurenceEnd.getId());
			
			((ExecutionSpecification) node).setCovered(lifeline.getId());
			
			exec.put(node.getId(), (ExecutionSpecification) node);
			execOccur.put(execOccurence.getId(), execOccurence);
			execOccur.put(execOccurenceEnd.getId(), execOccurenceEnd);
			
		} else if(node instanceof Message) {
			var messageOccurence = new MessageOccurenceSpecification();
			messageOccurence.setId(generateID());
			messageOccurence.setCovered(lifeline.getId());
			messageOccurence.setMessage(node.getId());
			
			
			if(((Message) node).getBegin().getId().compareTo(oldNode) == 0) {
				((Message) node).setSendEvent(messageOccurence.getId());
				messageOccurence.setName(node.getPatternName() + ":Send");
			} else if(((Message) node).getEnd().getId().compareTo(oldNode) == 0) {
				((Message) node).setReceiveEvent(messageOccurence.getId());
				messageOccurence.setName(node.getPatternName() + ":Receive");
			} else {
				throw new RuntimeException(String.format("No MsgOccurence found for Message %s", node.getId()));
			}
			
			message.put(node.getId(), (Message) node);
			msgOccur.put(messageOccurence.getId(), messageOccurence);
		}
	}
	
	/**
	 * Funktion erzeugt die CoveredBy eigenschaft für eine {@link lifeline}
	 */
	private String createCoveredBy(UMLObject uml, Lifeline lifeline) {
		String res = "";
		if(uml instanceof Lifeline) {
			for(var child : ((Lifeline) uml).getChildren()) {
				res = res + createCoveredBy(child, lifeline);
			}
		} else if(uml instanceof ExecutionSpecification) {
			ExecutionOccurenceSpecification start = null;
			ExecutionOccurenceSpecification finish = null;
			for(var exOcurance : execOccur.values()) {
				if(exOcurance.getExecution().contains(uml.getId())) {
					if(exOcurance.getName().contains(":Start")) {
						start = exOcurance;
					} else if(exOcurance.getName().contains(":Finish")) {
						finish = exOcurance;
					}
				}
			}
			res = res + start.getId() + " ";
			((ExecutionSpecification) uml).setBegin(start.getId());
			res = res + uml.getId() + " ";
			for(var child : ((SequenceDiagrammNode) uml).getChildren()) {
				res = res + createCoveredBy(child, lifeline) + " ";
			}
			res = res + finish.getId() + " ";
			((ExecutionSpecification) uml).setFinish(finish.getId());
		} else if(uml instanceof Message) {
			for(var msg : msgOccur.values()) {
				if(msg.getMessage().compareTo(uml.getId()) == 0) {
					if(msg.getCovered().compareTo(lifeline.getId()) == 0) {
						res = res + msg.getId() + " ";
					}
				}
			}
		}
		return res;
	}

	/**
	 * Funktion findet alle {@link Dictionary}s in einem {@link UMLObject}.
	 */
	private void findAllDictionarys(UMLObject uml) {
		if(uml.isPattern()) {
			if(uml.getDictionary() != null) {
				if(!dictionarys.contains(uml.getDictionary())) {
					dictionarys.add(uml.getDictionary());
				}
			}else if(uml.getContains() != null) {
				if(uml.getContains().getSubDictionary() != null) {
					if(!dictionarys.contains(uml.getContains().getSubDictionary())) {
						dictionarys.add(uml.getContains().getSubDictionary());
					}
				}
			}
		}
		if(uml instanceof SequenceDiagrammNode) {
			((SequenceDiagrammNode) uml).getChildren().forEach(c -> findAllDictionarys(c));
		}
	}
	
	/**
	 * Funktion generiert eine eindeutige ID.
	 */
	private String generateID() {
		String id = UUID.randomUUID().toString().replace("-", "");
		id = "_" + id;
		return id;
	}
}
