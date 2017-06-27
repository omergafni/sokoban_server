package com.server.model.sokoban;


import com.server.model.planner.predicate.Predicate;
import com.server.model.searcher.searchable.Position;

public class SokobanPredicate extends Predicate {

    public SokobanPredicate(Predicate p){
        super(p.getType(),p.getId(),p.getValue());
    }
    public SokobanPredicate(String type, String id, String value) {
        super(type, id, value);
    }

    @Override
    public boolean contradicts(Predicate p) {
        return super.contradicts(p) || (!id.equals(p.getId()) && value.equals(p.getValue()));
    }

    public Position getPosition(){
        String[] s = getValue().split(",");
        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);
        return new Position(x,y);
    }



}
