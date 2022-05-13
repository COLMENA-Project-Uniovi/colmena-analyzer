package model;

public class ColmenaError {
	private String id;
	private String name;
	private String firstFamily;
	private String secondFamily;
	private String complement;

	public ColmenaError(String id, String name, String firstFamily,
			String secondFamily, String complement) {
		this.id = id;
		this.name = name;
		this.firstFamily = firstFamily;
		this.secondFamily = secondFamily;
		this.complement = complement;
	}

	public ColmenaError(String[] parts) {
		this.id = parts[0];
		this.name = parts[1];
		this.firstFamily = parts[2];
		this.secondFamily = parts[3];
		this.complement = parts[4];
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getFirstFamily() {
		return firstFamily;
	}

	public String getSecondFamily() {
		return secondFamily;
	}

	public String getComplement() {
		return complement;
	}

	@Override
	public String toString() {
		return "ColmenaError \n\t id :\t " + id + " \n\t name :\t "
				+ name + " \n\t firstFamily :\t " + firstFamily
				+ " \n\t secondFamily :\t " + secondFamily
				+ " \n\t complement :\t " + complement;
	}

	
}
