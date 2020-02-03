package sample;

import javafx.fxml.Initializable;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MainModel {

    private final int width;
    private final int height;

    private final Random random = new Random();

    private int[][] allCells;

    private final ScheduledExecutorService executor;

    public MainModel(int width, int height, ScheduledExecutorService executor) {

        this.width = width;
        this.height = height;
        this.executor = executor;

        allCells = new int[width][height];

        getInitialCells();

        this.executor.scheduleAtFixedRate(
                () -> {
                    System.out.println("Run");
                    int[][] newCells = new int[width][height];

                    for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {

                        for (int colIndex = 0; colIndex < allCells[rowIndex].length; colIndex++) {

                            if (allCells[rowIndex][colIndex] == 1) {

                                int neighbours = 0;

                                if (allCells[rowIndex][colIndex - 1] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex][colIndex + 1] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex - 1][colIndex] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex + 1][colIndex] == 1) {
                                    neighbours++;
                                }

                                if (neighbours == 2 || neighbours == 3) {
                                    newCells[rowIndex][colIndex] = 1;
                                } else {
                                    newCells[rowIndex][colIndex] = 0;
                                }

                            } else if (allCells[rowIndex][colIndex] == 0) {

                                int neighbours = 0;

                                if (allCells[rowIndex][colIndex - 1] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex][colIndex + 1] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex - 1][colIndex] == 1) {
                                    neighbours++;
                                }
                                if (allCells[rowIndex + 1][colIndex] == 1) {
                                    neighbours++;
                                }

                                if (neighbours == 3) {
                                    newCells[rowIndex][colIndex] = 1;
                                } else {
                                    newCells[rowIndex][colIndex] = 0;
                                }

                            } else {

                                newCells[rowIndex][colIndex] = 0;

                            }

                        }

                    }

                    allCells = newCells;

                }, 0L, 1, TimeUnit.SECONDS);


    }

    private void getInitialCells() {

        for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {
            for (int colIndex = 0; colIndex < allCells[rowIndex].length; colIndex++) {
                allCells[rowIndex][colIndex] = 1;
                /*
                if (random.nextInt(1_000_000) < 1_000_000) {
                    allCells[rowIndex][colIndex] = 1;
                } else {
                    allCells[rowIndex][colIndex] = 0;
                }
                */
            }
        }


    }

    public int[][] getCells() {
        return allCells;
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }
}
