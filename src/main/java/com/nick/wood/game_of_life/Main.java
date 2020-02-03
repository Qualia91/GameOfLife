package com.nick.wood.game_of_life;

import com.nick.wood.game_of_life.model.MainModel;
import com.nick.wood.game_of_life.model.SphericalUniverse;
import com.nick.wood.game_of_life.model.Universe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        int width = 1000;
        int height = 800;

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Universe universe = new SphericalUniverse();

        MainModel mainModel = new MainModel(width, height, universe);

        executor.submit(mainModel);

    }
}
