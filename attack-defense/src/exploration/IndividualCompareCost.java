package exploration;

import java.util.Comparator;

/**
 * Comparator of individuals based on their score (BIC or F1_score)
 * @author mediouni
 *
 */
public class IndividualCompareCost implements Comparator<Individual> {

	// Sort in an increasing order
    public int compare(Individual o1, Individual o2) {
    	if(o1.getCost() > o2.getCost()) return 1;
    	else
    		if(o1.getCost() == o2.getCost()) return 0;
    		else
    			return -1;
    }
}
