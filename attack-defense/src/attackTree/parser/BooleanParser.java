package attackTree.parser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CommonTokenStream;

import attackTree.model.BooleanFormula;

public class BooleanParser {
	
	public BooleanFormula parse(String strFormula){
		//strFormula = removeSpaces(strFormula);
		BoolLexer lexer = new BoolLexer(new ANTLRInputStream(strFormula));
		BoolParser parser = new BoolParser(new CommonTokenStream(lexer));
		
//		lexer.removeErrorListeners();
//		parser.removeErrorListeners();
//		parser.setErrorHandler(new BailErrorStrategy());
//		parser.setBuildParseTree(false);
		
		BooleanFormula phi = parser.program().f;
		return phi;
	}

}
