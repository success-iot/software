package generator;

import attackTree.model.*;
import attackTree.monitor.Monitor;
import attackTree.parser.BooleanParser;
import players.*;

public class TraceGenerator {
	
	private Attacker A;
	private Defender D;
	private Environment env;
	private String formula ;
	
	public TraceGenerator(Attacker A, Defender D, Environment env, String formula) {
		this.A = A;
		this.D = D;
		this.env = env;
		this.formula = formula;
	}
	
	public void setDefender(Defender d) {
		this.D = d;
	}
	
	public Defender getDefender() {
		return this.D;
	}
	
	public Trace generateTrace() {
		A.setCost(0);
		A.setTime(0);

		A.resetActionHistory();
		Trace trace = new Trace();
		Monitor monitor = new Monitor();
		
		BooleanParser parser = new BooleanParser();		
		BooleanFormula tree = parser.parse(formula);
		
		monitor.setDefenses(tree, D);
		
 		
		while(!tree.isTerminal()) {
			String action = A.tryAttack();
			boolean success = env.evaluateSuccess(action);
			trace.addSymbol(action);
			trace.addSymbol(success? "OK": "KO");
			if(success) {
				tree.instantiate(action, success);
				A.setActionSucceeded(action);
				tree = monitor.simplify(tree);
			}
		}
		
		
		if(((StateFormula) tree.getRightFormula()).getStateFormula().equals("#True") ) {
			trace.setVerdict(true);
		}
		else {
			trace.setVerdict(false);
		}
		trace.setCost(A.getCost());
 		return trace;		
	}

	public Trace generateTrace(long max_cost, long max_time) {
		A.setCost(0);
		A.setTime(0);
		A.resetActionHistory();
		Trace trace = new Trace();
		Monitor monitor = new Monitor();

		BooleanParser parser = new BooleanParser();		
		BooleanFormula tree = parser.parse(formula);
		
		
		monitor.setDefenses(tree, D);
		tree = monitor.simplify(tree);
		
		boolean found_error = false;
 		
		while(!tree.isTerminal() && A.getCost()<= max_cost && A.getTime()<= max_time && !found_error) {
			String action = A.tryAttack();
			if(action.equals("ERROR")){
//				System.err.println("Found error");
				trace.setVerdict(false);
				found_error = true;
			}
			else {
				boolean success = env.evaluateSuccess(action);
				trace.addSymbol(action);
				trace.addSymbol(success? "OK": "KO");
				if(success) {
					A.setActionSucceeded(action);
					tree.instantiate(action, success);
					tree = monitor.simplify(tree);
				}
			}
			
		}
//		if(found_error)
//			System.err.println("While loop done!");
		
		if(!found_error) {
			if(A.getCost()>max_cost || A.getTime()>max_time)
			{
//				A.setCost(max_cost);
				trace.setVerdict(false);
			}
			else {
				if(((StateFormula) tree.getRightFormula()).getStateFormula().equals("#True") ) {
					trace.setVerdict(true);
				}
				else {
//					A.setCost(max_cost);
					trace.setVerdict(false);
				}
			}
		}
		
		trace.setCost(A.getCost());
 		return trace;
	}

	public Attacker getAttacker() {
		return A;
	}


	public String toString() {
		String S = "";
		S+= A+"\n";
		S+= D+"\n";
//		S+= env+"\n";
		return S;
	}

}
