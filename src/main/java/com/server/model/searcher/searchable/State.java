package com.server.model.searcher.searchable;

/**
 * State represent a state of a searchable object
 * @param <T> describe the type of the state (i.e. <Position>)
 */
public class State<T> implements Comparable{

    private T state;
    private double cost;
    private State<T> cameFrom;
    private Action action;

    /**
     * Constructor
     * @param state is the state parameter (i.e. 'Position'>
     * @param cost is the cost for this state
     */
    public State(T state, double cost) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = null;
        this.action = null;
    }

    /**
     * Constructor
     * @param state is the state parameter (i.e. 'Position'>
     * @param cost is the cost for this state
     * @param cameFrom is the state before
     * @param action is the action that have to be made to achieve this state
     */
    public State(T state, double cost, State<T> cameFrom, Action action) {
        this.state = state;
        this.cost = cost;
        this.cameFrom = cameFrom;
        this.action = action;
    }

    public T getState() {return state;}

    public double getCost() {return cost;}

    public void setCost(double cost) {this.cost = cost;}

    public State<T> getCameFrom() {return cameFrom;}

    public void setCameFrom(State<T> cameFrom) {this.cameFrom = cameFrom;}

    public Action getAction() {return action;}

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        State<T> s = (State<T>)obj;
        return this.state.equals(s.getState());
    }

    @Override
    public int compareTo(Object o) {
        State<T> obj = (State<T>)o;
        return (int)(cost - obj.getCost());
    }
}
