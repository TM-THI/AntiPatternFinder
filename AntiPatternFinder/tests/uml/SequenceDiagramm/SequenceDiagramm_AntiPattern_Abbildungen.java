package uml.SequenceDiagramm;


import org.junit.jupiter.api.Test;

import uml.UMLObject;
import uml.UMLPatternHelper;
import uml.XMI.read.ReadXMI;
import uml.sequenceDiagramm.SearchSequenceDiagramm;
import uml.sequenceDiagramm.SequenceDiagramm;
import uml.sequenceDiagramm.antiPattern.Contains;
import uml.sequenceDiagramm.antiPattern.Dictionary;
import uml.sequenceDiagramm.antiPattern.NotOperator;
import uml.sequenceDiagramm.antiPattern.WildCardOperator;
import uml.sequenceDiagramm.model.nodes.ExecutionSpecification;
import uml.sequenceDiagramm.model.nodes.Lifeline;
import uml.sequenceDiagramm.model.paths.Message;

class SequenceDiagramm_AntiPattern_Abbildungen {

	@Test
	void create_SequenceDiagramm() {
		
		var insecureDiagramm = helper_insecureDiagramm();
//		
//		var insecureDiagrammList = ReadXMI.readFile("./tests/sources/insecureDiagramm.uml");
//		var insecureDiagramm = insecureDiagrammList.get(0);
//		
//		var input = ReadXMI.readFile("./tests/sources/antiPattern/inputValidation.uml").get(0);
		
		var result = SearchSequenceDiagramm.searchPattern(insecureDiagramm, helper_inputValidator());
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
		result = SearchSequenceDiagramm.searchPattern(insecureDiagramm, helper_sendMessage());
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
		result = SearchSequenceDiagramm.searchPattern(insecureDiagramm, helper_checkRights());
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
		result = SearchSequenceDiagramm.searchPattern(insecureDiagramm, helper_insecureEncryption());
		
		for(var res : result) {
			SequenceDiagrammTestHelper.transitAndPrint(res, 0);
		}
		
	}
	
	public static UMLObject helper_insecureDiagramm() {
		var insecureDiagramm = new SequenceDiagramm();
		
		var main_lifeline = new Lifeline("Main", insecureDiagramm);
		var inputHelper_lifeline = new Lifeline("InputHelper", insecureDiagramm);
		var msgHandler_lifeline = new Lifeline("MessageHandler", insecureDiagramm);
		var cryptoHanlder_lifeline = new Lifeline("Crypto", insecureDiagramm);
		var backup_lifeline = new Lifeline("BackupPersonalData", insecureDiagramm);
		var storage_lifeline = new Lifeline("StorageHelper", insecureDiagramm);
		
		var asyncBackup = new ExecutionSpecification("async_BackupPersonalData", backup_lifeline);
		
		
		var main = new ExecutionSpecification("main", main_lifeline);
		var asyncBackup_msg = new Message("backupPersonalData",main, asyncBackup);
		var inputHelper_readInput = new ExecutionSpecification("readInput", inputHelper_lifeline);
		var main_msgReadInput = new Message("readInput", main, inputHelper_readInput);
		var msgReadResult = new Message("", inputHelper_readInput, main);
		var inputHelper_sendInput = new ExecutionSpecification("insertInputToDatabase", inputHelper_lifeline);
		var main_msgSendInput = new Message("insertInputToDatabase", main, inputHelper_sendInput);
		var msgSendResult = new Message("", inputHelper_sendInput, main);
		
		var createMsg = new ExecutionSpecification("createMessage", msgHandler_lifeline);
		var handleInput_createMsg = new Message("createMessage", main, createMsg);
		var createReturn = new Message("", createMsg, main);
		var sendMsg = new ExecutionSpecification("sendMessage", msgHandler_lifeline);
		var handleInput_sendMsg = new Message("sendMessage", main, sendMsg);
		var sendReturn = new Message("", sendMsg, main);
		
		
		var asyncBackup_readPersonal = new ExecutionSpecification("backupPersonalData", asyncBackup);
		var asyncBackup_readPersonal_encrypt = new ExecutionSpecification("readData", null);
		var asyncBackup_selfMessage = new Message("readPersonalData", asyncBackup_readPersonal, asyncBackup_readPersonal_encrypt);
		asyncBackup_readPersonal_encrypt.changeParent(asyncBackup_readPersonal);
		var crypto = new ExecutionSpecification("encrypt_DES", cryptoHanlder_lifeline);
		var cryptoMsg = new Message("encrypt_DES", asyncBackup_readPersonal_encrypt, crypto);
		var cryptoReply = new Message("replay_encryption", crypto, asyncBackup_readPersonal_encrypt);
		var store = new ExecutionSpecification("storeEncryptedData", storage_lifeline);
		var storeMsg = new Message("storeEncryptedData", asyncBackup_readPersonal_encrypt, store);
		
		return insecureDiagramm;
	}
	
	public static UMLObject helper_inputValidator() {
		var pattern = new SequenceDiagramm();
		var pattern_main_lifeline = new Lifeline("", pattern);
		var second_lifeline = new Lifeline("**", pattern);
		
		var pattern_main = new ExecutionSpecification("", pattern_main_lifeline);
		var readInput = new ExecutionSpecification("", second_lifeline);
		var readInputMsg = new Message(pattern_main, readInput);
		var validateInputTask = new ExecutionSpecification("", second_lifeline);
		var validateInput = new Message(pattern_main, validateInputTask);
		var workInputTask = new ExecutionSpecification("", second_lifeline);
		var workInput = new Message(pattern_main, workInputTask);
		
		UMLPatternHelper.setAllChildrenToPattern(pattern);
		
		pattern_main_lifeline.setWildCardOperator(new WildCardOperator());
//		second_lifeline.setWildCardOperator(new WildCardOperator());		// impliziet durch Name **
		
		pattern_main.setWildCardOperator(new WildCardOperator());
		readInput.setWildCardOperator(new WildCardOperator());
		validateInputTask.setWildCardOperator(new WildCardOperator());
		workInputTask.setWildCardOperator(new WildCardOperator());
		
		String[] names = {"read", "einlesen", "getInput"};
		var readInputDict = new Dictionary("readInput", names);
		readInputMsg.setContains(new Contains(readInputDict));
		
		String[] names2 = {"validate", "check"};
		var validateInputDict = new Dictionary("inputValidator", names2);
		validateInput.setContains(new Contains(validateInputDict));
		validateInput.setNotOperator(new NotOperator());
		
		String[] names3 = {"input"};
		var workInputDict = new Dictionary("workWithInput", names3);
		workInput.setContains(new Contains(workInputDict));
		
		return pattern;
	}
	
	
	public static UMLObject helper_sendMessage() {
		
		var insecureDiagramm = new SequenceDiagramm();
		
		var main_lifeline = new Lifeline("", insecureDiagramm);
		var second_lifeline = new Lifeline("**", insecureDiagramm);
		
		var main = new ExecutionSpecification("", main_lifeline);
		var encryptTask = new ExecutionSpecification("", second_lifeline);
		var encryptMsg = new Message(main, encryptTask);
		var sendMsgTask = new ExecutionSpecification("", second_lifeline);
		var sendMsg = new Message(main, sendMsgTask);
		
		UMLPatternHelper.setAllChildrenToPattern(insecureDiagramm);
		
		main_lifeline.setWildCardOperator(new WildCardOperator());
//		second_lifeline.setWildCardOperator(new WildCardOperator());		// impliziet durch Name **
		
		main.setWildCardOperator(new WildCardOperator());
		encryptTask.setWildCardOperator(new WildCardOperator());
		sendMsgTask.setWildCardOperator(new WildCardOperator());
		
		
		String[] names = {"encrypt"};
		var encryptDict = new Dictionary("encryptMessage", names);
		encryptMsg.setContains(new Contains(encryptDict));
		encryptMsg.setNotOperator(new NotOperator());
		
		String[] names2 = {"send", "senden", "schicken", "kommunizieren", "communicate"};
		var sendMessage = new Dictionary("sendMessage", names2);
		sendMsg.setContains(new Contains(sendMessage));
		
		return insecureDiagramm;
	}
	
	public static UMLObject helper_checkRights() {
		var insecureDiagramm = new SequenceDiagramm();
		
		var main_lifeline = new Lifeline("", insecureDiagramm);
		var second_lifeline = new Lifeline("**", insecureDiagramm);
		
		var main = new ExecutionSpecification("", main_lifeline);
		var checkTask = new ExecutionSpecification("", second_lifeline);
		var checkMsg = new Message(main, checkTask);
		var criticalTask = new ExecutionSpecification("", second_lifeline);
		var criticalMsg = new Message(main, criticalTask);
		
		UMLPatternHelper.setAllChildrenToPattern(insecureDiagramm);
		
		main_lifeline.setWildCardOperator(new WildCardOperator());
//		second_lifeline.setWildCardOperator(new WildCardOperator());		// impliziet durch Name **
		
		main.setWildCardOperator(new WildCardOperator());
		checkTask.setWildCardOperator(new WildCardOperator());
		criticalTask.setWildCardOperator(new WildCardOperator());
		
		
		String[] names = {"checkRight", "right", "hasRight"};
		var checkDict = new Dictionary("checkRights", names);
		checkMsg.setContains(new Contains(checkDict));
		checkMsg.setNotOperator(new NotOperator());
		
		String[] names2 = {"readpersonaldata", "private", "user"};
		var criticalDict = new Dictionary("criticalAction", names2);
		criticalMsg.setContains(new Contains(criticalDict));
		
		return insecureDiagramm;
	}
	
	public static UMLObject helper_insecureEncryption() {
		var insecureDiagramm = new SequenceDiagramm();
		
		var main_lifeline = new Lifeline("", insecureDiagramm);
		var second_lifeline = new Lifeline("**", insecureDiagramm);
		
		var main = new ExecutionSpecification("", main_lifeline);
		var task = new ExecutionSpecification("", second_lifeline);
		var insecureMsg = new Message(main, task);
		
		UMLPatternHelper.setAllChildrenToPattern(insecureDiagramm);
		
		main_lifeline.setWildCardOperator(new WildCardOperator());
//		second_lifeline.setWildCardOperator(new WildCardOperator());		// impliziet durch Name **
		
		main.setWildCardOperator(new WildCardOperator());
		task.setWildCardOperator(new WildCardOperator());
		
		
		String[] names = {"DES", "PGP"};
		var insecureDict = new Dictionary("insecureEncryption", names);
		insecureMsg.setContains(new Contains(insecureDict));
		return insecureDiagramm;
	}
}
