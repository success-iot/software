package attackTree.model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Formula implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Formula leftFormula;
	Formula rightFormula;
	Operator operator;
	
	public Formula(Formula leftFormula, Formula rightFormula, Operator operator) {
		super();
		this.leftFormula = leftFormula;
		this.rightFormula = rightFormula;
		this.operator = operator;
	}

	
	public String toString() {
		if(leftFormula == null && operator == null && rightFormula == null)
			return "";
		
		if(leftFormula == null && operator == null )
			return  rightFormula.toString();
	
		if(leftFormula == null  )
			return operator.toString() 
					+ " (" + rightFormula.toString() + ")";
		
		
		return  "(" + leftFormula.toString() + ") " 
				+ operator.toString() 
				+ " (" + rightFormula.toString() + ")";
	}
	
	public Formula getLeftFormula() {
		return leftFormula;
	}


	public void setLeftFormula(Formula leftFormula) {
		this.leftFormula = leftFormula;
	}


	public Formula getRightFormula() {
		return rightFormula;
	}


	public void setRightFormula(Formula rightFormula) {
		this.rightFormula = rightFormula;
	}


	public Operator getOperator() {
		return operator;
	}


	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public ArrayList<String> getDataVariablesList(){
		ArrayList<String> list = new ArrayList<String>();
		if(leftFormula != null){
			list = leftFormula.getDataVariablesList();
		}
		list.addAll(rightFormula.getDataVariablesList());
		return list;
	}


	public abstract boolean instantiate(String action, boolean value);
	
	

}
