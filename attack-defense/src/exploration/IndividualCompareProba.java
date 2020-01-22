package exploration;

import java.util.Comparator;

/**
 * Comparator of individuals based on their score (BIC or F1_score)
 * @author mediouni
 *
 */
public class IndividualCompareProba implements Comparator<Individual> {

	// Sort in an decreasing order
    public int compare(Individual o1, Individual o2) {
    	if(o1.getProbaSuccess() < o2.getProbaSuccess()) return 1;
    	else
    		if(o1.getProbaSuccess() == o2.getProbaSuccess()) return 0;
    		else
    			return -1;
    }
}
