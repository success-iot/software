package exploration;

import java.util.Comparator;

/**
 * Comparator of individuals based on their score (BIC or F1_score)
 * @author mediouni
 *
 */
public class IndividualCompareRank implements Comparator<Individual> {

	// Sort in an increasing order
    public int compare(Individual o1, Individual o2) {
    	if(o1.getRank() > o2.getRank()) return 1;
    	else
    		if(o1.getRank() == o2.getRank()) {
    			if(o1.getFitness() < o2.getFitness()) return 1;
    			else if(o1.getFitness() == o2.getFitness()) return 0;
    				else return -1;
    		}
    		else
    			return -1;
    }
}
