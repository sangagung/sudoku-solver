import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.problem.ActionsFunction;
import aima.core.search.framework.problem.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class LabFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new LEActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new LEResultFunction();
		}
		return _resultFunction;
	}

	private static class LEActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			LabEnvironment le = (LabEnvironment) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (le.canMove(LabEnvironment.ATAS)) {
				actions.add(LabEnvironment.ATAS);
			}
			if (le.canMove(LabEnvironment.BAWAH)) {
				actions.add(LabEnvironment.BAWAH);
			}
			if (le.canMove(LabEnvironment.KIRI)) {
				actions.add(LabEnvironment.KIRI);
			}
			if (le.canMove(LabEnvironment.KANAN)) {
				actions.add(LabEnvironment.KANAN);
			}
			if (le.canMove(LabEnvironment.AMBIL)){
				actions.add(LabEnvironment.AMBIL);
			}


			return actions;
		}
	}

	private static class LEResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			LabEnvironment le = (LabEnvironment) s;

			// le.printLab();
			// System.out.println();
			// System.out.print("Currently in: " + le.getCurCoordinate() + " can move to ");

			if (LabEnvironment.ATAS.equals(a)
					&& le.canMove(LabEnvironment.ATAS)) {
				LabEnvironment newLe = new LabEnvironment(le.getObstacles(), le.getItems(), le.getCurCoordinate(), le.getItemAmount());
				newLe.moveUp();
				//System.out.println(newLe.getCurCoordinate() + "ATAS");
				return newLe;
			} else if (LabEnvironment.BAWAH.equals(a)
					&& le.canMove(LabEnvironment.BAWAH)) {
				LabEnvironment newLe = new LabEnvironment(le.getObstacles(), le.getItems(), le.getCurCoordinate(), le.getItemAmount());
				newLe.moveDown();
				//System.out.println(newLe.getCurCoordinate() + "BAWAH");
				return newLe;
			} else if (LabEnvironment.KIRI.equals(a)
					&& le.canMove(LabEnvironment.KIRI)) {
				LabEnvironment newLe = new LabEnvironment(le.getObstacles(), le.getItems(), le.getCurCoordinate(), le.getItemAmount());
				newLe.moveLeft();
				//System.out.println(newLe.getCurCoordinate() + "KIRI");
				return newLe;
			} else if (LabEnvironment.KANAN.equals(a)
					&& le.canMove(LabEnvironment.KANAN)) {
				LabEnvironment newLe = new LabEnvironment(le.getObstacles(), le.getItems(), le.getCurCoordinate(), le.getItemAmount());
				newLe.moveRight();
				//System.out.println(newLe.getCurCoordinate() + "KANAN");
				return newLe;
			} else if (LabEnvironment.AMBIL.equals(a)) {
				LabEnvironment newLe = new LabEnvironment(le.getObstacles(), le.getItems(), le.getCurCoordinate(), le.getItemAmount());
				newLe.pickUp();
				//System.out.println("Ambil @" + newLe.getCurCoordinate());
				return newLe;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}