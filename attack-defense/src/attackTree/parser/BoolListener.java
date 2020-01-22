// Generated from Bool.g4 by ANTLR 4.7
package attackTree.parser;

import attackTree.model.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BoolParser}.
 */
public interface BoolListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BoolParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(BoolParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(BoolParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolParser#formula}.
	 * @param ctx the parse tree
	 */
	void enterFormula(BoolParser.FormulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#formula}.
	 * @param ctx the parse tree
	 */
	void exitFormula(BoolParser.FormulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolParser#formula2}.
	 * @param ctx the parse tree
	 */
	void enterFormula2(BoolParser.Formula2Context ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#formula2}.
	 * @param ctx the parse tree
	 */
	void exitFormula2(BoolParser.Formula2Context ctx);
	/**
	 * Enter a parse tree produced by {@link BoolParser#sub_formula}.
	 * @param ctx the parse tree
	 */
	void enterSub_formula(BoolParser.Sub_formulaContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#sub_formula}.
	 * @param ctx the parse tree
	 */
	void exitSub_formula(BoolParser.Sub_formulaContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(BoolParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(BoolParser.TerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link BoolParser#subformula2}.
	 * @param ctx the parse tree
	 */
	void enterSubformula2(BoolParser.Subformula2Context ctx);
	/**
	 * Exit a parse tree produced by {@link BoolParser#subformula2}.
	 * @param ctx the parse tree
	 */
	void exitSubformula2(BoolParser.Subformula2Context ctx);
}