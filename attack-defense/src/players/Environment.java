package players;

public class Environment {
	private double[] success_probabilities;
	private String[] actions;
	
	public Environment(double[] success_probabilities, String[] actions) {
		this.success_probabilities = success_probabilities;
		this.actions = actions;
	}
	
	public boolean evaluateSuccess(String action) {
		int i = findAction(action);
		double p = Math.random();
		try {
			return (p<=success_probabilities[i] ? true: false);
		}
		catch(Exception e){
			System.err.println(action);
			return false;
		}
	}

	private int findAction(String action) {
		for (int j = 0; j < actions.length; j++) {
			if(actions[j].equals(action))
				return j;
		}
		return -1;
	}
	
	
}
