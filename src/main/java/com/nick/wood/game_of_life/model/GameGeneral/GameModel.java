package com.nick.wood.game_of_life.model.GameGeneral;

import com.nick.wood.rendering.Window;

public class GameModel implements Runnable {

    private double FPS = 1;
    private final Window window;
    private final Game game;

    public GameModel(int width, int height, Game game) {

        this.game = game;

        this.window = new Window(
                width,
                height,
                game::drawUserStates,
                function -> FPS = function.apply(FPS));

    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();

        double deltaSeconds = 0.0;

        while (true) {

            long now = System.nanoTime();

            deltaSeconds += (now - lastTime) / 1000000000.0;

            while (deltaSeconds >= 1/FPS) {

                 game.update();

                deltaSeconds = 0.0;

            }

            window.render(game.getPixelMatrix());

            window.setTitle(FPS + ": q = slow down, w = speed up");

            lastTime = now;

        }
    }

}
