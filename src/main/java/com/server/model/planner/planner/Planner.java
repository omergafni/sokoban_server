package com.server.model.planner.planner;

import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.plannable.Plannable;

import java.util.List;

public interface Planner {

    List<PlanAction> computePlan(Plannable plannable, HeuristicMethods h);

}
