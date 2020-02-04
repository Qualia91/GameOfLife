package com.nick.wood.game_of_life.model.universe;

import com.nick.wood.game_of_life.model.State;

public interface Universe {
	boolean isCellAlive(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement);

	boolean isCell(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement, State interactingState);
}
