package com.server.model.searcher.searcher;

import com.server.model.searcher.searchable.Searchable;
import com.server.model.searcher.searchable.Solution;

public interface Searcher<T> {

    Solution search(Searchable<T> s);

}
