import aima.core.search.framework.problem.GoalTest;

/**
 * @author Sang Agung R. P.
 * 
 */
public class LabGoalTest implements GoalTest {

	public boolean isGoalState(Object state) {
		// System.out.println("Sisa item : " + ((LabEnvironment) state).getItemAmount());
		return (((LabEnvironment) state).getItemAmount() == 1);
	}
}