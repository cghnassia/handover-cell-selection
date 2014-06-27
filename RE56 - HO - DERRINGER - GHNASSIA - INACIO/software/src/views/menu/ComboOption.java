package views.menu;

public class ComboOption implements Comparable<ComboOption> {
	
	private int id;
	private String value;
	
	public ComboOption(int id, String value) {
		this.setId(id);
		this.setValue(value);
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		
		return this.value.toString();
	}
	
	@Override
	public int compareTo(ComboOption o) {
		return new Integer(this.id).compareTo(new Integer(o.getId()));
	}

}
