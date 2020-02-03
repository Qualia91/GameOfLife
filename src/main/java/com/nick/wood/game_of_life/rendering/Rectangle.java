package com.nick.wood.game_of_life.rendering;

public class Rectangle {

    public int x;
    public int y;
    public int w;
    public int h;
    
    private int[] pixels;

    public Rectangle(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
    
    public void generateGraphics(int color) {
        
        pixels = new int[w * h];
        for (int x = 0; x < w; x++) {

            for (int y = 0; y < h; y++) {

                pixels[x + y * w] = color;

            }

        }
        
    }

    public boolean intersects(Rectangle r) {

        if (x > r.x + r.w || r.x > x + w) {
            return false;
        }

        if (y > r.y + r.h || r.y > y + h) {
            return false;
        }

        return true;

    }

    public int[] getPixels() {
        if (pixels != null) {
            return pixels;
        } else {
            System.out.println("No pixels in camera");
            return null;
        }
    }

    public void setPixels(int[] pixels) {
        this.pixels = pixels;
    }

    public void generateGraphics(int color, int borderWidth) {
        pixels = new int[w * h];

        for (int currentX = 0; currentX < w; currentX++) {

            if (currentX < borderWidth || currentX > (w - borderWidth)) {

                for (int currentY = 0; currentY < h; currentY++) {

                    pixels[currentX + currentY * w] = color;

                }

            } else {

                for (int currentY = 0; currentY < h; currentY++) {

                    if (currentY < borderWidth || currentY > (h - borderWidth)) {

                        pixels[currentX + currentY * w] = color;

                    }

                }

            }

        }

    }

}
