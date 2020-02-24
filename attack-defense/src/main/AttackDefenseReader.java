
package main;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;

/*
 *
 * Attack Defense File Reader
 *
 */

public class AttackDefenseReader {
    
    private String m_tree;
    private ArrayList<Attack> m_attacks;
    private ArrayList<Defense> m_defenses;
    private long m_max_cost;
    private long m_max_time;

    public AttackDefenseReader(String filename) {
	m_tree = "";
	m_attacks = new ArrayList<Attack>();
	m_defenses = new ArrayList<Defense>();
	m_max_cost = 0;
	m_max_time = 0;
	parse(filename);
    }

    public String getTree() {
	return m_tree;
    }
    public String[] getAttackActions() {
	String[] actions = new String[m_attacks.size()];
	for(int i = 0; i < m_attacks.size(); i++ )
	    actions[i] = m_attacks.get(i).name;
	return actions;
    }
    public long[] getAttackCosts() {
	long[] costs = new long[ m_attacks.size() ];
	for(int i = 0; i < m_attacks.size(); i++ )
	    costs[i] = m_attacks.get(i).cost;
	return costs;
    }
    public double[] getAttackLowerBounds() {
	double[] lower = new double[ m_attacks.size() ]; 
 	for(int i = 0; i < m_attacks.size(); i++ )
	    lower[i] = m_attacks.get(i).lower;
	return lower;
    }
    public double[] getAttackUpperBounds() {
	double[] upper = new double[ m_attacks.size() ]; 
 	for(int i = 0; i < m_attacks.size(); i++ )
	    upper[i] = m_attacks.get(i).upper;
	return upper;
    }
    public double[] getAttackProbas() {
	double[] probas = new double[ m_attacks.size() ]; 
 	for(int i = 0; i < m_attacks.size(); i++ )
	    probas[i] = m_attacks.get(i).proba;
	return probas;
    }
    
    public String[] getDefenseActions() {
	String[] actions = new String[m_defenses.size()];
	for(int i = 0; i < m_defenses.size(); i++ )
	    actions[i] = m_defenses.get(i).name;
	return actions;
    }
    public boolean[] getDefenseStatuses() {
	boolean[] statuses = new boolean[m_defenses.size()];
	for(int i = 0; i < m_defenses.size(); i++ )
	    statuses[i] = m_defenses.get(i).status;
	return statuses;
    }
    public long getMaxTime() {
	return m_max_time;
    }
    public long getMaxCost() {
	return m_max_cost;
    }

    //
    // private members
    //

    private void parse(String filename) {
	// attack pattern : "attack actionID costN [lowN,upperN] probaF"
	Pattern ap = Pattern.compile("attack\\s+(\\w+)\\s+(\\d+)\\s+\\[(\\d+),(\\d+)\\]\\s+(\\d+.\\d+)");
	
	// defense pattern : "defense actionID statusID"
	Pattern dp = Pattern.compile("defense\\s+(\\w+)\\s+(\\w+)");
	
	String thisLine = null;
	try {
	    // open input stream test.txt for reading purpose.
	    BufferedReader br = new BufferedReader(new FileReader(filename));
	    
	    int mode = 0; // 0 - normal , 1 - within tree
	    while ((thisLine = br.readLine()) != null) {
		// System.out.println(thisLine);
		
		thisLine.trim();
		
		// ignore comments and empty lines
		if (thisLine.startsWith("#") || thisLine.equals(""))
		    continue;
		
		if (mode == 0) {
		    // read attack line
		    if (thisLine.startsWith("attack")) {
			Matcher m = ap.matcher(thisLine);
			if (m.find()) 
			    m_attacks.add(new Attack(m.group(1),
						     Long.valueOf(m.group(2)),
						     Double.valueOf(m.group(3)),
						     Double.valueOf(m.group(4)),
						     Double.valueOf(m.group(5))));
			else
			    System.out.println("malformed attack line '" + thisLine + "' ignored");
		    }
		    // read defense line
		    else if (thisLine.startsWith("defense")) {
			Matcher m = dp.matcher(thisLine);
			if (m.find()) 
			    m_defenses.add(new Defense(m.group(1),
						       Boolean.valueOf(m.group(2))));
			
			else 
			    System.out.println("malformed defense line '" + thisLine + "' ignored");
		    }
		    // read max cost line
		    else if (thisLine.startsWith("max cost"))
			m_max_cost = Long.valueOf( thisLine.substring(8).trim() );
		    // read max time line
		    else if (thisLine.startsWith("max time"))
			m_max_time = Long.valueOf( thisLine.substring(8).trim() );
		    
		    // read attack-defense tree
		    else if (thisLine.startsWith("tree")) {
			m_tree = "";
			if (thisLine.endsWith("end")) 
			    // attack written on a single line
			    m_tree = thisLine.substring(4, thisLine.length() - 3);
			else {
			    // attack written on multiple lines...
			    m_tree = m_tree + " " + thisLine.substring(4);
			    mode = 1;
			}	
		    }
		    else 
			System.out.println("malformed line '" + thisLine + "' ignored");
		}
		
		else {
		    // attack written on multiple lines...
		    if (thisLine.endsWith("end")) {
			m_tree = m_tree + " " + thisLine.substring(0, thisLine.length() - 3);
			mode = 0;
		    }
		    else 
			m_tree = m_tree + " " + thisLine;
		}	
	    }
	} catch(Exception e) {
	    e.printStackTrace();
	}	
    }

    //
    // additional data structures
    //
    
    public class Attack {
	String name;
	long cost;
	double lower, upper;
	double proba;
	Attack(String n, long c, double l, double u, double p) {
	    name = n; cost = c; lower = l; upper = u; proba = p;
	}
    }
    
    public class Defense {
	String name;
	boolean status;
	Defense(String n, boolean s) {
	    name = n; status = s;
	}
    }   
}
