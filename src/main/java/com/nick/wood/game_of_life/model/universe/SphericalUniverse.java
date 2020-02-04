package com.nick.wood.game_of_life.model.universe;

import com.nick.wood.game_of_life.model.State;

public class SphericalUniverse implements Universe {

	@Override
	public boolean isCellAlive(State[][] matrix, int rowIndex, int colIndex, int rowDisplacement, int colDisplacement) {
		while (0 > rowIndex) {
			rowIndex += matrix.length;
		}
		while (rowIndex >= matrix.length) {
			rowIndex -= matrix.length;
		}

		int targetRow = rowIndex + rowDisplacement;
		while (0 > targetRow) {
			targetRow += matrix.length;
		}
		while (targetRow >= matrix.length) {
			targetRow -= matrix.length;
		}

		while (0 > colIndex) {
			colIndex += matrix[rowIndex].length;
		}
		while (colIndex >= matrix[rowIndex].length) {
			colIndex -= matrix[rowIndex].length;
		}

		int targetCol = colIndex + colDisplacement;
		while (0 > targetCol) {
			targetCol += matrix[targetRow].length;
		}
		while (targetCol >= matrix[targetRow].length) {
			targetCol -= matrix[targetRow].length;
		}


		return matrix[targetRow][targetCol].equals(State.ALIVE);
	}
}
