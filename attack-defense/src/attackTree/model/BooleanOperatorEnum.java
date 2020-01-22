package attackTree.model;


public enum BooleanOperatorEnum {
	
	NOT,
	AND,
	OR;

	public String toString() {
		if(this.name().equalsIgnoreCase("NOT"))
			return "!";
		if(this.name().equalsIgnoreCase("AND"))
			return "&&";
		if(this.name().equalsIgnoreCase("OR"))
			return "||";
		return null;
	}
}
