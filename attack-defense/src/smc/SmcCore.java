package smc;

import java.util.ArrayList;

import generator.Trace;
import generator.TraceGenerator;
import players.Attacker;
import players.Defender;

public class SmcCore {
	
	private TraceGenerator traceGen;
	private PEstimation smc;
	private long max_cost;
	private long max_time;
	private double mean_cost;
	
	

	public long max_cost() {
		return max_cost;
	}
	
	
	public SmcCore(TraceGenerator traceGen, PEstimation smc, long max_cost, long max_time) {
		super();
		this.traceGen = traceGen;
		this.smc = smc;
		this.max_cost = max_cost;
		this.max_time = max_time;
		this.mean_cost = 0.0;
	}
	
	public void setDefender(Defender d) {
		traceGen.setDefender(d);
	}
	
	public Defender getDefender() {
		return traceGen.getDefender();
	}
	public double run() {
		ArrayList<Long> costs = new ArrayList<Long>();
 		while(!smc.hasConcluded()) {
// 	 		System.out.println((++cpt) + "/" + smc.getN());
 			Trace trace = traceGen.generateTrace(max_cost, max_time);
// 			System.out.println(trace);
// 			if(trace.getVerdict()) 
 				costs.add(traceGen.getAttacker().getCost());
 			smc.runStep(trace.getVerdict());
 		}
// 		System.out.println(printStats(costs));
 		mean_cost = mean(costs);
// 		System.out.println("Mean :::::::::::::: "+mean_cost);
 		return smc.getVerdict();
	}

	private String printStats(ArrayList<Long> costs) {
		double mean = mean(costs);
		String S = ("Mean : "+mean)+"\n";
		S += "Standard deviation : "+standardDeviation(costs, mean);
		return S;
	}

	private double standardDeviation(ArrayList<Long> costs, double mean) {
		double d = 0.0;
		for (Long l : costs) {
			d += Math.pow(mean - l, 2);
		}
		
			
		return Math.sqrt(d / costs.size());
	}

	private double mean(ArrayList<Long> costs) {
		double mean = 0.0;
		for (Long l : costs) {
			mean += l;
		}
		return mean/costs.size();
	}

	public Attacker getAttacker() {
		return traceGen.getAttacker();
	}

	public double getMeanCost() {
		return mean_cost;
	}
	
	public void resetTest() {
		smc.reset();
		this.mean_cost = 0.0;
	}

	
	

}
