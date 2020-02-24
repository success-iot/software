package main;

import java.util.ArrayList;

import attackTree.model.BooleanFormula;
import attackTree.model.BooleanOperator;
import attackTree.model.BooleanOperatorEnum;
import attackTree.model.StateFormula;
import attackTree.monitor.Monitor;
import attackTree.parser.BooleanParser;
import exploration.DefenseConstructor;
import exploration.GeneticAlgorithm;
import exploration.GeneticAlgorithm.ExplorationType;
import exploration.Individual;
import exploration.LocalSearch;
import exploration.Population;
import generator.Trace;
import generator.TraceGenerator;
import players.Attacker;
import players.Defender;
import players.Environment;
import smc.PEstimation;
import smc.SmcCore;

public class Main {
	
    private  String tree;
    private  long max_cost;
    private  Attacker A;
    private  Defender D;
    private  Environment env;
    private  long max_time;

	

    private  void testSMC() {
	// Instantiate attack tree
		
	tree = "is && bs && (!z)";
		
	// BooleanParser parser = new BooleanParser();		
	// BooleanFormula tree = parser.parse(m.tree);
	// System.out.println(tree);
				
	// Instantiate Attacker
	String[] attackActions = {"is","bs"};
	long[] costFunction = {80,100};

	double[] lower = {0, 0};
	double[] upper = {20, 20};
	A = new Attacker(costFunction, attackActions, lower, upper);
	
	// Instantiate Defender
	String[] defenseActions = {"z"};
	boolean[] defenses = {false};
	D = new Defender(defenseActions, defenses);
 	
	// Instantiate environment
	double[] probas = {0.8, 0.7};
	env = new Environment(probas, attackActions);
 	
	// Instantiate max cost/time
	max_cost = 50000;
	max_time = 300;
	double pr = 0.5;
 	
	// Generate SMC Test
	double[] scheduler = {pr,(1-pr)};
	A.setScheduler(scheduler);
	TraceGenerator TGen = new TraceGenerator(A,D,env, tree);
	// 			 	System.out.println(TGen.generateTrace());	
	PEstimation algo = new PEstimation(0.05, 0.001);
	SmcCore smc = new SmcCore(TGen, algo, max_cost, max_time);
	double p = smc.run();
	System.out.println("Cost : "+smc.getMeanCost());
	System.out.println("SMC verdict : "+p); 		
    }
    
    private  void testLS() {

	// Generate SMC Test
	TraceGenerator TGen = new TraceGenerator(A,D,env, tree);
	
	PEstimation algo = new PEstimation(0.05, 0.001);
	SmcCore smc = new SmcCore(TGen, algo, max_cost, max_time);
 	
	double[] scheduler = {0.5, 0.3, 0.2};
	Individual individu = new Individual(smc, scheduler, max_cost);
	System.out.println(individu);
	LocalSearch LS = new LocalSearch(smc, individu);
	LS.explore(ExplorationType.MONO_COST, 2);
	System.out.println(LS.getSolution());
    }
    
	
    private Individual testGA(ExplorationType type, int intensificationType) {
	
	// Generate SMC Test
	TraceGenerator TGen = new TraceGenerator(A,D,env, tree);
	 		
	PEstimation algo = new PEstimation(0.1, 0.1);
	SmcCore smc = new SmcCore(TGen, algo, max_cost, max_time);
 		
 		
 		
	GeneticAlgorithm GA = new GeneticAlgorithm(smc, 3, 10, intensificationType, 0.2, 0.8);
	GA.explore(type);
	return GA.getBestIndividual(type);
	// System.out.println("\t"+(System.currentTimeMillis() - begin)+"\t" + GA.getBestIndividual().getScore()+"\t" + GA.getBestIndividual().getProbaSuccess());
	// A.setScheduler(GA.getBestIndividual().getScheduler());
	// 		
	// double p = 0;
	// for(int i = 0; i< 10 ; i++) {
	// 	smc.resetTest();
	// 	smc.run();
	// 	p += smc.getMeanCost();
	// }
	// p = p/10;
	
    }
	
	
    private void testGAwithStatistics() {
	int repetitions = 25;
	double[] costs = new double[repetitions];
	double[] proba_succ = new double[repetitions];
	double sum_cost = 0;
	double sum_proba = 0;
	long sum_time = 0;
	for(int i = 0; i< repetitions; i++)
	    {
		System.out.print("it("+i+"):\t");
		long begin = System.currentTimeMillis();
		Individual solution = testGA(ExplorationType.BI_OBJECTIVE, 2);
		System.out.println(" ->"+solution);
		costs[i] = solution.getCost();
		proba_succ[i] = solution.getProbaSuccess();
		sum_cost += solution.getCost();
		sum_proba += solution.getProbaSuccess();
		sum_time += (System.currentTimeMillis() - begin);
	    }

	System.out.println("Mean cost : "+(sum_cost/repetitions));
	System.out.println("Cost deviation : "+ standardDeviation(costs, (sum_cost/repetitions)));
	System.out.println("Mean proba : "+(sum_proba/repetitions));
	System.out.println("Proba deviation : "+ standardDeviation(proba_succ, (sum_proba/repetitions)));
	System.out.println("Mean time : "+(sum_time/repetitions));
    }
	
    private void testDefenseConstructor() {
	long t = System.currentTimeMillis();
	TraceGenerator TGen = new TraceGenerator(A,D,env, tree); 	
	//		System.out.println(TGen);
	PEstimation algo = new PEstimation(0.1, 0.1);
	SmcCore smc = new SmcCore(TGen, algo, max_cost, max_time);
	GeneticAlgorithm GA = new GeneticAlgorithm(smc, 3, 10, 2, 0.2, 0.8);

	// System.out.println(GA.getDefender());
		
	DefenseConstructor defConst = new DefenseConstructor(GA);
	defConst.buildDefense(0.05);
	System.out.println("\n" + defConst.getDefender());
 		
	System.out.println("\nExecution time : "+ (System.currentTimeMillis() - t) + "ms");
    }
	
    /*
    public void BGP() {
	// Attack Defense Tree from FORMATS paper
	tree = "( ( ( sm || no || op || ka   ) && (sa && (!au)) ) && (!rn)  ) || ( ar && (!sr))";
	// Instantiate Attacker
	String[] attackActions = {" sm", "no", "op", "ka", "sa", "ar"};
	long[] costFunction = {50,60,70,100,150,190};
	
	double[] lower = {0, 0, 0, 0, 0, 0};
	double[] upper = {20, 20, 20, 20, 20, 20};
	A = new Attacker(costFunction, attackActions, lower, upper);
	    
	// Instantiate Defender
	String[] defenseActions = {"au","rn","sr"};
	boolean[] defenses = {false, false, false};
	D = new Defender(defenseActions, defenses);
	    
	// Instantiate environment
	// double[] probas = {0.056, 0.07, 0.105, 0.14, 0.042, 0.26};
	double[] probas = {0.7, 0.7, 0.7, 0.7, 0.42, 0.65};
	env = new Environment(probas, attackActions);
		 		
	// Instantiate max cost/time
	max_cost = 50000;
	max_time = 400;
    }
	
	
    public void orga() {
	// Attack Defense Tree from FORMATS paper
	tree = "(is && bs && ((!t1) || (!tf) )) || (t) || ( (!t2) && ( (st) || ( im && ot && ( ba || (!at)) ) )) || (b)";
	// Instantiate Attacker
	String[] attackActions = {"is","bs","t","b","st","ba","im","ot"};
	long[] costFunction = {80,100,700,700,50,85,70,0};
	    
	double[] lower = {0, 0, 0, 0, 0, 0, 0, 0};
	double[] upper = {20, 20, 20, 20, 20, 20, 20, 20};
	A = new Attacker(costFunction, attackActions, lower, upper);
	    
	// Instantiate Defender
	String[] defenseActions = {"t1","t2","tf","at"};
	boolean[] defenses = {true, false, true, true};
	D = new Defender(defenseActions, defenses);
	    
	// Instantiate environment
	double[] probas = {0.8, 0.7, 0.7, 0.7, 0.5, 0.6, 0.5, 0.6};
	env = new Environment(probas, attackActions);
	    
	// Instantiate max cost/time
	max_cost = 50000;
	max_time = 300;
    }
	
    public void SCADA() {
	// Attack Defense Tree from FORMATS paper
	tree = "(	((  (s1 && s2)  || (s1 && s3) || (s2 && s3)) || (wse))	||	(ulan)	||	(	( ( (hmi)&& (!sw)) && (scopf) )	||	((g1 && (!rst1)) && ((g2 && (!rst2)) && (g3 && (!rst3)) )))	)	"
	    + "||"
	    + "	(db || uwan ||ws)";
	// Instantiate Attacker
	String[] attackActions = {"s1", "s2", "s3", "wse", "ulan", "hmi", "scopf", "g1", "g2", "g3", "db", "uwan", "ws"};
	long[] costFunction = {100, 110, 90, 250, 275, 100, 120, 100, 30, 40, 170, 160, 150};
	
	double[] lower = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	double[] upper = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
	A = new Attacker(costFunction, attackActions, lower, upper);
	
	// Instantiate Defender
	String[] defenseActions = {"sw","rst1","rst2","rst3"};
	boolean[] defenses = {false, false, false, false};
	D = new Defender(defenseActions, defenses);
	
	// Instantiate environment
	double[] probas = {0.1, 0.1, 0.1, 0.25, 0.3, 0.15, 0.15, 0.09, 0.15, 0.08, 0.5, 0.35, 0.4};
	// double[] probas = {1, 1, 1, 1, 1, 0.25, 1, .4, .5, .6, 1, 1,1 };
	env = new Environment(probas, attackActions);
	
	// Instantiate max cost/time
	max_cost = 50000;
	max_time = 300;
    }
    
    public void MI() {
	// Attack Defense Tree from FORMATS paper
	tree = "(	(uar	||	(lv && (!dva)))	) "
	    + "||	(( ela || ewa )	||	(dbf || dbg || dbw)	||	(oc)	||	(cmf || cmc || cmu))"
	    + "||	(mu && vop)"
	    + "||	((pc) || ( (sn || rt) && (!tpt)) || (sme))";
	// Instantiate Attacker
	String[] attackActions = {"uar", "lv", "ela", "ewa", "dbf", "dbg", "dbw", "oc", "cmf", "cmc", "cmu", "mu", "vop", "pc", "sn", "rt", "sme"};
	long[] costFunction = {50, 60, 70, 100, 150, 190, 100, 110, 90, 250, 275, 100, 120, 100, 30, 40, 170};
	
	double[] lower = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	double[] upper = {20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20};
	A = new Attacker(costFunction, attackActions, lower, upper);
	
	// Instantiate Defender
	String[] defenseActions = {"dva","tpt"};
	boolean[] defenses = { true, true};
	D = new Defender(defenseActions, defenses);
	
	// Instantiate environment
	double[] probas = {0.08, 0.07, 0.15, .2, .1, .4, .1, .1, .1, .25, .3, .2, .15, .15, 0.18, 0.12, .5};
	//				double[] probas = {1, 0.7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.6, 0.6, 1};
	env = new Environment(probas, attackActions);
	
	// Instantiate max cost/time
	max_cost = 50000;
	max_time = 300;
    }
    */
    
    public void load(String filename) {
	AttackDefenseReader ar = new AttackDefenseReader(filename);
	tree = ar.getTree();
	A = new Attacker(ar.getAttackCosts(), ar.getAttackActions(),
			 ar.getAttackLowerBounds(), ar.getAttackUpperBounds());
	D = new Defender(ar.getDefenseActions(), ar.getDefenseStatuses());
	env = new Environment(ar.getAttackProbas(), ar.getAttackActions());
	max_cost = ar.getMaxCost();
	max_time = ar.getMaxTime();
    }
    
    public static void main(String[] args) {
	Main m = new Main();
	// m.testSMC();
		
	// Benchmark
	// m.orga();
	// m.BGP();
	// m.SCADA();
	// m.MI();
		
		
	// // Attack Defense Tree from Axel files
	// m.tree = "(is && bs && ((!t1) || (!tf)) )"
	//		+ "||t"
	//		+ "||b"
	//		+ "||(st && ((!at) || ba) )"
	//		+ "||(im && ot && (!t2))";
		
		
	// BooleanParser parser = new BooleanParser();		
	// BooleanFormula tree = parser.parse(m.tree);
	// System.out.println(tree);

	if (args.length != 1) {
	    System.out.println("usage");
	    System.exit(-1);
	}
	
	m.load( args[0] );
		
	// Monitor monitor = new Monitor();		
	BooleanParser parser = new BooleanParser();
	BooleanFormula tree = parser.parse(m.tree);
	// monitor.setDefenses(tree, m.D);
	// tree = monitor.simplify(tree);
	System.out.println("attack-defense tree\n" + tree);
	// 		
	// TraceGenerator TGen = new TraceGenerator(m.A,m.D, m.env, m.tree);
	// double[] scheduler = {0,0,1,0,0,0,0};
	// m.A.setScheduler(scheduler);
	// 		 
	// PEstimation algo = new PEstimation(0.1, 0.1);
	// SmcCore smc = new SmcCore(TGen, algo, m.max_cost, m.max_time);
	// 		
	// 		
	// double p = smc.run();
	// System.out.println("Cost : "+smc.getMeanCost());
	// System.out.println("SMC verdict : "+p); 
 		
	// m.testLS(); 		
	m.testDefenseConstructor();
	// m.testGAwithStatistics();
 		
    }		
    
    private double standardDeviation(double[]  costs, double mean) {
	double d = 0.0;
	for (double l : costs) {
	    d += Math.pow(mean - l, 2);
	}	
			
	return Math.sqrt(d / costs.length);
    }

}
