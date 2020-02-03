package sample;

import javafx.collections.ObservableArray;

public class Cell {

    int x;
    int y;
    boolean alive;
    int liveNeighbours;


    public Cell(int xPos, int yPos) {
        this.x = xPos;
        this.y = yPos;
        this.alive = true;
    }
}
