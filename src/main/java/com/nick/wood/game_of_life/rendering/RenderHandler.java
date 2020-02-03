package com.nick.wood.game_of_life.rendering;

import com.nick.wood.game_of_life.model.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class RenderHandler {

    private BufferedImage view;
    private int[] pixels;

    public RenderHandler(int width, int height) {

        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

    }

    public void render(Graphics g) {

        g.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);

    }

    public void changeScreenSize(int width, int height) {
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

    }

    private void setPixel(int pixel, int x, int y) {
        int pixelIndex = x + (y * view.getWidth());
        if (pixelIndex < pixels.length) {
            pixels[pixelIndex] = pixel;
        }
    }


    public void renderCells(State[][] cells) {
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells[x].length; y++) {
                setPixel(cells[x][y].getColor().hashCode(), x, y);
            }
        }
    }

    public void renderArray(int[] renderPixels,  int renderWidth, int renderHeight, int xPos, int yPos, int xZoom, int yZoom) {
        for (int y = 0; y < renderHeight; y++) {
            for (int x = 0; x < renderWidth; x++) {
                for (int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++) {
                    for (int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++) {
                        setPixel(renderPixels[x + y * renderWidth], (xPos + (x * xZoom) + xZoomPosition), (yPos + (y * yZoom) + yZoomPosition));
                    }
                }
            }
        }
    }

    public void renderRectangle(Rectangle rectangle, int xZoom, int yZoom) {
        int[] rectanglePixels = rectangle.getPixels();
        if (rectanglePixels != null) {
            renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x, rectangle.y, xZoom, yZoom);
        }
    }

    public void clear() {
        Arrays.fill(pixels, 0);
    }
}
