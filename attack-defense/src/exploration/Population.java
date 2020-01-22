package exploration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import exploration.GeneticAlgorithm.ExplorationType;
import generator.TraceGenerator;
import players.Attacker;
import smc.SmcCore;



/**
 * Bunch of individuals used in a Genetic algorithm
 * @author mediouni
 *
 */
public class Population {
	
	private ArrayList<Individual> individuals;
	private int populationSize ;

	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////				Constructor                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	

	public Population(SmcCore smc, int size) {
		this.populationSize = size;
		individuals = new ArrayList<Individual>();
		for (int i = 0; i < size; i++) {
			double[] scheduler = smc.getAttacker().generateScheduler();
			individuals.add(new Individual(smc, scheduler, smc.max_cost()));
//			System.out.println("Generated individual "+i);
		}
	}

	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					Set/Get                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

	public void add(Individual individu) {
		individuals.add(individu);
	}

	public Individual getIndividual(int i) {
		return this.individuals.get(i);
	}

	
	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					Methods                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////

	public Individual selectRandomIndividual() {
		int index = (int) Math.round(Math.random() * (populationSize-1));
		return individuals.get(index);
	}

	/**
	 * Replacement method that keeps a given number of individuals by first choosing a percentage
	 * of the best ones then completing with the worst individuals
	 * @param biObjective 
	 * @param population_size number of individuals to keep in the population
	 * @param EreRatio percentage of the best individuals
	 */
	public void ExtremeRankElitism(ExplorationType exploType, int population_size, double EreRatio) {
		int nb_best = (int) Math.round(population_size*EreRatio);
		switch(exploType) {
		case MONO_COST: 
			sortByScore();
			break;
		case MONO_PROBA: 
			sortByProba();
			break;

		case BI_OBJECTIVE: 
			sortByRank();
			
			break;
		}
		
		ArrayList<Individual> selected = new ArrayList<Individual>();
		for (int i = 0; i < nb_best; i++) {
			selected.add(individuals.get(i));
		}
		
		for (int i = 0; i < population_size - nb_best; i++) {
			selected.add(individuals.get(individuals.size() - 1 - i));
		}
		individuals = selected;
		
		if(individuals.size() != population_size)
			System.err.println("Error in ERE selection");
		
	}
	
	public void sortByScore() {
		Collections.sort(individuals,new IndividualCompareCost());
	}

	public void sortByProba() {
		Collections.sort(individuals,new IndividualCompareProba());
	}

	public void sortByRank() {
		Collections.sort(individuals,new IndividualCompareRank());
	}

	public String toString() {
		String S = " ===== Population ===== ";
		for (Individual individual : individuals) {
			S += "\n\t "+individual;
		}
		return S;
	}


	public void computeRanking() {
		resetRanks();
		
		ArrayList<Integer> indivIndex = new ArrayList<Integer>();
		for (int i = 0; i < individuals.size(); i++) {
			indivIndex.add(i);
		}
		int rank = 1;
		while(!indivIndex.isEmpty()) {
			indivIndex = detectNonDominated(indivIndex, rank);
			rank ++;
		}
		
	}



	private ArrayList<Integer> detectNonDominated(ArrayList<Integer> indivIndex, int rank) {
		ArrayList<Integer> toRemove = new ArrayList<Integer>();
		for (Integer i : indivIndex) {
			if(isNonDominated(indivIndex, i)) {
				individuals.get(i).setRank(rank);
				toRemove.add(i);
			}
		}
		indivIndex.removeAll(toRemove);
		return indivIndex;
	}


	


	private boolean isNonDominated(ArrayList<Integer> indivIndex, Integer currentIndividual) {
		for (Integer i : indivIndex) {
			if(i != currentIndividual)
				if(dominates(i, currentIndividual))
					return false;
		}
		return true;
	}


	private boolean dominates(Integer i, Integer currentIndividual) {
		Individual ind1 = individuals.get(i);	
		Individual ind2 = individuals.get(currentIndividual);
		if(ind1.getCost() == ind2.getCost() && ind1.getProbaSuccess() == ind2.getProbaSuccess()) return false;
		if(ind1.getCost() == ind2.getCost() && ind1.getProbaSuccess() >= ind2.getProbaSuccess()) return true;
		if(ind1.getCost() <= ind2.getCost() && ind1.getProbaSuccess() == ind2.getProbaSuccess()) return true;
		if(ind1.getCost() <= ind2.getCost() && ind1.getProbaSuccess() >= ind2.getProbaSuccess()) return true;
		return false;
	}


	private void resetRanks() {
		for (Individual individual : individuals) {
			individual.setRank(0);
		}
	}




}
