
package main;

import attackTree.model.Formula;
import attackTree.model.BooleanFormula;
import attackTree.model.BooleanOperator;
import attackTree.model.BooleanOperatorEnum;
import attackTree.model.StateFormula;
import players.Defender;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class AttackDefenseExporter {

    private Formula tree;

    private Defender defender;
    private HashMap<String,Boolean> actMap;

    private int index;
    private HashMap<XFormula,Integer> nodeMap;

    
    AttackDefenseExporter(Formula t, Defender d) {
	tree = t;
	defender = d;
	actMap = new HashMap<String, Boolean>();
	for(int i = 0; i < defender.getNbDefenses(); i++) {
	    String action = defender.getAction(i);
	    actMap.put( action, defender.getActivationStatus(i));
	}
	index = 0;
	nodeMap = new HashMap<XFormula,Integer>();
	
    }

    public void dotExport(String filename) {
	try {
	    // write the dot string to the file
	    BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
	    if ( bw != null) {
		bw.write( dotString() );
		bw.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public String dotString() {
	String result = "";
	result += "digraph attack_defense_tree {\n";
	result += dotString( collapse(tree) );
	result += "}";
	return result;
    }
   
    private String dotString(XFormula F) {
	if (!nodeMap.containsKey(F))
	    nodeMap.put(F, index++);
	
	String result = "";

	// generate dot node
	result += "F" + Integer.toString(nodeMap.get(F)) +
	    " [shape=" + F.getShape(actMap) +
	    ", label=" + F.getLabel() +
	    ", color=" + F.getColor(actMap) + "]\n";
	
	if (F.arguments == null)
	    return result;

	// recursion + generate edges
	for(int i = 0; i < F.arguments.size(); i++) {
	    result += dotString (F.arguments.get(i) );
	    result += "F" + nodeMap.get(F) + " -> " +
		"F" + nodeMap.get( F.arguments.get(i)) + "\n";
	}
	
	return result;
    }
    
    // additional classes / methods
    // collapse tree formula to n-ary AND/OR operators...
    
    private XFormula collapse(Formula F) {
	if (F instanceof BooleanFormula)
	    return collapse( (BooleanFormula) F);
	if (F instanceof StateFormula)
	    return collapse( (StateFormula) F);
	return null;
    }
    
    private XFormula collapse(BooleanFormula F) {
	ArrayList<XFormula> args = new ArrayList<XFormula>();
	XFormula left = null, right = null;
	
	if (F.getLeftFormula() != null)
	    left = collapse(F.getLeftFormula());
	if (F.getRightFormula() != null)
	    right = collapse(F.getRightFormula());
	
	if (F.getOperator() == null)
	    return right;

	BooleanOperator bop = (BooleanOperator) F.getOperator();
	
	if (bop.getOpName() == BooleanOperatorEnum.NOT) {
	    args.add(right);
	    return new XFormula(XOperator.XNOT, args, null);
	}

	if (bop.getOpName() == BooleanOperatorEnum.AND) {
	    if (left.operator == XOperator.XAND && right.operator == XOperator.XAND) {
		left.arguments.addAll(right.arguments);
		return left;
	    }
	    else if (left.operator == XOperator.XAND) {
		left.arguments.add(right);
		return left;
	    }
	    else if (right.operator == XOperator.XAND) {
		right.arguments.add(left);
		return right;
	    }
	    else {
		args.add(left);	args.add(right);
		return new XFormula(XOperator.XAND, args, null);
	    }		
	}
	
	if (bop.getOpName() == BooleanOperatorEnum.OR) {
	    if (left.operator == XOperator.XOR && right.operator == XOperator.XOR) {
		left.arguments.addAll(right.arguments);
		return left;
	    }
	    else if (left.operator == XOperator.XOR) {
		left.arguments.add(right);
		return left;
	    }
	    else if (right.operator == XOperator. XOR) {
		right.arguments.add(left);
		return right;
	    }
	    else {
		args.add(left);	args.add(right);
		return new XFormula(XOperator.XOR, args, null);
	    }		
	}
	
	return null;
    }
    
    private XFormula collapse(StateFormula F) {
	return new XFormula(XOperator.XNAME, null, F.getStateFormula());
    }
    
    private enum XOperator { XAND, XOR, XNOT, XNAME };
   
    private class XFormula {
	XOperator operator;
	public ArrayList<XFormula> arguments;
	public String name;
	XFormula(XOperator op, ArrayList<XFormula> args, String nm) {
	    operator = op;
	    arguments = args;
	    name = nm;
	}
	String getShape(HashMap<String, Boolean> actMap) {
	    return operator == XOperator.XNAME ?
		(actMap.containsKey(name) ? "parallelogram" : "rectangle") :
		"circle";
	}
	String getLabel() {
	    String label = null;
	    switch (operator) {
	    case XAND:  label = "AND"; break;
	    case XOR:   label = "OR"; break;
	    case XNOT:  label = "NOT"; break;
	    case XNAME: label = name; break;
	    }
	    return label;
	}
	String getColor(HashMap<String, Boolean> actMap) {
	    return operator == XOperator.XNAME ?
		( actMap.containsKey(name) ?
		  (actMap.get(name) ? "blue" : "green") :
		  "red" ) :
		"black";
	}
    }
    
};
