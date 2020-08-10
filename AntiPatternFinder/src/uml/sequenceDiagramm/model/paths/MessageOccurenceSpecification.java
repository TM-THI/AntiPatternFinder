package uml.sequenceDiagramm.model.paths;

/**
 * Klasse stellt eine MessageOccurenceSpecification in einem SequenceDiagramms dar.<br>
 * Wird nur beim verarbeiten von XMI-Dateien benötigt.<br>
 * Erweiterung von {@link SequenceDiagrammPath}
 *
 */
public class MessageOccurenceSpecification extends SequenceDiagrammPath{

	private String message;
	private String covered;
	
	public MessageOccurenceSpecification() {
		super(null, null);
		super.setObjectType("MessageOccurenceSpecification");
	}

	
	// Getters&Setters
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getCovered() {
		return covered;
	}
	public void setCovered(String covered) {
		this.covered = covered;
	}
	
	
}
