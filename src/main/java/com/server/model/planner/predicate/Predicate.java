package com.server.model.planner.predicate;

public class Predicate {

    protected String type, id, value;

    /**
     * Constructor
     * @param type is the logic predicate name
     * @param id is the id of the predicate
     * @param value is the data of the predicate
     */
    public Predicate(String type, String id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    /**
     * Check if this predicate satisfies the given p
     * @param p
     * @return true if the given p is satisfied by this predicate, otherwise return false.
     */
    public boolean satisfies(Predicate p){
        return (type.equals(p.type) && (id.equals(p.id) || p.id.equals("?")) && value.equals(p.value));
    }

    /**
     * Check if this predicate contradicts the given p
     * @param p
     * @return true if the given p is contradict by this predicate, otherwise return false.
     */
    public boolean contradicts(Predicate p) {
        return (type.equals(p.type) && id.equals(p.id) && !value.equals(p.value));
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Predicate p = (Predicate)obj;
        return (type.equals(p.type) && id.equals(p.id) && value.equals(p.value));
    }

    @Override
    public String toString() {
        return "("+type+","+id+","+value+")";
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public String getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
