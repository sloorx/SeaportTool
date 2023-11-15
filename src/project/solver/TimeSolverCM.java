package project.solver;

import java.util.List;

import project.Quest;
import project.QuestSolver;
import project.Solution;

public class TimeSolverCM implements QuestSolver, ISolverCM {

	@Override
	public List<Solution> solve(Quest q) {
		SolverCM sCM = new SolverCM(this);
		return sCM.solve(q);
	}

	public List<Solution> getBestSolutions(List<Solution> result) {
		// get lowest number of turns and lowest free capacity
		int minTurnCount = Integer.MAX_VALUE;
		int minFreeCapacity = Integer.MAX_VALUE;

		for (Solution solution : result) {
			int turnCount = solution.getTurns().size();
			int freeCapacity = solution.getFreeCapacity();

			if (turnCount < minTurnCount || (turnCount == minTurnCount && freeCapacity < minFreeCapacity)) {
				minTurnCount = turnCount;
				minFreeCapacity = freeCapacity;
			}
		}

		final int bestMinTurnCount = minTurnCount;
		final int bestMinFreeCapacity = minFreeCapacity;

		// filter out solutions with more turns and larger free capacity
		result.removeIf(solution -> solution.getTurns().size() > bestMinTurnCount
				|| (solution.getTurns().size() == bestMinTurnCount
						&& solution.getFreeCapacity() > bestMinFreeCapacity));

		return result;
	}

}
