package players;

import java.util.Arrays;

public class Attacker {
	private long cost ;
	private long time ;
	private long[] cost_function;
	private String[] actions;
	private boolean[] succeedingActions;
	private double[] scheduler;
	private double[] lower_bound;
	private double[] upper_bound;
	
	
	private Attacker(long[] cost_function, String[] actions) {
		this.cost = 0;
		this.time = 0;
		this.cost_function = cost_function;
		this.actions = actions;
		this.scheduler = generateScheduler();
		this.cumulativeScheduler = computeCumulative(this.scheduler);
		this.succeedingActions = new boolean[this.actions.length];
		for (int i = 0; i<actions.length; i++) {
			succeedingActions[i] = false;
		}
	}
	
	
	
	public Attacker(long[] cost_function, String[] actions, double[] lower_bound,
			double[] upper_bound) {
		super();
		this.cost = 0;
		this.time = 0;
		this.cost_function = cost_function;
		this.actions = actions;
		this.lower_bound = lower_bound;
		this.upper_bound = upper_bound;
		this.succeedingActions = new boolean[this.actions.length];
		for (int i = 0; i<actions.length; i++) {
			succeedingActions[i] = false;
		}
		this.scheduler = generateScheduler();
		this.cumulativeScheduler = computeCumulative(this.scheduler);
	}

	
	public void setActionSucceeded(String action) {
		int i = indexOf(action);
		succeedingActions[i] = true;
	}
	
	private int indexOf(String action) {
		for (int i = 0; i< actions.length; i++) {
			if(actions[i].equals(action))
				return i;
		}
		return -1;
	}



	public boolean isActionSucceded(int i) {
		return succeedingActions[i];
	}


	public double[] getScheduler() {
		return scheduler;
	}

	public void setScheduler(double[] scheduler) {
		this.scheduler = scheduler;
		this.cumulativeScheduler = computeCumulative(this.scheduler);
	}

	private double[] cumulativeScheduler;
	


	private double[] computeCumulative(double[] scheduler2) {
		double[] cumul = new double[scheduler2.length];
		double sum = 0;
		for (int i = 0; i < scheduler2.length; i++) {
			sum += scheduler2[i];
			cumul[i] = sum;
		}
		
		if(sum !=1)
			{
//			System.err.println("Cumulative error! "+sum);
			cumul[scheduler2.length - 1] += 1 - sum;
			}
		return cumul;
		
	}

	public double[] generateScheduler() {
		int length = actions.length;
		double[] schedulingFunction = new double[length];
		double[] P = new double[length];
		double sum = 0;
		for (int i = 0; i < P.length; i++) {
			P[i] = Math.random();
			sum += P[i] ;
		}
		
		double sumFloat = 0;
		for (int i = 0; i < schedulingFunction.length; i++) {
			schedulingFunction[i] = P[i] / sum;
			sumFloat += schedulingFunction[i] ;
		}
		
		schedulingFunction[length - 1] += 1 - sumFloat;
			
		return schedulingFunction;
	}
	
	public void resetAttacker(){
		this.cost = 0;
		this.scheduler = generateScheduler();
		this.cumulativeScheduler = computeCumulative(this.scheduler);
		
	}
	
	public void resetActionHistory() {
		for (int i = 0; i<actions.length; i++) {
			succeedingActions[i] = false;
		}
	}
	
	public String tryAttack() {
		double[] cumulativeScheduler2 = computeAvailableActions();
		double p = Math.random();
		for (int i = 0; i < actions.length; i++) {
			if(p <= cumulativeScheduler2[i])
			{
				time += selectTime(i);
				cost += cost_function[i];
				return actions[i];
			}
		}
//		System.err.println(Arrays.toString(cumulativeScheduler2));
//		System.err.println(Arrays.toString(succeedingActions));
		return "ERROR";
	}

	private double[] computeAvailableActions() {
		double[] scheduler = this.scheduler.clone();
		for (int i = 0; i < scheduler.length; i++) {
			if(succeedingActions[i])
				scheduler[i] = 0;
		}
		scheduler = normalize(scheduler);
		return computeCumulative(scheduler);
		
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

	private long selectTime(int i) {

		long t = (long) (this.lower_bound[i] + ((long) Math.floor( Math.random() * (this.upper_bound[i] - this.lower_bound[i]))));
		return t;
	}



	public long getCost() {
		return cost;
	}
	
	public long getTime() {
		return time;
	}

	public void setCost(long i) {
		cost = 0;
	}



	public void setTime(int i) {
		time = 0;
	}
	
	public String toString()
	{
		String S = "Attacker : \n";
		S += Arrays.toString(actions);
		return S;
	}
	
}
