package uml.SequenceDiagramm;


import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import uml.UMLObject;
import uml.UMLPatternHelper;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Frame;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.paths.Message;

class SequenceDiagrammEqualsTest {

	@Test
	void test() {
		var start = generateSequenceDiagramm();
		var pattern = generateSequenceDiagrammPattern();

		start.equals(pattern);
		
		assertTrue(start.equals(pattern));
		
	}
	
	
	
	// Helper
	private UMLObject generateSequenceDiagramm() {
		var diagramm = new SequenceDiagramm();
		var frame = new Frame("Frame", diagramm);

		var lifeline = new Lifeline("Main", frame);
		var execution = new ExecutionSpecification("main", lifeline);
		var execSend = new ExecutionSpecification("send", execution);

		var lifeline2 = new Lifeline("Crypto", frame);
		var encrypt = new ExecutionSpecification("veryGoodEncryptFunction", lifeline2);

		var message = new Message("HEllO", execSend, encrypt);
		var message2 = new Message("Back", encrypt, execSend);
		
		return frame;
	}
	
	private UMLObject generateSequenceDiagrammPattern() {
		var diagramm = new SequenceDiagramm();
		var frame = new Frame(diagramm);
		frame.setContains(new Contains("fRaMe"));

		var lifeline = new Lifeline("", frame);
		var execution = new ExecutionSpecification("", lifeline);

		var lifeline2 = new Lifeline("", frame);
		var encrypt = new ExecutionSpecification("", lifeline2);
		encrypt.setContains(new Contains("encrypt"));
		
		UMLPatternHelper.setAllChildrenToPattern(diagramm);
		
		return frame;
	}

}
