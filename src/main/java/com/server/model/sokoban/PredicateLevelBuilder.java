package com.server.model.sokoban;

import com.server.model.planner.predicate.And;
import com.server.model.planner.predicate.Predicate;

import java.util.ArrayList;

public class PredicateLevelBuilder {

    ArrayList<char[]> level = new ArrayList<>();
    And kb = new And(null);

    public And getGoal(){
        And goal = new And(null);
        Integer i = 0;
        for(Predicate p : kb.getPredicates()){
            if(p.getType().startsWith("targetAt")){
                goal.addPredicate(new Predicate("boxAt","b"+i.toString(), p.getValue()));
                i++;
            }
        }
        return goal;
    }

    public And getKnowledgebase(){

        int boxCount = 0;
        int targetCount = 0;
        for(int i = 0; i < level.size(); i++){
            for(int j = 0; j < level.get(i).length; j++){
                switch(level.get(i)[j]){
                    case 'x': kb.addPredicate(new Predicate("wallAt","",i+","+j)); break;
                    case ' ': kb.addPredicate(new Predicate("clearAt", "", i+","+j)); break;
                    case 'A': kb.addPredicate(new Predicate("sokobanAt", "", i+","+j)); break;
                    case 'B': kb.addPredicate(new Predicate("boxAt", "b"+boxCount, i+","+j)); boxCount++;  break;
                    case '@': kb.addPredicate(new Predicate("targetAt", "t"+targetCount, i+","+j)); targetCount++; break;
                }
            }
        }
        return kb;
    }

    public void setLevel(ArrayList<char[]> level) {
        this.level = level;
    }
}
