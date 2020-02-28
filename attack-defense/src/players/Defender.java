package players;

import java.util.Arrays;

public class Defender {
	private String[] actions;
	private boolean[] defenses;
	
	private Defender(String[] actions) {
		super();
		this.actions = actions;
		this.defenses = generateDefense(actions.length);
	}

	public Defender(String[] actions, boolean[] defenses) {
		this.actions = actions;
		this.defenses = defenses;
	}

	private boolean[] generateDefense(int length) {
		boolean[] def = new boolean[length];
		for (int i = 0; i < def.length; i++) {
			double p = Math.random();
			def[i] = (p<0.5? true:false);
		}
		return def;
	}

	public int alphabetSize() {
		return actions.length;
	}

	public boolean getActivationStatus(int i) {
		return defenses[i];
	}

	public String getAction(int i) {
		return actions[i];
	}

	public void disableDefenses() {
		for (int i = 0; i < defenses.length; i++) {
			defenses[i] = false;
		}
	}
	
	public void enableDefense(int i) {
		this.defenses[i] = true;
	}

	public int getNbDefenses() {
		return this.defenses.length;
	}

	public void disableDefense(int i) {
		this.defenses[i] = false;
	}

	public String toString() {
		String S = "Defender: \n";
		S += Arrays.toString(actions)+"\n";
		S += Arrays.toString(defenses);
		return S;
	}

	public void enableDefenses() {
		for (int i = 0; i < defenses.length; i++) {
			defenses[i] = true;
		}
	}
}
