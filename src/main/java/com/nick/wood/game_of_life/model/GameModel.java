package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.model.universe.Universe;
import com.nick.wood.rendering.Window;

import java.util.Arrays;
import java.util.Random;

public class GameModel implements Runnable {

    private double FPS = 1;

    private final Universe universe;

    private final Random random = new Random();

    private final Window window;

    private State[][] allCells;
    private final int[][] pixelMatrix;

    public GameModel(int width, int height, Universe universe) {

        this.universe = universe;

        this.allCells = new State[width][height];
        this.pixelMatrix = new int[width][height];

        this.window = new Window(
                width,
                height,
                (x, y) -> allCells[x][y] = State.ALIVE,
                function -> FPS = function.apply(FPS));

        getInitialCells();

    }

    State[][] update(State[][] allCells, Universe universe) {

        State[][] newCells = new State[allCells.length][];

        for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {

            newCells[rowIndex] = new State[allCells[rowIndex].length];

            for (int colIndex = 0; colIndex < allCells[rowIndex].length; colIndex++) {

                if (allCells[rowIndex][colIndex].equals(State.ALIVE)) {

                    int neighbours = checkNumOfAliveNeighbours(allCells, rowIndex, colIndex, universe);

                    newCells[rowIndex][colIndex] = (neighbours == 2 || neighbours == 3) ? State.ALIVE : State.DEAD;

                } else if (allCells[rowIndex][colIndex].equals(State.DEAD)) {

                    int neighbours = checkNumOfAliveNeighbours(allCells, rowIndex, colIndex, universe);

                    newCells[rowIndex][colIndex] = (neighbours == 3) ? State.ALIVE : State.DEAD;

                } else {

                    newCells[rowIndex][colIndex] = State.DEAD;

                }

            }

        }

        return newCells;

    }

    int checkNumOfAliveNeighbours(State[][] matrix, int rowIndex, int colIndex, Universe universe) {
        int neighbours = 0;

        for (int rowDisplacement = -1; rowDisplacement <= 1; rowDisplacement++) {
            for (int colDisplacement = -1; colDisplacement <= 1; colDisplacement++) {
                if (!(rowDisplacement == 0 && colDisplacement == 0)) {
                    neighbours += universe.isCellAlive(matrix, rowIndex, colIndex, rowDisplacement, colDisplacement) ? 1 : 0;
                }
            }
        }

        return neighbours;
    }



    private void getInitialCells() {

        for (State[] allCell : allCells) {
            Arrays.fill(allCell, State.DEAD);
        }

        // Test shapes
        // block
        //allCells[10][10] = State.ALIVE;
        //allCells[10][11] = State.ALIVE;
        //allCells[11][10] = State.ALIVE;
        //allCells[11][11] = State.ALIVE;

        // blinker
        //allCells[10][10] = State.ALIVE;
        //allCells[11][10] = State.ALIVE;
        //allCells[12][10] = State.ALIVE;

        // glider
        //allCells[10][11] = State.ALIVE;
        //allCells[11][12] = State.ALIVE;
        //allCells[12][10] = State.ALIVE;
        //allCells[12][11] = State.ALIVE;
        //allCells[12][12] = State.ALIVE;

        // random
        for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {
            for (int colIndex = 0; colIndex < allCells[rowIndex].length; colIndex++) {
                if (random.nextInt(1_000_000) < 700_000) {
                    allCells[rowIndex][colIndex] = State.ALIVE;
                }
            }
        }
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();

        double deltaSeconds = 0.0;

        while (true) {

            long now = System.nanoTime();

            deltaSeconds += (now - lastTime) / 1000000000.0;

            while (deltaSeconds >= 1/FPS) {

                allCells = update(allCells, universe);

                deltaSeconds = 0.0;

            }

            editPixelMatrix(allCells);

            window.render(pixelMatrix);

            window.setTitle(FPS + ": q = slow down, w = speed up");

            lastTime = now;

        }
    }

    private void editPixelMatrix(State[][] cells) {
        for (int rowIndex = 0; rowIndex < cells.length; rowIndex++) {
            for (int colIndex = 0; colIndex < cells[rowIndex].length; colIndex++) {
                pixelMatrix[rowIndex][colIndex] = cells[rowIndex][colIndex].getColor().hashCode();
            }
        }
    }

}
