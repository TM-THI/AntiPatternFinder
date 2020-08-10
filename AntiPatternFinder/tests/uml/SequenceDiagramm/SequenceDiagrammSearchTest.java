package uml.SequenceDiagramm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uml.sequenceDiagramm.SearchSequenceDiagramm;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.NotOperator;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Frame;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.nodes.SequenceDiagrammNode;
import uml.sequenceDiagramm.model.paths.Message;

class SequenceDiagrammSearchTest {

	@Test
	void test() {
		// Diagramm
		var diagramm = new SequenceDiagramm();
		var frame = new Frame("Frame", diagramm);
		
		var lifeline = new Lifeline("Main", frame);
		var execution = new ExecutionSpecification("main", lifeline);
		var execSend = new ExecutionSpecification("send", execution);
		var execution1 = new ExecutionSpecification("main1", lifeline);
		var execSend1 = new ExecutionSpecification("send1", execution1);
		
		var lifeline2 = new Lifeline("Crypto", frame);
		var encrypt = new ExecutionSpecification("veryGoodEncryptFunction", lifeline2);
		
		var message = new Message("HEllO", execSend, encrypt);
		var message2 = new Message("Back", encrypt, execSend);
		
		var falseLifeline = new Lifeline("False", frame);
		var falseExec = new ExecutionSpecification("false1", falseLifeline);
		var falseExec2 = new ExecutionSpecification("false2", falseExec);
		var falseExec3 = new ExecutionSpecification("false3", falseExec2);
		var falseMessage = new Message("false", falseExec2, falseExec3);
		
		
		// Pattern
		var p_lifeline = new Lifeline("", null);
		p_lifeline.setToPattern();
		p_lifeline.setWildCardOperator(new WildCardOperator());
		
		var p_exec = new ExecutionSpecification("", p_lifeline);
		p_exec.setToPattern();
		p_exec.setWildCardOperator(new WildCardOperator());
		
		var p_receiver = new ExecutionSpecification("", null);
		p_receiver.setToPattern();
		p_receiver.setWildCardOperator(new WildCardOperator());
		var p_send = new Message("", p_exec,p_receiver);
		p_send.setToPattern();
		p_send.setContains(new Contains("HellO"));
		
		
		var result = SearchSequenceDiagramm.searchPattern(diagramm, p_lifeline);
		
		for(var r : result) {
			SequenceDiagrammTestHelper.transitAndPrint(r, 0);
		}
		
		
		assertEquals("Main", result.get(0).getName());
		assertEquals("main", ((SequenceDiagrammNode) result.get(0)).getChildren().get(0).getName());
		assertEquals("send", ((SequenceDiagrammNode) ((SequenceDiagrammNode) result.get(0)).getChildren().get(0)).getChildren().get(0).getName());
	
		assertEquals("Crypto", result.get(1).getName());
		assertEquals("veryGoodEncryptFunction", ((SequenceDiagrammNode) result.get(1)).getChildren().get(0).getName());
		assertEquals("HEllO", ((SequenceDiagrammNode) ((SequenceDiagrammNode) result.get(1)).getChildren().get(0)).getChildren().get(0).getName());
	}
	
	@Test
	void testNotOperator() {
		// Diagramm
		var diagramm = new SequenceDiagramm();
		var frame = new Frame("Frame", diagramm);
		
		var lifeline = new Lifeline("Main", frame);
		var execution = new ExecutionSpecification("main", lifeline);
		var execSend = new ExecutionSpecification("send", execution);
		var execution1 = new ExecutionSpecification("main1", lifeline);
		var execSend1 = new ExecutionSpecification("send1", execution1);
		
		var lifeline2 = new Lifeline("Crypto", frame);
		var encrypt = new ExecutionSpecification("veryGoodEncryptFunction", lifeline2);
		
		var message = new Message("HEllO", execSend, encrypt);
		var message2 = new Message("Back", encrypt, execSend);
		
		var falseLifeline = new Lifeline("False", frame);
		var falseExec = new ExecutionSpecification("false1", falseLifeline);
		var falseExec2 = new ExecutionSpecification("false2", falseExec);
		var falseExec3 = new ExecutionSpecification("false3", falseExec2);
		var falseMessage = new Message("false", falseExec2, falseExec3);
		
		
		// Pattern
		var p_lifeline = new Lifeline("", null);
		p_lifeline.setToPattern();
		p_lifeline.setWildCardOperator(new WildCardOperator());
		
		var p_exec = new ExecutionSpecification("main", p_lifeline);
		p_exec.setToPattern();
		p_exec.setNotOperator(new NotOperator());
		
		var p_receiver = new ExecutionSpecification("", null);
		p_receiver.setToPattern();
		p_receiver.setWildCardOperator(new WildCardOperator());
		var p_send = new Message("", p_exec,p_receiver);
		p_send.setToPattern();
		p_send.setContains(new Contains("HellO"));
		
		
		var result = SearchSequenceDiagramm.searchPattern(diagramm, p_lifeline);
		
		for(var r : result) {
			SequenceDiagrammTestHelper.transitAndPrint(r, 0);
		}
		
		assertFalse(result.isEmpty());
		assertEquals("Crypto", result.get(0).getName());
		assertEquals("veryGoodEncryptFunction", ((SequenceDiagrammNode) result.get(0)).getChildren().get(0).getName());
		assertEquals("HEllO", ((SequenceDiagrammNode) ((SequenceDiagrammNode) result.get(0)).getChildren().get(0)).getChildren().get(0).getName());
	}

}
