package com.server.model.sokoban;


import com.server.model.searcher.searchable.Action;
import com.server.model.searcher.searchable.Position;
import com.server.model.searcher.searchable.Searchable;
import com.server.model.searcher.searchable.State;

import java.util.ArrayList;

public abstract class SokobanCommonSearchable implements Searchable<Position> {

    protected ArrayList<char[]> grid = new ArrayList<>();
    protected State<Position> initialState = new State<>(null,0,null,new Action(null));
    protected State<Position> goalState = new State<>(null,0,null,new Action(null));


    public SokobanCommonSearchable(ArrayList<char[]> grid) {
        this.grid = grid;
    }


    public void setInitialState(State<Position> initialState) {
        this.initialState = initialState;
    }

    public void setGoalState(State<Position> goalState) {
        this.goalState = goalState;
    }

    public void updateGrid(Position objectPosition, Position newPosition){

        // Make a copy of the current grid
        ArrayList<char[]> newGrid = new ArrayList<>();
        for(int i = 0; i < grid.size(); i++){
            newGrid.add(new char[grid.get(i).length]);
            for(int j = 0; j < grid.get(i).length; j++){
                newGrid.get(i)[j] = grid.get(i)[j];
            }
        }
        // Copy the worldObject character from the old position
        char c = newGrid.get(objectPosition.getRow())[objectPosition.getCol()];
        // To the new position
        newGrid.get(newPosition.getRow())[newPosition.getCol()] = c;
        // Clear the worldObject from old position
        newGrid.get(objectPosition.getRow())[objectPosition.getCol()] = ' ';
        grid = newGrid;
    }

    public char getNeighbor(Position p, Direction d){

        char neighbor = '?';
        int row = p.getRow();
        int col = p.getCol();

        if(d == Direction.UP){
            neighbor = grid.get(row-1)[col];
        }
        else if(d == Direction.DOWN){
            neighbor = grid.get(row+1)[col];
        }
        else if(d == Direction.LEFT){
            neighbor = grid.get(row)[col-1];
        }
        else if(d == Direction.RIGHT){
            neighbor = grid.get(row)[col+1];
        }
        return neighbor;
    }
    @Override
    public State<Position> getInitialState() {
        return initialState;
    }

    @Override
    public State<Position> getGoalState() {
        return goalState;
    }


}
