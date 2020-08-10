package uml.SequenceDiagramm;


import org.junit.jupiter.api.Test;

import uml.UMLPatternHelper;
import uml.XMI.write.WriteXMI;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.antiPattern.NotOperator;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.paths.Message;

class SequenceDiagrammCreateDiagramms {

	@Test
	void create_SequenceDiagramm() {
		
		var insecureDiagramm = SequenceDiagramm_AntiPattern_Abbildungen.helper_insecureDiagramm();
		
		WriteXMI.writeFile("./tests/output/insecureDiagramm.uml", insecureDiagramm);
		
	}
	
	@Test
	void create_AntiPattern_encryptedMessages() {
		
		var insecureDiagramm = SequenceDiagramm_AntiPattern_Abbildungen.helper_sendMessage();
		
		WriteXMI.writeFile("./tests/output/encryptedCommunication.uml", insecureDiagramm);
		
	}
	
	@Test
	void create_AntiPattern_inputValidation() {
		
		var insecureDiagramm = SequenceDiagramm_AntiPattern_Abbildungen.helper_inputValidator();
		
		WriteXMI.writeFile("./tests/output/inputValidation.uml", insecureDiagramm);
		
	}
	
	@Test
	void create_AntiPattern_checkRights() {
		
		var insecureDiagramm = SequenceDiagramm_AntiPattern_Abbildungen.helper_checkRights();
		
		WriteXMI.writeFile("./tests/output/checkRights.uml", insecureDiagramm);
		
	}
	
	@Test
	void create_AntiPattern_insecureEncryption() {
		
		var insecureDiagramm = SequenceDiagramm_AntiPattern_Abbildungen.helper_insecureEncryption();
		
		WriteXMI.writeFile("./tests/output/insecureEncryption.uml", insecureDiagramm);
		
	}
	
}
