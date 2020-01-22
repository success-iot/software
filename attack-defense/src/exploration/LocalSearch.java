package exploration;

import java.util.ArrayList;

import exploration.GeneticAlgorithm.ExplorationType;
import generator.TraceGenerator;
import smc.SmcCore;



/**
 * Heuristic local search to intensify the quality (score) of a learned model by exploring 
 * the epsilon parameter of IOALERGIA
 * @author mediouni
 *
 */
public class LocalSearch {
	private Individual solution;
	private SmcCore smc;
	
		
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	////////////				Constructor                        //////////
	/////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
		
	
	public LocalSearch(SmcCore smc, Individual inv) {
		this.solution = inv;
		this.smc = smc;
	}
	
	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					Set/Get                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

	public Individual getSolution() {
		return solution;
	}

	
	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					Methods                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	private double[] swap(double[] scheduler, int i, int j) {
		double d = scheduler[i];
		scheduler[i] = scheduler[j];
		scheduler[j] = d;
		return scheduler;
	}
	
	private ArrayList<Individual> getNeighbors1() {
		ArrayList<Individual> neighbors = new ArrayList<Individual>();		
		for(int i = 0; i < this.solution.getScheduler().length - 1; i++) 
		for(int j = i+1; j < this.solution.getScheduler().length; j++) {
			double[] scheduler = this.solution.getScheduler().clone();
			scheduler = swap(scheduler, i, j);
			neighbors.add(new Individual(smc, scheduler, smc.max_cost()));	
		}		
		return neighbors;
	}	
	
	private ArrayList<Individual> getNeighbors2() {
		ArrayList<Individual> neighbors = new ArrayList<Individual>();		
		for(int i = 0; i < this.solution.getScheduler().length; i++){
			if(this.solution.getScheduler()[i] != 0 && this.solution.getScheduler()[i] != 1) {
				double[] scheduler = this.solution.getScheduler().clone();
				scheduler = cancel(scheduler, i);
				neighbors.add(new Individual(smc, scheduler, smc.max_cost()));	
			}
		}		
		return neighbors;
	}
	
	private double[] cancel(double[] scheduler, int i) {
		scheduler[i] = 0;
		return normalize(scheduler);
	}
	
	private double[] normalize(double[] scheduler) {
		double[] normalized = new double[scheduler.length];
		double sum = 0.0;
		for(int i = 0 ; i < scheduler.length ; i ++ )
			sum += scheduler[i];
		double norm_sum = 0.0;
		for(int i = 0 ; i < scheduler.length ; i ++ )
		{
			normalized[i] = scheduler[i]/sum;
			norm_sum += normalized[i];
		}
		
		normalized[scheduler.length - 1] += 1 - norm_sum;
		
		return normalized;
	}

	

	public void exploreCost(int type) {
		boolean updated;
		do {

			updated = false;
			ArrayList<Individual> neighbors = getNeighbors(type);
			for (Individual individual : neighbors) {
//				System.out.println("\t"+individual);
				if(individual.getCost() < solution.getCost()) {
					updated = true;
//					System.out.println("\t"+individual);
					solution = individual;
				}
			}
		} while (updated);
	}
	
	public void exploreProba(int type) {
		boolean updated;
		do {

			updated = false;
			ArrayList<Individual> neighbors = getNeighbors(type);
			for (Individual individual : neighbors) {
//				System.out.println("\t"+individual);
				if(individual.getProbaSuccess() > solution.getProbaSuccess()) {
					updated = true;
//					System.out.println("\t"+individual);
					solution = individual;
				}
			}
		} while (updated);
	}
	
	public void exploreFitness(int type) {
		boolean updated;
		do {

			updated = false;
			ArrayList<Individual> neighbors = getNeighbors(type);
			for (Individual individual : neighbors) {
//				System.out.println("\t"+individual);
				if(individual.getFitness() > solution.getFitness()) {
					updated = true;
//					System.out.println("\t"+individual);
					solution = individual;
				}
			}
		} while (updated);
	}

	public void explore(ExplorationType exploType, int neighborhoodType) {
		switch(exploType) {
		case MONO_COST:
			exploreCost(neighborhoodType);
			break;
		case MONO_PROBA:
			exploreProba(neighborhoodType);
			break;
		case BI_OBJECTIVE:
			exploreFitness(neighborhoodType);
			break;
		}
	}

	private ArrayList<Individual> getNeighbors(int type) {
		switch(type) {
			case 1 : return getNeighbors1();
			case 2 : return getNeighbors2();
			default : return getNeighbors2();
		}
	}

}
