package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.rendering.RenderHandler;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.util.Arrays;
import java.util.Random;
import java.awt.*;


public class MainModel extends JFrame implements Runnable {

    private double FPS = 1;
    private final RenderHandler renderHandler;
    private final Universe universe;
    private Canvas canvas = new Canvas();

    private final int width;
    private final int height;

    private final Random random = new Random();

    private State[][] allCells;

    public MainModel(int width, int height, Universe universe) {

        this.width = width;
        this.height = height;
        this.universe = universe;

        this.allCells = new State[width][height];

        getInitialCells();
        setupBindings();

        this.renderHandler = new RenderHandler(width, height);

        initialiseWindow();

    }

    private void setupBindings() {

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                renderHandler.changeScreenSize(getWidth(), getHeight());
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                allCells[MouseInfo.getPointerInfo().getLocation().x-canvas.getLocationOnScreen().x]
                        [MouseInfo.getPointerInfo().getLocation().y-canvas.getLocationOnScreen().y]
                        = State.ALIVE;
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                allCells[MouseInfo.getPointerInfo().getLocation().x-canvas.getLocationOnScreen().x]
                        [MouseInfo.getPointerInfo().getLocation().y-canvas.getLocationOnScreen().y]
                        = State.ALIVE;
            }
        });

        canvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'q') {
                    FPS /= 2;
                } else if (e.getKeyChar() == 'w') {
                    FPS *= 2;
                }

                setTitle(FPS + ": q = slow down, w = speed up");
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    State[][] update(State[][] allCells, int width, int height, Universe universe) {

        State[][] newCells = new State[width][height];

        for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {

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

    public void render() {
        // get buffered strategy
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();

        // get graphics from buffered strategy
        Graphics g = bufferStrategy.getDrawGraphics();

        // calls paint in JFrame we are extending. Is needed, says in javadoc
        // if this is not in other things aren't drawn that don't appear in this function
        // ie background
        super.paint(g);

        renderHandler.clear();

        renderHandler.renderCells(allCells.clone());

        renderHandler.render(g);

        g.dispose();

        // tells that you are done with writing to the buffer and
        // puts it in the queue to be shown
        bufferStrategy.show();
    }

    private void initialiseWindow() {
        this.setResizable(false);

        // TO close the frame when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // sets starting position and size
        setBounds(0, 0, width, height);

        // puts the frame in the center of the screen
        setLocationRelativeTo(null);

        // adds canvas to jframe
        add(canvas);

        // sets window visible. needs to go above buffered strategy
        // so it has something to attach to
        setVisible(true);

        // creates buffers
        canvas.createBufferStrategy(4);

        setTitle(FPS + ": q = slow down, w = speed up");
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

                allCells = update(allCells, width, height, universe);

                deltaSeconds = 0.0;

            }

            render();

            lastTime = now;

        }
    }
}
