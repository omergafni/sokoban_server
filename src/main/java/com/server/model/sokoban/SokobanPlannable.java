package com.server.model.sokoban;


import com.server.model.planner.plannable.PlanAction;
import com.server.model.planner.plannable.Plannable;
import com.server.model.planner.predicate.And;
import com.server.model.planner.predicate.Predicate;
import com.server.model.searcher.searchable.Action;
import com.server.model.searcher.searchable.Position;
import com.server.model.searcher.searchable.Solution;
import com.server.model.searcher.searchable.State;
import com.server.model.searcher.searcher.Searcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SokobanPlannable implements Plannable {

    private Searcher pSearcher; // Player searcher
    private Searcher bSearcher; // Box searcher
    private BoxSearchable bSearchable;
    private PlayerSearchable pSearchable;
    private And goal;
    private And kb;

    public SokobanPlannable(ArrayList<char[]> grid, Searcher pSearcher, Searcher bSearcher) {
        this.pSearcher = pSearcher;
        this.bSearcher = bSearcher;
        this.bSearchable = new BoxSearchable(grid);
        this.pSearchable = new PlayerSearchable(grid);
        PredicateLevelBuilder plb = new PredicateLevelBuilder();
        plb.setLevel(grid);
        kb = plb.getKnowledgebase();
        goal = plb.getGoal();
    }

    @Override
    public List<PlanAction> getSatisfyingActions(Predicate p) {

        LinkedList<PlanAction> plan = new LinkedList<>();
        SokobanPredicate sp = new SokobanPredicate(p);
        //SokobanPredicate sp = new SokobanPredicate(p.getType(),p.getId(),p.getValue());
        bSearchable.setGoalState(new State<>(sp.getPosition(),1));
        bSearchable.setInitialState(new State<>(getBoxInitialState(p),1));
        Solution boxSol = bSearcher.search(bSearchable);

        // Get player location on level load
        pSearchable.setInitialState(new State<>(pSearchable.getSokoPosition(),1));

        for(Action boxAction : boxSol.getActions()){

            pSearchable.setGoalState(new State<>(boxAction.getPushFrom(),1));
            Solution playerSol = pSearcher.search(pSearchable);
            for(Action playerAction : playerSol.getActions()){
               plan.addFirst(new PlanAction(playerAction.getAction()));
            }
            // Adding the last(=push) action to the plan
            plan.addFirst(new PlanAction(boxAction.getAction()));
            // Set new player location to the current box position (after push)
            pSearchable.setInitialState(new State<>(boxAction.getCurrentBoxPos(),1));

            // Updating grid after each box move action
            Position boxInitial = boxAction.getCurrentBoxPos();
            Position boxFinal = boxAction.getNextPosition();
            pSearchable.updateGrid(boxInitial,boxFinal);
        }
        if(plan.size() == 0) // Stop here if no action is required
            return plan;

        // This PlanAction is the last action to put the box on target + post-condition effect update
        plan.removeFirst();
        Action lastBoxAction = boxSol.getActions().getLast();
        SokobanPredicate playerAt = new SokobanPredicate("playerAt","",lastBoxAction.getCurrentBoxPos().toString());
        SokobanPredicate boxAt = new SokobanPredicate(p.getType(),p.getId(),p.getValue());
        plan.addFirst(new PlanAction(lastBoxAction.getAction(),null,new And(boxAt,playerAt)));

        // Updating grid after final box solution (note that last box movement is already updated for pSearchable!)
        Position boxInitial = getBoxInitialState(p);
        Position boxFinal = bSearchable.getGoalState().getState();
        Position playerInitial = pSearchable.getSokoPosition();
        Position playerFinal = lastBoxAction.getCurrentBoxPos();
        bSearchable.updateGrid(boxInitial,boxFinal);
        bSearchable.updateGrid(playerInitial,playerFinal);
        pSearchable.updateGrid(playerInitial,playerFinal);

        return plan;
    }

    @Override
    public And getGoal() {
        return goal;
    }

    @Override
    public And getKnowledgeBase() {
        return kb;
    }

    private Position getBoxInitialState(Predicate p){

        Position pos = null;
        for(Predicate pr : kb.getPredicates()){
            if(pr.getId().equals(p.getId())){
                SokobanPredicate sp = new SokobanPredicate(pr);
                pos = sp.getPosition();
            }
        }
        return pos;
    }
}
