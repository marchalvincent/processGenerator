package fr.lip6.move.processGenerator.constraint;

/**
 * Cette exception est renvoyée lorsque le résultat d'une requête OCL est un boolean (typiquement lorsque l'utilisateur a défini
 * sa requête manuellement). Ce n'est pas vraiment une erreur, mais cette solution permet de garder l'architecture actuelle.
 * 
 * @author Vincent
 *
 */
public class OclBooleanValue extends Exception {
	
	private static final long serialVersionUID = 1L;
	private boolean value;
	
	public OclBooleanValue(Boolean b) {
		super();
		value = b.booleanValue();
	}
	
	public boolean getValue() {
		return value;
	}
}
