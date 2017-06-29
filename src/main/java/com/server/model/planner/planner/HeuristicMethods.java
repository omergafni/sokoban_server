package com.server.model.planner.planner;

import com.server.model.planner.predicate.And;

/**
 * HeuristicMethods is an interface for heuristic methods that define the planner heuristic
 */
public interface HeuristicMethods {

    /**
     * Define the goal state that will be use by the planner.
     * @param knowledgeBase
     * @return the desired goal based on the given knowledge base.
     */
    And getGoal(And knowledgeBase);

}
