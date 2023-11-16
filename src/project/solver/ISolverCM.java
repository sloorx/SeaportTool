package project.solver;

import java.util.List;

import project.Solution;

/**
 * @author Christoph Mehlis
 * 
 *         The ISolverCM interface defines the contract for classes that provide
 *         solving strategies for quests.
 */
public interface ISolverCM {

	/**
	 * Filters and returns the best solutions from the given list based on the
	 * solving strategy.
	 * 
	 * @param result The list of solutions to be filtered.
	 * @return The list of best solutions.
	 */
	public List<Solution> getBestSolutions(List<Solution> result);
}
