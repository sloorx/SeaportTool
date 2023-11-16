package project.solver;

import java.util.List;

import project.Quest;
import project.QuestSolver;
import project.Solution;

/**
 * @author Christoph Mehlis
 * 
 *         The class implements the QuestSolver and ISolverCM interfaces to
 *         solve quests with a focus on minimizing the number of turns. It uses
 *         the SolverCM class with the time solver configuration.
 */
public class TimeSolverCM implements QuestSolver, ISolverCM {

	/**
	 * Solves the given quest using the SolverCM class with the time solver
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
	 * the best solutions. The criteria include the lowest number of turns and the
	 * lowest free capacity.
	 * 
	 * @param result The list of solutions to be filtered.
	 * @return A list of the best solutions based on the number of turns and free
	 *         capacity.
	 */
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
