package uml.sequenceDiagramm.model.paths;

/**
 * Klasse stellt eine ExecutionOccurenceSpecification in einem SequenceDiagramms dar.<br>
 * Wird nur beim verarbeiten von XMI-Dateien benötigt.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class ExecutionOccurenceSpecification extends SequenceDiagrammPath{
	
	private String covered = "";
	private String execution = "";

	public ExecutionOccurenceSpecification() {
		super(null, null);
		super.setObjectType("ExecutionOccurenceSpecification");
	}

	public String getCovered() {
		return covered;
	}
	public void setCovered(String covered) {
		this.covered = covered;
	}

	public String getExecution() {
		return execution;
	}
	public void setExecution(String execution) {
		this.execution = execution;
	}
	
	
	

}
