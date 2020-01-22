package exploration;

import java.util.ArrayList;
import java.util.Arrays;

import smc.SmcCore;

/**
 * Individuals of he population that evolves across generations of GA
 * @author mediouni
 *
 */
public class Individual {
	private static int count = 1;
	private int id;
	private double[] scheduler;
	private double cost;
	private double proba_success;
	private double fitness;
	private int rank;
	private double weight = 0.0005;
	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					 Constructor                   //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	public Individual(SmcCore smc, double[] scheduler, long cost_max) {
		smc.resetTest();
		this.scheduler = scheduler;
		smc.getAttacker().setScheduler(scheduler);
		double p = smc.run();
		proba_success = p;
		cost =(p!=0.0? smc.getMeanCost() : cost_max);
		id = count ++;
		fitness = computeFitness();
		rank = 0;
	}
	
	



/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					 	Set/Get                    //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

	

	public double getCost() {
		return cost;
	}
	
	public double getProbaSuccess() {
		return proba_success;
	}

	public void setCost(double score) {
		this.cost = score;
	}	
	
	public double[] getScheduler() {
		return scheduler;
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}


	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////						Methods                    //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////




	private double computeFitness() {
		return -weight * cost + (1 - weight) * proba_success;
//		return -(cost / proba_success);
	}
	
	public String toLongString() {
		String S = " ======== Individual ======== ";
		S+= "\nScheduler : "+Arrays.toString(this.scheduler);
		S+= "\nCost score : "+cost;
		return S;
	}
	
	public String toString() {
		return id+": ("+cost +","+ proba_success+","+ fitness+" , "+ rank +") -> "+Arrays.toString(this.scheduler);
	}




}
