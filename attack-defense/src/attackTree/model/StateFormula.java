package attackTree.model;

import java.util.ArrayList;

public class StateFormula extends Formula {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String stateFormula;
	
	public static String filterOuterParenthese(String s){
		int cpt = 1;
		if(s.charAt(0)== '(' )
		{
			for (int i = 1; i < s.length()-1; i++) {
				if(s.charAt(i)== '(' ) cpt ++;
				else if(s.charAt(i)== ')' ) cpt --;
				if(cpt == 0) return s;
			}
			return filterOuterParenthese(s.substring(1,s.length()-1));
			
		}
		else
			return s;
	}

	public StateFormula(String stateFormula) {
		super(null, null, null);
		this.stateFormula = filterOuterParenthese(stateFormula);
	}

	@Override
	public String toString() {
		
		return super.toString() + "" + stateFormula + "";
	}
	
	public String getStateFormula() {
		return this.stateFormula;
	}
	
	public void setStateFormula(String formula) {
		this.stateFormula = formula;
	}
	
	public ArrayList<String> getDataVariablesList(){
		ArrayList<String> list = new ArrayList<String>();
		
		String[] ss = ((String) stateFormula).split("/|\\+|<|>|-|!|&|\\||\\*|=|\\(|\\)");
		for (int i = 0; i < ss.length; i++) {
			if(ss[i]!="#True" && ss[i]!="#False" && ss[i]!="U" && ss[i]!="G" && ss[i]!="F" && ss[i]!="R" && ss[i]!="N"){
				try{
					@SuppressWarnings("unused")
					Double d = Double.parseDouble(ss[i]);
				}
				catch (NumberFormatException e) {
					if(ss[i].length()> 0 ){
						list.add(ss[i]);
					}
				}
			}
		}
		
		return list;
	}
	
	public boolean instantiate(String action, boolean value) {
		if(this.stateFormula.equals(action)) {
			this.stateFormula = (value?"#True":"#False");
			return true;
		}
		else return false;
	}
}
