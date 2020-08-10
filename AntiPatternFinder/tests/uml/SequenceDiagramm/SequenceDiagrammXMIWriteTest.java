package uml.SequenceDiagramm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uml.XMI.read.ReadXMI;
import uml.XMI.write.WriteXMI;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.Message;
import uml.sequenceDiagramm.model.paths.SequenceDiagrammPath;

class SequenceDiagrammXMIWriteTest {

	@Test
	void test() {
		var read = ReadXMI.readFile("./tests/sources/sampleTwoInteractions.uml");
		
		WriteXMI.writeFile("./tests/output/test.uml", read.get(0));
		
		var result = ReadXMI.readFile("./tests/output/test.uml");
		
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
		var read = ReadXMI.readFile("./tests/sources/samplePattern.uml");
		
		WriteXMI.writeFile("./tests/output/testPattern.uml", read.get(0));
		
		var result = ReadXMI.readFile("./tests/output/testPattern.uml");

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
	
	@Test
	void test_pattern_write() {
		var p_sequence = new SequenceDiagramm();
		p_sequence.setToPattern();
		var p_lifeline = new Lifeline("", p_sequence);
		p_lifeline.setToPattern();
		p_lifeline.setWildCardOperator(new WildCardOperator());
		var p_exec = new ExecutionSpecification("", p_lifeline);
		p_exec.setToPattern();
		p_exec.setWildCardOperator(new WildCardOperator());
		
		var p_two_lifeline = new Lifeline("", p_sequence);
		p_two_lifeline.setToPattern();
		p_two_lifeline.setWildCardOperator(new WildCardOperator());
		var p_receiver = new ExecutionSpecification("", p_two_lifeline);
		p_receiver.setToPattern();
		p_receiver.setWildCardOperator(new WildCardOperator());
		var p_send = new Message("", p_exec,p_receiver);
		p_send.setToPattern();
		p_send.setContains(new Contains("HellO"));
		
		WriteXMI.writeFile("/Users/timomeilinger/Library/Mobile Documents/com~apple~CloudDocs/THI_Studium_Cloud/Master/3.Semester/Masterarbeit/Umsetzung/AntiPatternFinder/tests/output/testPattern_write.uml", p_sequence);
		
		
	}

}
