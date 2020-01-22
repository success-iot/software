package attackTree.model;

import java.io.Serializable;

public abstract class Operator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract String toString();
	
	public abstract boolean isUnary();

	public abstract String getName();

}
