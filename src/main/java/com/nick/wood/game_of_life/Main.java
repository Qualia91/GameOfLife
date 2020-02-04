package com.nick.wood.game_of_life;

import com.nick.wood.game_of_life.model.GameModel;
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

        GameModel gameModel = new GameModel(width, height, universe);

        executor.submit(gameModel);

    }
}
