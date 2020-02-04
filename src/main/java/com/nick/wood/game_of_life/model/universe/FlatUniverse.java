package com.nick.wood.game_of_life.model.universe;

import com.nick.wood.game_of_life.model.State;

public class FlatUniverse implements Universe {

	@Override
	public boolean isCellAlive(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement) {
		if (0 <= rowIndex && rowIndex < matrix.length) {
			if (0 <= (rowIndex + rowDisplacement) && (rowIndex + rowDisplacement) < matrix.length) {
				if (0 <= colIndex && colIndex < matrix[rowIndex + rowDisplacement].length) {
					if (0 <= (colIndex + colDisplacement) && (colIndex + colDisplacement) < matrix[rowIndex + rowDisplacement].length) {
						return matrix[rowIndex + rowDisplacement][colIndex + colDisplacement].equals(State.ALIVE);
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean isCell(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement, State interactingState) {
		if (0 <= rowIndex && rowIndex < matrix.length) {
			if (0 <= (rowIndex + rowDisplacement) && (rowIndex + rowDisplacement) < matrix.length) {
				if (0 <= colIndex && colIndex < matrix[rowIndex + rowDisplacement].length) {
					if (0 <= (colIndex + colDisplacement) && (colIndex + colDisplacement) < matrix[rowIndex + rowDisplacement].length) {
						return matrix[rowIndex + rowDisplacement][colIndex + colDisplacement].equals(interactingState);
					}
				}
			}
		}
		return false;
	}
}
