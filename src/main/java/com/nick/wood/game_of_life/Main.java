package com.nick.wood.game_of_life;

import com.nick.wood.game_of_life.model.GameGeneral.GameModel;
import com.nick.wood.game_of_life.model.GameOfLife;
import com.nick.wood.game_of_life.model.universe.SphericalUniverse;
import com.nick.wood.game_of_life.model.universe.Universe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        int width = 1000;
        int height = 800;

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Universe universe = new SphericalUniverse();
        GameOfLife gameOfLife = new GameOfLife(width, height, universe);

        GameModel gameModel = new GameModel(width, height, gameOfLife);

        executor.submit(gameModel);

    }
}
