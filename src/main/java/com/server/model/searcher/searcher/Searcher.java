package com.server.model.searcher.searcher;

import com.server.model.searcher.searchable.Searchable;
import com.server.model.searcher.searchable.Solution;

/**
 * Searcher is an abstraction of a searcher objects
 * @param <T> is the state parameter that use to define a game state
 */
public interface Searcher<T> {

    Solution search(Searchable<T> s);
}
