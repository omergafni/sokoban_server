package com.server.model.planner.plannable;

import com.server.model.planner.predicate.And;

/**
 * PlanAction represents the action for the sokoban character
 */
public class PlanAction {

    private String name;
    private And preConditions;
    private And postConditions;

    public PlanAction(String name) {
        this.name = name;
        this.postConditions = null;
        this.postConditions = null;
    }

    /**
     * Constructor
     * @param name is the action name
     * @param preConditions is the pre-condition that must to be achieved before that action can execute
     * @param postConditions is the condition after the action is executed
     */
    public PlanAction(String name, And preConditions, And postConditions) {
        this.preConditions = preConditions;
        this.postConditions = postConditions;
        this.name = name;
    }

    public And getPreCondition(){
        return this.preConditions;
    }

    public And getPostCondition(){
        return this.postConditions;
    }

    @Override
    public String toString() {
        return name;
    }
}
