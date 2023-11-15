package project.solver;

import java.util.List;

import project.Quest;
import project.QuestSolver;
import project.Solution;

public class CapacitySolverCM implements QuestSolver, ISolverCM {

	@Override
	public List<Solution> solve(Quest q) {
		SolverCM sCM = new SolverCM(this);
		return sCM.solve(q);
	}

	public List<Solution> getBestSolutions(List<Solution> result) {
		// get lowest free capacity and lowest turn count
		int minFreeCapacity = Integer.MAX_VALUE;
		int minTurnCount = Integer.MAX_VALUE;

		for (Solution solution : result) {
			int freeCapacity = solution.getFreeCapacity();
			int turnCount = solution.getTurns().size();

			if (freeCapacity < minFreeCapacity || (freeCapacity == minFreeCapacity && turnCount < minTurnCount)) {
				minFreeCapacity = freeCapacity;
				minTurnCount = turnCount;
			}
		}

		final int bestMinFreeCapacity = minFreeCapacity;
		final int bestMinTurnCount = minTurnCount;

		// filter out solutions with larger free capacity and more turns 
		result.removeIf(solution -> solution.getFreeCapacity() > bestMinFreeCapacity
				|| (solution.getFreeCapacity() == bestMinFreeCapacity
						&& solution.getTurns().size() > bestMinTurnCount));

		return result;
	}

}
