package com.server.model.searcher.searcher;

import com.server.model.searcher.searchable.Action;
import com.server.model.searcher.searchable.Solution;
import com.server.model.searcher.searchable.State;

import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * CommonSearcher is an abstraction of searchers objects
 * @param <T> is the state parameter
 */
public abstract class CommonSearcher<T> implements Searcher<T> {

    protected PriorityQueue<State<T>> openList;
    protected int evaluatedNodes;

    public CommonSearcher(){
        openList = new PriorityQueue<>();
        evaluatedNodes = 0;
    }

    protected State popOpenList(){
        evaluatedNodes++;
        return openList.poll();
    }

    /**
     * Back tracing the solution so the solution will show in the correct order
     * @param goalState is the state we will back trace from
     * @return the solution
     */
    protected Solution backTrace(State<T> goalState) {
        LinkedList<Action> actions = new LinkedList<>();
        State<T> currState = goalState;
        while (currState.getCameFrom() != null) {
            actions.addFirst(currState.getAction());
            currState = currState.getCameFrom();
        }
        Solution sol = new Solution();
        sol.setActions(actions);
        return sol;
    }
}
