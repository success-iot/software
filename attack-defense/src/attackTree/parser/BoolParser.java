// Generated from Bool.g4 by ANTLR 4.7
package attackTree.parser;

import attackTree.model.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoolParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, TRUE=3, FALSE=4, STRING=5, NOT=6, AND=7, OR=8, WS=9;
	public static final int
		RULE_program = 0, RULE_formula = 1, RULE_formula2 = 2, RULE_sub_formula = 3, 
		RULE_terminal = 4, RULE_subformula2 = 5;
	public static final String[] ruleNames = {
		"program", "formula", "formula2", "sub_formula", "terminal", "subformula2"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'('", "')'", "'#True'", "'#False'", null, "'!'", "'&&'", "'||'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "TRUE", "FALSE", "STRING", "NOT", "AND", "OR", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Bool.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BoolParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgramContext extends ParserRuleContext {
		public BooleanFormula f;
		public FormulaContext e;
		public TerminalNode EOF() { return getToken(BoolParser.EOF, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitProgram(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(12);
			((ProgramContext)_localctx).e = formula();
			setState(13);
			match(EOF);

				((ProgramContext)_localctx).f =  ((ProgramContext)_localctx).e.f;

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FormulaContext extends ParserRuleContext {
		public BooleanFormula f;
		public String s;
		public Sub_formulaContext e3;
		public Formula2Context e2;
		public Sub_formulaContext sub_formula() {
			return getRuleContext(Sub_formulaContext.class,0);
		}
		public Formula2Context formula2() {
			return getRuleContext(Formula2Context.class,0);
		}
		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterFormula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitFormula(this);
		}
	}

	public final FormulaContext formula() throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(16);
			((FormulaContext)_localctx).e3 = sub_formula();
			setState(17);
			((FormulaContext)_localctx).e2 = formula2();

				((FormulaContext)_localctx).s =   ((FormulaContext)_localctx).e3.s+ ((FormulaContext)_localctx).e2.s;
				if(((FormulaContext)_localctx).e2.s !="")
					((FormulaContext)_localctx).f =  new BooleanFormula( ((FormulaContext)_localctx).e3.f, ((FormulaContext)_localctx).e2.f, ((FormulaContext)_localctx).e2.o);
				else 
					((FormulaContext)_localctx).f =  ((FormulaContext)_localctx).e3.f;

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Formula2Context extends ParserRuleContext {
		public Operator o;
		public BooleanFormula f;
		public String s;
		public Sub_formulaContext e3;
		public Formula2Context e2;
		public TerminalNode OR() { return getToken(BoolParser.OR, 0); }
		public Sub_formulaContext sub_formula() {
			return getRuleContext(Sub_formulaContext.class,0);
		}
		public Formula2Context formula2() {
			return getRuleContext(Formula2Context.class,0);
		}
		public Formula2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formula2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterFormula2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitFormula2(this);
		}
	}

	public final Formula2Context formula2() throws RecognitionException {
		Formula2Context _localctx = new Formula2Context(_ctx, getState());
		enterRule(_localctx, 4, RULE_formula2);
		try {
			setState(26);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(20);
				match(OR);
				setState(21);
				((Formula2Context)_localctx).e3 = sub_formula();
				setState(22);
				((Formula2Context)_localctx).e2 = formula2();

					((Formula2Context)_localctx).s =  "||"+ ((Formula2Context)_localctx).e3.s+ ((Formula2Context)_localctx).e2.s;	
					((Formula2Context)_localctx).o =  new BooleanOperator(BooleanOperatorEnum.OR);
					if(((Formula2Context)_localctx).e2.s !="")
						((Formula2Context)_localctx).f =  new BooleanFormula( ((Formula2Context)_localctx).e3.f, ((Formula2Context)_localctx).e2.f, ((Formula2Context)_localctx).e2.o);
					else 
						((Formula2Context)_localctx).f =  ((Formula2Context)_localctx).e3.f;

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{

					((Formula2Context)_localctx).s =  "";

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Sub_formulaContext extends ParserRuleContext {
		public BooleanFormula f;
		public String s;
		public TerminalContext e4;
		public Subformula2Context e5;
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public Subformula2Context subformula2() {
			return getRuleContext(Subformula2Context.class,0);
		}
		public Sub_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sub_formula; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterSub_formula(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitSub_formula(this);
		}
	}

	public final Sub_formulaContext sub_formula() throws RecognitionException {
		Sub_formulaContext _localctx = new Sub_formulaContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_sub_formula);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			((Sub_formulaContext)_localctx).e4 = terminal();
			setState(29);
			((Sub_formulaContext)_localctx).e5 = subformula2();

				((Sub_formulaContext)_localctx).s =   ((Sub_formulaContext)_localctx).e4.s+ ((Sub_formulaContext)_localctx).e5.s;
				if(((Sub_formulaContext)_localctx).e5.s !="")
					((Sub_formulaContext)_localctx).f =  new BooleanFormula( ((Sub_formulaContext)_localctx).e4.f, ((Sub_formulaContext)_localctx).e5.f, ((Sub_formulaContext)_localctx).e5.o);
				else 
					((Sub_formulaContext)_localctx).f =  ((Sub_formulaContext)_localctx).e4.f;

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminalContext extends ParserRuleContext {
		public BooleanFormula f;
		public String s;
		public Token STRING;
		public FormulaContext e;
		public Token NOT;
		public FormulaContext e1;
		public TerminalNode TRUE() { return getToken(BoolParser.TRUE, 0); }
		public TerminalNode FALSE() { return getToken(BoolParser.FALSE, 0); }
		public TerminalNode STRING() { return getToken(BoolParser.STRING, 0); }
		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class,0);
		}
		public TerminalNode NOT() { return getToken(BoolParser.NOT, 0); }
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitTerminal(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_terminal);
		try {
			setState(47);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TRUE:
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				match(TRUE);

					((TerminalContext)_localctx).s =  "#True";
					((TerminalContext)_localctx).f =  new BooleanFormula(null, new StateFormula("#True"), null);

				}
				break;
			case FALSE:
				enterOuterAlt(_localctx, 2);
				{
				setState(34);
				match(FALSE);

					((TerminalContext)_localctx).s =  "#False";
					((TerminalContext)_localctx).f =  new BooleanFormula(null, new StateFormula("#False"), null);

				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 3);
				{
				setState(36);
				((TerminalContext)_localctx).STRING = match(STRING);

					((TerminalContext)_localctx).s =  (((TerminalContext)_localctx).STRING!=null?((TerminalContext)_localctx).STRING.getText():null);
					((TerminalContext)_localctx).f =  new BooleanFormula(null, new StateFormula((((TerminalContext)_localctx).STRING!=null?((TerminalContext)_localctx).STRING.getText():null)), null);

				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 4);
				{
				setState(38);
				match(T__0);
				setState(39);
				((TerminalContext)_localctx).e = formula();
				setState(40);
				match(T__1);

					((TerminalContext)_localctx).s =  "(" + ((TerminalContext)_localctx).e.s + ")" ;
					((TerminalContext)_localctx).f =  ((TerminalContext)_localctx).e.f;

				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 5);
				{
				setState(43);
				((TerminalContext)_localctx).NOT = match(NOT);
				setState(44);
				((TerminalContext)_localctx).e1 = formula();

					((TerminalContext)_localctx).s =  (((TerminalContext)_localctx).NOT!=null?((TerminalContext)_localctx).NOT.getText():null) + ((TerminalContext)_localctx).e1.s;
					((TerminalContext)_localctx).f =  new BooleanFormula( null, ((TerminalContext)_localctx).e1.f, new BooleanOperator(BooleanOperatorEnum.NOT));
					

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Subformula2Context extends ParserRuleContext {
		public Operator o;
		public BooleanFormula f;
		public String s;
		public TerminalContext e;
		public Subformula2Context e2;
		public TerminalNode AND() { return getToken(BoolParser.AND, 0); }
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public Subformula2Context subformula2() {
			return getRuleContext(Subformula2Context.class,0);
		}
		public Subformula2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subformula2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).enterSubformula2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoolListener ) ((BoolListener)listener).exitSubformula2(this);
		}
	}

	public final Subformula2Context subformula2() throws RecognitionException {
		Subformula2Context _localctx = new Subformula2Context(_ctx, getState());
		enterRule(_localctx, 10, RULE_subformula2);
		try {
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				match(AND);
				setState(50);
				((Subformula2Context)_localctx).e = terminal();
				setState(51);
				((Subformula2Context)_localctx).e2 = subformula2();

					((Subformula2Context)_localctx).s =  "&&"+ ((Subformula2Context)_localctx).e.s+ ((Subformula2Context)_localctx).e2.s;
					((Subformula2Context)_localctx).o =  new BooleanOperator(BooleanOperatorEnum.AND);
					if(((Subformula2Context)_localctx).e2.s !="")
						((Subformula2Context)_localctx).f =  new BooleanFormula( ((Subformula2Context)_localctx).e.f, ((Subformula2Context)_localctx).e2.f, ((Subformula2Context)_localctx).e2.o);
					else 
						((Subformula2Context)_localctx).f =  ((Subformula2Context)_localctx).e.f;

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{

					((Subformula2Context)_localctx).s = "";

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13<\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\5\4\35\n\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\62\n\6\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\5\7:\n\7\3\7\2\2\b\2\4\6\b\n\f\2\2\2;\2\16\3\2\2\2\4\22\3\2\2\2\6"+
		"\34\3\2\2\2\b\36\3\2\2\2\n\61\3\2\2\2\f9\3\2\2\2\16\17\5\4\3\2\17\20\7"+
		"\2\2\3\20\21\b\2\1\2\21\3\3\2\2\2\22\23\5\b\5\2\23\24\5\6\4\2\24\25\b"+
		"\3\1\2\25\5\3\2\2\2\26\27\7\n\2\2\27\30\5\b\5\2\30\31\5\6\4\2\31\32\b"+
		"\4\1\2\32\35\3\2\2\2\33\35\b\4\1\2\34\26\3\2\2\2\34\33\3\2\2\2\35\7\3"+
		"\2\2\2\36\37\5\n\6\2\37 \5\f\7\2 !\b\5\1\2!\t\3\2\2\2\"#\7\5\2\2#\62\b"+
		"\6\1\2$%\7\6\2\2%\62\b\6\1\2&\'\7\7\2\2\'\62\b\6\1\2()\7\3\2\2)*\5\4\3"+
		"\2*+\7\4\2\2+,\b\6\1\2,\62\3\2\2\2-.\7\b\2\2./\5\4\3\2/\60\b\6\1\2\60"+
		"\62\3\2\2\2\61\"\3\2\2\2\61$\3\2\2\2\61&\3\2\2\2\61(\3\2\2\2\61-\3\2\2"+
		"\2\62\13\3\2\2\2\63\64\7\t\2\2\64\65\5\n\6\2\65\66\5\f\7\2\66\67\b\7\1"+
		"\2\67:\3\2\2\28:\b\7\1\29\63\3\2\2\298\3\2\2\2:\r\3\2\2\2\5\34\619";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}