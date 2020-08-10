package uml.SequenceDiagramm;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uml.UMLPatternHelper;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Frame;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.paths.Message;

class SequenceDiagrammPatternTest {

	@Test
	void test() {
		var diagramm = new SequenceDiagramm();
		var frame = new Frame("Frame", diagramm);
		
		var lifeline = new Lifeline("Main", frame);
		var execution = new ExecutionSpecification("main", lifeline);
		var execSend = new ExecutionSpecification("send", execution);
		
		var lifeline2 = new Lifeline("Crypto", frame);
		var encrypt = new ExecutionSpecification("veryGoodEncryptFunction", lifeline2);
		
		var message = new Message("HEllO", execSend, encrypt);
		var message2 = new Message("Back", encrypt, execSend);
		
		UMLPatternHelper.setAllChildrenToPattern(diagramm);
		
		assertEquals(2, execSend.getChildren().size());
		assertEquals(2, encrypt.getChildren().size());
		
		
		SequenceDiagrammTestHelper.transitAndPrint(diagramm, 0);
		
	}
}
