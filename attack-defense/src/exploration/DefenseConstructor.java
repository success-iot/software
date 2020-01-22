package exploration;

import java.util.Arrays;

import exploration.GeneticAlgorithm.ExplorationType;
import players.Defender;

public class DefenseConstructor {

	private Defender defender;
	private GeneticAlgorithm GA;
	
	
	
	
	
	public DefenseConstructor(GeneticAlgorithm gA) {
		super();
		GA = gA;
		this.defender = GA.getDefender();
		this.defender.enableDefenses();
		GA.setDefender(defender);
		
	}
	public Defender getDefender() {
		return defender;
	}
	public void setDefender(Defender defender) {
		this.defender = defender;
	}
	

	public GeneticAlgorithm getGA() {
		return GA;
	}
	public void setGA(GeneticAlgorithm gA) {
		GA = gA;
	}
	
	public void buildDefense(double epsilon) {
		boolean improved = false;
		double[] gain = new double[defender.getNbDefenses()];
		do {
			improved = false;
			reset(gain);
			System.out.println(this.defender);
			GA.explore(ExplorationType.BI_OBJECTIVE);
			double cost_with_def = GA.getBestIndividual(ExplorationType.BI_OBJECTIVE).getCost();
			System.out.println("Actual cost : "+cost_with_def);
			for (int i = 0; i< defender.getNbDefenses(); i++) 
			if(defender.getActivationStatus(i) == true){
				defender.disableDefense(i);
				GA.explore(ExplorationType.BI_OBJECTIVE);
				double cost_without_def = GA.getBestIndividual(ExplorationType.BI_OBJECTIVE).getCost();
				defender.enableDefense(i);
				gain[i] = (cost_with_def - cost_without_def) / cost_with_def;
			}
			System.out.println("Gains : "+Arrays.toString(gain));
			int j = getWorstGain(gain, epsilon);
			System.out.println("Chosen defense : "+j);
			if(j != -1) {
				defender.disableDefense(j);
				improved = true;
			}
			
		} while(improved);
		
	}
	private int getWorstGain(double[] gain, double epsilon) {
		int index = -1;
		double worst = Double.MAX_VALUE;
		for ( int i = 0; i < gain.length; i++) {
			if(gain[i]< epsilon && gain[i]< worst) {
				index = i;
				worst = gain[i];
			}
		}
		return index;
	}
	private void reset(double[] gain) {
		for ( int i = 0; i < gain.length; i++) {
			gain[i] = 1;
		}
	}
	
	
}
