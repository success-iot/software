package attackTree.monitor;

import attackTree.model.BooleanFormula;
import attackTree.model.BooleanOperator;
import attackTree.model.BooleanOperatorEnum;
import attackTree.model.Formula;
import attackTree.model.StateFormula;
import players.Defender;

public class Monitor {
	
	public BooleanFormula simplify(BooleanFormula f){
		if(f == null) return null;
		
		if(f.getOperator() != null)
		if(f.getOperator() instanceof BooleanOperator){
			if(((BooleanOperator)f.getOperator()).getOpName() == BooleanOperatorEnum.AND){
	
				BooleanFormula left = simplify( (BooleanFormula) f.getLeftFormula());
				BooleanFormula right = simplify( (BooleanFormula) f.getRightFormula());
				if(left == null) return null;
				if(right == null) return null;
				
				
				if(left.getOperator() == null)
					if( ((StateFormula) left.getRightFormula()).getStateFormula().equals("#False") ){
						return new BooleanFormula(null ,  new StateFormula("#False") , null);			
					}
				
				if(right.getOperator() == null)
					if(((StateFormula) right.getRightFormula()).getStateFormula().equals("#False")){
						return new BooleanFormula(null ,  new StateFormula("#False") , null);			
					}
				
				if(right.getOperator() == null)
					if(((StateFormula) right.getRightFormula()).getStateFormula().equals("#True")  ){
						return left;			
					}
				
				if(left.getOperator() == null)
					if(((StateFormula) left.getRightFormula()).getStateFormula().equals("#True")   ){
						return right;				
					}
				
				return new BooleanFormula( left, right, new BooleanOperator(BooleanOperatorEnum.AND) );
				
			}
			
			
			else if(((BooleanOperator)f.getOperator()).getOpName() == BooleanOperatorEnum.OR){
	
				BooleanFormula left = simplify( (BooleanFormula) f.getLeftFormula());
				BooleanFormula right = simplify( (BooleanFormula) f.getRightFormula());
				
				
				if(left.getOperator() == null)
					if(((StateFormula) left.getRightFormula()).getStateFormula().equals("#True")   ){
						return new BooleanFormula(null ,  new StateFormula("#True") , null);					
					}
				
				if(right.getOperator() == null)
					if(((StateFormula) right.getRightFormula()).getStateFormula().equals("#True")   ){
						return new BooleanFormula(null ,  new StateFormula("#True") , null);					
					}
				
				if(right.getOperator() == null)
					if(((StateFormula) right.getRightFormula()).getStateFormula().equals ("#False")  ){
						return left;			
					}
				if(left.getOperator() == null)
					if(((StateFormula) left.getRightFormula()).getStateFormula().equals ("#False")  ){
						return right;				
					}
				
				return new BooleanFormula( left, right, new BooleanOperator(BooleanOperatorEnum.OR) );
				
			}	
			
			else if(((BooleanOperator)f.getOperator()).getOpName() == BooleanOperatorEnum.NOT) {
				BooleanFormula right = simplify( (BooleanFormula) f.getRightFormula());
				if(right.getOperator() == null) {
					if( ((StateFormula) right.getRightFormula()).getStateFormula().equals("#True")   ){
						return new BooleanFormula(null ,  new StateFormula("#False") , null);					
					}
					
					if( ((StateFormula) right.getRightFormula()).getStateFormula().equals("#False")   ){
						return new BooleanFormula(null ,  new StateFormula("#True") , null);					
					}
				}
			}
		}
		
		return f;
	}

	public void setDefenses(BooleanFormula tree, Defender d) {
		for (int i = 0; i < d.alphabetSize(); i++) {
			tree.instantiate(d.getAction(i), d.getActivationStatus(i));
		}
	}
}
