package com.nick.wood.game_of_life;

import com.nick.wood.game_of_life.model.MainModel;
import com.nick.wood.game_of_life.model.UniverseShape;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        int width = 1000;
        int height = 1000;

        ExecutorService executor = Executors.newFixedThreadPool(1);

        MainModel mainModel = new MainModel(width, height, UniverseShape.SPHERE);

        executor.submit(mainModel);

    }
}
