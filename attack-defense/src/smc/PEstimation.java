package smc;


public class PEstimation{

	private int N;	
	private int nb_traces = 0;
	private int nb_traces_true = 0;
	private boolean concluded = false;
	private double verdict = 0.0;
	
	public PEstimation(double delta, double alpha) {
		N =(int)Math.ceil(4*Math.log(2/alpha)/(Math.pow(delta, 2))); 
		
	}

	public boolean runStep(boolean t_verdict) {
		addTrace(t_verdict);
		conclude();
		
		return concluded;
	}

	private void conclude() {
		if(N == nb_traces) {
			verdict = (nb_traces_true+0.0)/(N+0.0);
			concluded = true;
		}
		
	}

	public int getN(){
		return N;
	}

	private void addTrace(boolean t_verdict){
		nb_traces ++;
		if(t_verdict) nb_traces_true ++;
			
	}
	
	public boolean hasConcluded(){
		return concluded;
	}
	

	public double getVerdict() {
		return verdict;
	}

	public void reset() {
		nb_traces = 0;
		nb_traces_true = 0;
		concluded = false;
		verdict = 0.0;
	}
}
