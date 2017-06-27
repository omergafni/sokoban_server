package com.server.model.sokoban;

import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.planner.HeuristicMethods;
import com.server.model.planner.planner.Planner;

import java.util.List;

public class SokobanSolver {

    private SokobanPlannable plannable;
    private Planner planner;
    private HeuristicMethods h;

    public SokobanSolver(SokobanPlannable sokoPlannable, Planner planner) {
        this.planner = planner;
        this.plannable = sokoPlannable;

    }

    public List<PlanAction> solve(){
        if(h == null)
            return planner.computePlan(plannable,null);
        else
            return planner.computePlan(plannable,h);
    }


    public void setHeuristic(HeuristicMethods h) {
        this.h = h;
    }
}

