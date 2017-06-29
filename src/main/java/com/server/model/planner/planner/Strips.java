package com.server.model.planner.planner;

import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.plannable.Plannable;
import com.server.model.planner.predicate.And;
import com.server.model.planner.predicate.Predicate;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Strips is a planning algorithm
 */
public class Strips implements Planner {

    @Override
    public List<PlanAction> computePlan(Plannable plannable, HeuristicMethods h) {

        Stack<Object> stack = new Stack<>();
        if (h == null) { // No heuristic is provided
            stack.push(plannable.getGoal());
        }
        else{ // Try optimizing with heuristic
            stack.push(h.getGoal(plannable.getKnowledgeBase()));
        }
        And currState = plannable.getKnowledgeBase();
        List<PlanAction> plan = new LinkedList<>();

        while (!stack.isEmpty()) {
            Object obj = stack.pop();

            if (obj instanceof And) {
                And clause = (And) obj;
                for (Predicate p : clause.getPredicates()) {
                    stack.push(p);
                }
            } else if (obj instanceof Predicate) {
                Predicate p = (Predicate) obj;
                if (!currState.satisfies(p)) { // predicate is not clause and unsatisfied
                    List<PlanAction> actionList = plannable.getSatisfyingActions(p);
                    for (PlanAction a : actionList) {
                        stack.push(a);
                    }
                }
            } else if (obj instanceof PlanAction) {
                PlanAction a = (PlanAction) obj;
                if (a.getPostCondition() != null) {
                    currState.update(a.getPostCondition());
                }
                plan.add(a); // adding the action to the solution
            } else {
                throw new IllegalArgumentException("Invalid object in stack");
            }
        }
        return plan;
    }
}