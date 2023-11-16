package project.solver;

import java.util.List;

import project.Quest;
import project.QuestSolver;
import project.Solution;

/**
 * @author Christoph Mehlis
 * 
 *         The class implements the QuestSolver and ISolverCM interfaces to
 *         solve quests with a focus on minimizing free capacity. It uses the
 *         SolverCM class with the capacity solver configuration.
 */
public class CapacitySolverCM implements QuestSolver, ISolverCM {

	/**
	 * Solves the given quest using the SolverCM class with the capacity solver
	 * configuration.
	 * 
	 * @param q The quest to be solved.
	 * @return A list of solutions to the quest.
	 */
	@Override
	public List<Solution> solve(Quest q) {
		SolverCM sCM = new SolverCM(this);
		return sCM.solve(q);
	}

	/**
	 * The method filters a list of solutions based on specific criteria to retain
	 * the best solutions. The criteria include the lowest free capacity and the
	 * lowest turn count.
	 * 
	 * @param result The list of solutions to be filtered.
	 * @return A list of the best solutions based on free capacity and turn count.
	 */
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
