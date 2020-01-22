package attackTree.model;


public class BooleanFormula extends Formula {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BooleanFormula(Formula leftFormula, Formula rightFormula, Operator operator) {
		super(leftFormula, rightFormula, operator);
		
		
	}

	
	@Override
	public String toString() {
		return  super.toString() ;
	}

	public boolean isTerminal() {
		if(operator == null)
			if(((StateFormula) rightFormula).getStateFormula().equals("#True") || ((StateFormula) rightFormula).getStateFormula().equals("#False"))
				return true;
		return false;
	}
	
	public boolean instantiate(String action, boolean value) {
		boolean found = false;
		{
			if(this.leftFormula != null) {
				found |= leftFormula.instantiate(action, value);
			}
			found |= rightFormula.instantiate(action, value);
		}
		
		return found;
	}


	
}
