package com.nick.wood.game_of_life.model.GameGeneral;

public interface Game {
	void update();

	void drawUserStates(Integer x, Integer y);

	int[][] getPixelMatrix();
}
