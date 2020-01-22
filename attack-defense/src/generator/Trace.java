package generator;

import java.util.ArrayList;

public class Trace {
	
	private ArrayList<String> symbols;
	private boolean verdict;
	private long cost;
	

	public Trace() {
		this.symbols = new ArrayList<String>();
	}
	
	public void addSymbol(String symbol) {
		this.symbols.add(symbol);
	}
	
	public ArrayList<String> getSymbols() {
		return symbols;
	}

	public void setSymbols(ArrayList<String> symbols) {
		this.symbols = symbols;
	}

	public boolean getVerdict() {
		return verdict;
	}

	public void setVerdict(boolean verdict) {
		this.verdict = verdict;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public String toString() {
		String S = "";
		for (String string : symbols) {
			S+=" "+string;
		}
		S+="\n Verdict : "+ verdict;
		S+="\n Cost : "+ cost;
		return S;
	}
	
	
	

}
