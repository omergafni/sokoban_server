package com.server.model.planner.predicate;

import java.util.HashSet;

/**
 * And is a logic "and" predicate
 */
public class And extends Predicate {

    private HashSet<Predicate> predicates;

    /**
     * Constructor
     * @param predicates is all the predicates that include in the AND predicate
     */
    public And(Predicate... predicates){
        super("and","","");
        this.predicates = new HashSet<>();
        if(predicates != null){
            for(Predicate p : predicates){
                this.predicates.add(p);
                updateDescription();
            }
        }
    }

    /**
     * Adding predicate to the current AND predicate
     * @param p
     */
    public void addPredicate(Predicate p){
        if(predicates == null)
            predicates = new HashSet<>();
        predicates.add(p);
        updateDescription();
    }

    public HashSet<Predicate> getPredicates(){
        return this.predicates;
    }

    @Override
    public boolean satisfies(Predicate p){
        for(Predicate pr : predicates)
            if(pr.satisfies(p))
                return true;
        return false;
    }


    /**
     * Updating the AND predicate state regarding the given effect
     * @param effect
     */
    public void update(And effect){
        effect.predicates.forEach((Predicate p)->this.predicates.removeIf((Predicate pr)-> p.contradicts(pr)));
        predicates.addAll(effect.predicates);
        updateDescription();
    }

    /**
     * Updating the AND predicate description
     */
    public void updateDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(Predicate p : predicates){
            sb.append(p.toString());
            sb.append(" ∧ ");
        }
        sb.delete(sb.length()-3,sb.length());
        sb.append("}");
        value = sb.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
