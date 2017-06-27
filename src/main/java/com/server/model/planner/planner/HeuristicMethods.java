package com.server.model.planner.planner;

import com.server.model.planner.predicate.And;

public interface HeuristicMethods {

    And getGoal(And knowledgeBase);

}
