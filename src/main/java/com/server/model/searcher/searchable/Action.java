package com.server.model.searcher.searchable;

/**
 * Action represents the action that need to be execute to switch to another state.
 */
public class Action {

    private String action;
    private Position pushFrom; // Player position precondition
    private Position currentBoxPos;
    private Position nextPosition; // Box post condition

    public Action(String action) {
        this.action = action;
    }

    /**
     * Constructor
     * @param action is the action
     * @param pushFrom is the position that the sokoban player need to be placed to be able to push a box
     * @param currentBoxPos is the current box position
     * @param nextPosition is the box position after the action will happen
     */
    public Action(String action, Position pushFrom, Position currentBoxPos, Position nextPosition){
        this.action = action;
        this.pushFrom = pushFrom;
        this.currentBoxPos = currentBoxPos;
        this.nextPosition = nextPosition;
    }

    public Position getPushFrom() {
        return pushFrom;
    }

    public Position getNextPosition() {
        return nextPosition;
    }

    public Position getCurrentBoxPos() {
        return currentBoxPos;
    }

    public String getAction(){ return this.action; }

    @Override
    public String toString() {
        return action;
    }

    @Override
    public boolean equals(Object obj) {
        Action a = (Action)obj;
        return a.action.equals(action);
    }

    @Override
    public int hashCode() {
        return action.hashCode();
    }
}
