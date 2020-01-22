package exploration;

import players.Defender;
import smc.SmcCore;

/**
 * Metaheuristic used to explore a single parameter epsilon
 * @author mediouni
 *
 */
public class GeneticAlgorithm {

	private Population population;
	private int nb_generation;
	private int population_size;
	private int intensificationType = 0;
	private double mutation_proba;
	private double ERE_ratio;
	private SmcCore smc;
	
	

	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////				Constructor                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	
	public GeneticAlgorithm(SmcCore smc, int nb_generation, int population_size, double mutation_proba, double ERE_ratio) {
		
		this(smc, nb_generation, population_size, 0, mutation_proba, ERE_ratio);
	}
	
	/**
	 * Constructor
	 * @param sampleGen a sample generator consisting of a model to learn and a scheduler 
	 * @param nb_generation number of generation of the GA
	 * @param population_size the number of individuals in the population
	 * @param mutation_proba probability to mutate a solution 
	 * @param ERE_ratio proportion of best individuals to keep
	 */
	public GeneticAlgorithm(SmcCore smc, int nb_generation, int population_size, int intensification,
			 double mutation_proba, double ERE_ratio) {
		this.smc = smc;
		this.population = null;
		this.nb_generation = nb_generation;
		this.population_size = population_size;
		this.intensificationType = intensification;

		this.mutation_proba = mutation_proba;
		this.ERE_ratio = ERE_ratio;
	}
	
	

	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////				    Set/Get                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	/**
	 * Start the genetic algorithm exploration for the defined number of generations 
	 * on population of defined size
	 */
	public Population getPopulation() {
		return this.population;
	}

	public Individual getBestIndividual(ExplorationType type) {
		if(type != ExplorationType.BI_OBJECTIVE) population.sortByScore();
		return population.getIndividual(0);
	}
		
	public void setDefender(Defender d) {
		smc.setDefender(d);
		smc.resetTest();
	}
	
	public Defender getDefender() {
		return smc.getDefender();
	}
	
	
	
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
////////////					Methods                        //////////
/////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////
	
	public void explore(ExplorationType type) {
		this.population = new Population(smc, population_size);
		switch (type) {
		case MONO_COST:
			exploreCost();
			break;
			
		case MONO_PROBA:
			exploreProba();
			break;
		case BI_OBJECTIVE:
			exploreBiobjective();
			break;

		default:
			break;
		}
	}
	
	
	private void exploreBiobjective() {
//		System.out.println("Initial population : "+ population);
		for (int i = 0; i < nb_generation; i++) {
			for(int j = 0; j < population_size/2; j++) {

				// Select pairs of individuals 
				double[] scheduler1 = population.selectRandomIndividual().getScheduler();
				double[] scheduler2 = population.selectRandomIndividual().getScheduler();
				
				// cross-over
				double[] scheduler3 = computeCrossOver(scheduler1, scheduler2);
				Individual individu = new Individual(smc, scheduler3, smc.max_cost());
				
				// intensification
				if(intensificationType != 0) {
//					System.out.println("Intensification "+j);
					LocalSearch LR = new LocalSearch(smc, individu);
					LR.explore(ExplorationType.BI_OBJECTIVE , intensificationType);
					individu = LR.getSolution();
//					System.out.println("Fin intensification "+j);
				}
				
				// mutation
				double p =Math.random();
				if(p < mutation_proba) {
					double[] scheduler4 = mutate(individu.getScheduler());
					individu = new Individual(smc, scheduler4, smc.max_cost());
				}
				
				population.add(individu);
			}
			
			//replacement
			population.computeRanking();
//			System.out.println("Before replacement : "+ population);
			
			population.ExtremeRankElitism(ExplorationType.BI_OBJECTIVE ,population_size, ERE_ratio);
			
//			System.out.println("\nGeneration "+ (i+1)+" : "+ population);
			System.out.print((i+1));
		}
//		System.out.println(population);
	}
	


	private void exploreProba() {
//		System.out.println("Initial population : "+ population);
		for (int i = 0; i < nb_generation; i++) {
			for(int j = 0; j < population_size/2; j++) {

				// Select pairs of individuals 
				double[] scheduler1 = population.selectRandomIndividual().getScheduler();
				double[] scheduler2 = population.selectRandomIndividual().getScheduler();
				
				// cross-over
				double[] scheduler3 = computeCrossOver(scheduler1, scheduler2);
				Individual individu = new Individual(smc, scheduler3, smc.max_cost());
				
				// intensification
				if(intensificationType != 0) {
//					System.out.println("Intensification "+j);
					LocalSearch LR = new LocalSearch(smc, individu);
					LR.explore(ExplorationType.MONO_PROBA , intensificationType);
					individu = LR.getSolution();
//					System.out.println("Fin intensification "+j);
				}
				
				// mutation
				double p =Math.random();
				if(p < mutation_proba) {
					double[] scheduler4 = mutate(individu.getScheduler());
					individu = new Individual(smc, scheduler4, smc.max_cost());
				}
				
				population.add(individu);
			}
			
			//replacement
			population.ExtremeRankElitism(ExplorationType.MONO_PROBA ,population_size, ERE_ratio);
			
			System.out.println("\nGeneration "+ (i+1)+" : "+ population);
//			System.out.print((i+1));
		}
	}
	

	private void exploreCost() {
		System.out.print("C");
//		System.out.println("Initial population : "+ population);
		for (int i = 0; i < nb_generation; i++) {
			for(int j = 0; j < population_size/2; j++) {

				// Select pairs of individuals 
				double[] scheduler1 = population.selectRandomIndividual().getScheduler();
				double[] scheduler2 = population.selectRandomIndividual().getScheduler();
				
				// cross-over
				double[] scheduler3 = computeCrossOver(scheduler1, scheduler2);
				Individual individu = new Individual(smc, scheduler3, smc.max_cost());
				
				// intensification
				if(intensificationType != 0) {
//					System.out.println("Intensification "+j);
					LocalSearch LR = new LocalSearch(smc, individu);
					LR.explore(ExplorationType.MONO_COST , intensificationType);
					individu = LR.getSolution();
//					System.out.println("Fin intensification "+j);
				}
				
				// mutation
				double p =Math.random();
				if(p < mutation_proba) {
					double[] scheduler4 = mutate(individu.getScheduler());
					individu = new Individual(smc, scheduler4, smc.max_cost());
				}
				
				population.add(individu);
			}
			
			//replacement
			population.ExtremeRankElitism(ExplorationType.MONO_COST ,population_size, ERE_ratio);
			
			
			System.out.print((i+1));
		}
		System.out.println(population);
	}




private double[] mutate(double[] scheduler) {
		
		int i = (int) Math.ceil(Math.random() * (scheduler.length-1 ));
		double p = Math.random();
		scheduler[i] = p;
		return normalize(scheduler);
	}




private double[] computeCrossOver(double[] scheduler1, double[] scheduler2) {
	int length = scheduler1.length;
	double[] scheduler = new double[length];
	
	int cross_point = length / 2;
	for (int i = 0; i < scheduler.length; i++) {
		if(i < cross_point) {
			scheduler[i] = scheduler1[i];
		}
		else {
			scheduler[i] = scheduler2[i];
		}
	}
	
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
	


	public enum ExplorationType{
	MONO_COST, MONO_PROBA, BI_OBJECTIVE	
	}
}



