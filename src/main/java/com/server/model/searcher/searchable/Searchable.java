package com.server.model.searcher.searchable;

import java.util.ArrayList;

/**
 * Searchable is an abstraction of a searchable objects
 * @param <T> is the state parameter that use to define a game state
 */
public interface Searchable<T> {

    State<T> getInitialState();
    State<T> getGoalState();
    ArrayList<State<T>> getAllPossibleStates(State<T> s);

}
