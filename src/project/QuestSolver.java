package project;

import java.util.List;

/**
 * Classes implementing this interface create solutions that solve a given quest
 */
public interface QuestSolver {

    /**
     * Returns one or more solutions to a given quest (if possible)
     * @param q The quest to solve
     * @return A list of solutions
     */
    public List<Solution> solve(Quest q);
}
