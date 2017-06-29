package com.server.model.planner.planner;

import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.plannable.Plannable;

import java.util.List;

/**
 * Planner is the plan algorithm interface
 */
public interface Planner {

    /**
     * Compute the solution plan for the plannable object with the given heuristic
     * @param plannable is the object that the planner compute a solution for
     * @param h is the heuristic method
     * @return the plan action list
     */
    List<PlanAction> computePlan(Plannable plannable, HeuristicMethods h);

}
