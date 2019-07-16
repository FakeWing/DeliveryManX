package DeliverymanX;

public class Option {

	private String choice;
	private String id;
	private String text;

	public Option(String choice, String id, String text) {
		this.choice = choice;
		this.id = id;
		this.text = text;
	}

	public String getChoice() {
		return choice;
	}

	public void setChoice(String choice) {
		this.choice = choice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
