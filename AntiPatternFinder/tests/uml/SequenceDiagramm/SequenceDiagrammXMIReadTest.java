package uml.SequenceDiagramm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uml.XMI.read.ReadXMI;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

class SequenceDiagrammXMIReadTest {

	@Test
	void test() {
		var result = ReadXMI.readFile("./tests/sources/sampleTwoInteractions.uml");
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
		assertEquals(1, result.size());
		
		var currentObject = result.get(0);
		var currentChildren = ((SequenceDiagrammNode)currentObject).getChildren();
		assertEquals("Interaction2", currentObject.getName());
		
		currentObject = ((SequenceDiagrammNode) result.get(0)).getChildren().get(0);
		currentChildren = ((SequenceDiagrammNode) currentObject).getChildren();
		assertEquals("Lifeline1", currentObject.getName());
		assertEquals("Exec_Lifeline1_ex1_0", currentChildren.get(0).getName());
		assertEquals("Exec_Lifeline1_ex1_1", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0).getName());
		assertEquals("Nachricht_1", ((SequenceDiagrammNode)((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0)).getChildren().get(0).getName());
		assertEquals("Nachricht_1_return", ((SequenceDiagrammNode)((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0)).getChildren().get(1).getName());
		
		
		currentObject = ((SequenceDiagrammNode) result.get(0)).getChildren().get(1);
		currentChildren = ((SequenceDiagrammNode)currentObject).getChildren();
		assertEquals("Lifeline2", currentObject.getName());
		assertEquals("Exec_Lifeline2_ex1_0", currentChildren.get(0).getName());
		assertEquals("Nachricht_1", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0).getName());
		assertEquals("Nachricht_1_return", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(1).getName());
		
		currentObject = ((SequenceDiagrammNode) ((SequenceDiagrammNode) result.get(0)).getChildren().get(1)).getChildren().get(1);
		currentChildren = ((SequenceDiagrammNode) currentObject).getChildren();
		assertEquals("Exec_Lifeline2_ex2_0", currentObject.getName());
		assertEquals("Exec_Lifeline2_ex2_1", currentChildren.get(0).getName());
	}
	
	@Test
	void test_Pattern() {
		var result = ReadXMI.readFile("./tests/sources/samplePattern.uml");
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
		assertEquals(1, result.size());
		
		var currentObject = result.get(0);
		var currentChildren = ((SequenceDiagrammNode)currentObject).getChildren();
		assertEquals("Interaction2", currentObject.getName());
		
		currentObject = ((SequenceDiagrammNode) result.get(0)).getChildren().get(0);
		currentChildren = ((SequenceDiagrammNode) currentObject).getChildren();
		assertEquals("Lifeline1", currentObject.getName());
		assertEquals("Exec_Lifeline1_ex1_0", currentChildren.get(0).getName());
		assertEquals("Exec_Lifeline1_ex1_1", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0).getName());
		assertEquals("", ((SequenceDiagrammNode)((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0)).getChildren().get(0).getName());
		assertEquals("encryptDictionary", ((SequenceDiagrammNode)((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0)).getChildren().get(0).getDictionary().getName());
		assertEquals("Nachricht_1_return", ((SequenceDiagrammNode)((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0)).getChildren().get(1).getName());
		
		
		currentObject = ((SequenceDiagrammNode) result.get(0)).getChildren().get(1);
		currentChildren = ((SequenceDiagrammNode)currentObject).getChildren();
		assertEquals("Lifeline2", currentObject.getName());
		assertEquals("Exec_Lifeline2_ex1_0", currentChildren.get(0).getName());
		assertEquals("", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0).getName());
		assertEquals("encryptDictionary", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(0).getDictionary().getName());
		assertEquals("Nachricht_1_return", ((SequenceDiagrammNode) currentChildren.get(0)).getChildren().get(1).getName());
		
		currentObject = ((SequenceDiagrammNode) ((SequenceDiagrammNode) result.get(0)).getChildren().get(1)).getChildren().get(1);
		currentChildren = ((SequenceDiagrammNode) currentObject).getChildren();
		assertEquals("Exec_Lifeline2_ex2_0", currentObject.getName());
		assertEquals("Exec_Lifeline2_ex2_1", currentChildren.get(0).getName());
		
	}

}
