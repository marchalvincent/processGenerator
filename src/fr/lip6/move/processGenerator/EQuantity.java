package fr.lip6.move.processGenerator;


public enum EQuantity {
	MORE(0),
	LESS(1),
	EQUAL(2),
	MORE_OR_EQUAL(3),
	LESS_OR_EQUAL(4);
	
	public static EQuantity getQuantityByString(String name) {
		
		switch (name.toUpperCase()) {
			case "MORE":
				return MORE;
			case "LESS":
				return LESS;
			case "EQUAL":
				return EQUAL;
			case "MORE_OR_EQUAL":
				return MORE_OR_EQUAL;
			case "LESS_OR_EQUAL":
				return LESS_OR_EQUAL;
			default:
				return null;
		}
	}
	
	/**
	 * Cette variable va servir à stocker les informations utilisateurs dans un fichier de configuration.
	 */
	private int position;
	
	private EQuantity(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}
}
