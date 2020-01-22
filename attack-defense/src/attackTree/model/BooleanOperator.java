package attackTree.model;



public class BooleanOperator extends Operator{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BooleanOperatorEnum opName;

	public BooleanOperator(BooleanOperatorEnum opName) {
		super();
		this.opName = opName;
	}

	public BooleanOperatorEnum getOpName() {
		return opName;
	}

	public void setOpName(BooleanOperatorEnum opName) {
		this.opName = opName;
	}

	public String toString() {
		return opName.toString();
	}

	
	public boolean isUnary() {
		switch (opName) {
			case AND: return false;
			case OR: return false;
			case NOT: return true;
			default: return true;
		}
		
	}

	
	public String getName() {
		switch (opName) {
		case AND: return "AND";
		case OR: return "OR";
		case NOT: return "NOT";
		default: return " ";
		}
	
	}
}
