package com.nick.wood.game_of_life.model;

public interface Universe {
	boolean isCellAlive(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement);
}
