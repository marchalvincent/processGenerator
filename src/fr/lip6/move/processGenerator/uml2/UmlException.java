package fr.lip6.move.processGenerator.uml2;

/**
 * Une exception lancée lors d'une éxecution UML.
 * 
 * @author Vincent
 * 
 */
public class UmlException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public UmlException(String error) {
		super(error);
	}
}
