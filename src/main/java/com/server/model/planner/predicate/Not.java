package com.server.model.planner.predicate;

/**
 * Not is a logic "not" predicate
 */
public class Not extends Predicate {

    Predicate predicate;

    /**
     * Constructor
     * @param p is the regular predicate.
     */
    public Not(Predicate p) {
        super("!"+p.getType(), p.id, p.value);
        this.predicate = p;
    }

    @Override
    public boolean satisfies(Predicate p){
        return !predicate.satisfies(p);
    }

    @Override
    public boolean contradicts(Predicate p) {
        return !predicate.contradicts(p);
    }


}
