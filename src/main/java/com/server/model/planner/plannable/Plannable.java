package com.server.model.planner.plannable;

import com.server.model.planner.predicate.And;
import com.server.model.planner.predicate.Predicate;

import java.util.List;

public interface Plannable {

    And getGoal();
    And getKnowledgeBase();
    List<PlanAction> getSatisfyingActions(Predicate p);

}
