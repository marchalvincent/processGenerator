package fr.lip6.move.processGenerator;


public enum EQuantity {
	MORE,
	LESS,
	EQUAL,
	MORE_OR_EQUAL,
	LESS_OR_EQUAL;
	
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
}
