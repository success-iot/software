grammar Bool;

@header {
import attackTree.model.*;
}


// productions for syntax analysis
program returns [BooleanFormula f]: e=formula EOF
{
	$f = $e.f;
}
;


formula returns [BooleanFormula f, String s]
:  e3=sub_formula e2= formula2
{
	$s =  $e3.s+ $e2.s;
	if($e2.s !="")
		$f = new BooleanFormula( $e3.f, $e2.f, $e2.o);
	else 
		$f = $e3.f;
} 
;

formula2 returns [Operator o, BooleanFormula f, String s]
: OR e3=sub_formula e2=formula2 
{
	$s = "||"+ $e3.s+ $e2.s;	
	$o = new BooleanOperator(BooleanOperatorEnum.OR);
	if($e2.s !="")
		$f = new BooleanFormula( $e3.f, $e2.f, $e2.o);
	else 
		$f = $e3.f;
}
|
{
	$s = "";
}
;

sub_formula returns [BooleanFormula f, String s]
: e4=terminal e5=subformula2
{
	$s =  $e4.s+ $e5.s;
	if($e5.s !="")
		$f = new BooleanFormula( $e4.f, $e5.f, $e5.o);
	else 
		$f = $e4.f;
} 
;

terminal returns [BooleanFormula f, String s]
:TRUE 
{
	$s = "#True";
	$f = new BooleanFormula(null, new StateFormula("#True"), null);
}
| FALSE 
{
	$s = "#False";
	$f = new BooleanFormula(null, new StateFormula("#False"), null);
}
| STRING 
{
	$s = $STRING.text;
	$f = new BooleanFormula(null, new StateFormula($STRING.text), null);
}
| '(' e=formula ')' 
{
	$s = "(" + $e.s + ")" ;
	$f = $e.f;
}
| NOT e1 = formula
{
	$s = $NOT.text + $e1.s;
	$f = new BooleanFormula( null, $e1.f, new BooleanOperator(BooleanOperatorEnum.NOT));
	
}
;



subformula2 returns [Operator o, BooleanFormula f, String s]
: AND e=terminal e2=subformula2
{
	$s = "&&"+ $e.s+ $e2.s;
	$o = new BooleanOperator(BooleanOperatorEnum.AND);
	if($e2.s !="")
		$f = new BooleanFormula( $e.f, $e2.f, $e2.o);
	else 
		$f = $e.f;
}
|
{
	$s="";
}
;







// Lexer
TRUE : '#True';
FALSE : '#False';
STRING : ([a-zA-Z])+[0-9]*([a-zA-Z] | '.' | '_')*; 
NOT : '!';
AND : '&&';
OR : '||';
WS : [ \n\t\r]+ -> skip;