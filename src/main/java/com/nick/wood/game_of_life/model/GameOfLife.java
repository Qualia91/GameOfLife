package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.model.universe.Universe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameOfLife {

	private final Universe universe;
	Random random = new Random();
	private State[][] states;
	private final int[][] pixelMatrix;
	private final Map<State, Interaction[]> rulesMap = new HashMap<>();
	private final int cellSize;

	public GameOfLife(Universe universe, State[][] startingStateMatrix, int cellSize) {

		this.states = startingStateMatrix;
		this.pixelMatrix = new int[startingStateMatrix.length*cellSize][startingStateMatrix[0].length*cellSize];
		this.universe = universe;
		this.cellSize = cellSize;

		createRuleMap();

	}

	public GameOfLife(int width, int height, Universe universe, int cellSize) {
		this.states = new State[width/cellSize][height/cellSize];
		this.pixelMatrix = new int[width][height];
		this.universe = universe;
		this.cellSize = cellSize;
		getInitialCells(Init.RANDOM, 0, 0);

		createRuleMap();

	}

	public GameOfLife(int width, int height, Universe universe, int cellSize, Init init, int topLeftX, int topLeftY) {
		this.states = new State[width/cellSize][height/cellSize];
		this.pixelMatrix = new int[width][height];
		this.universe = universe;
		this.cellSize = cellSize;
		getInitialCells(init, topLeftX, topLeftY);

		createRuleMap();

	}

	private void createRuleMap() {
		Interaction[] aliveInteractions = new Interaction[] {
				new Interaction(State.ALIVE, new int[] {2, 3})
		};
		rulesMap.put(State.ALIVE, aliveInteractions);

		Interaction[] deadInteractions = new Interaction[] {
				new Interaction(State.ALIVE, new int[] {3})
		};
		rulesMap.put(State.DEAD, deadInteractions);
	}

	public void update() {

		State[][] newStates = new State[states.length][states[0].length];

		for (int rowIndex = 0; rowIndex < states.length; rowIndex++) {

			for (int colIndex = 0; colIndex < states[rowIndex].length; colIndex++) {

				for (Map.Entry<State, Interaction[]> stateEntry : rulesMap.entrySet()) {
					if (states[rowIndex][colIndex].equals(stateEntry.getKey())) {
						for (Interaction interaction : stateEntry.getValue()) {
							int neighbours = checkNumOfNeighboursInState(states, interaction.interactingState, rowIndex, colIndex, universe);

							newStates[rowIndex][colIndex] = interaction.getPredicateForAffectingCriteria().test(neighbours) ? State.ALIVE : State.DEAD;
						}
					}
				}
			}
		}

		states = newStates;

	}

	public void drawUserStates(Integer x, Integer y) {
		states[x/cellSize][y/cellSize] = State.ALIVE;
	}

	public int[][] getPixelMatrix() {
		for (int rowIndex = 0; rowIndex < states.length; rowIndex++) {
			for (int colIndex = 0; colIndex < states[rowIndex].length; colIndex++) {
				for (int i = 0; i < cellSize; i++) {
					for (int j = 0; j < cellSize; j++) {
						pixelMatrix[(rowIndex * (cellSize-1)) + rowIndex + i][(colIndex * (cellSize-1)) + colIndex + j] = states[rowIndex][colIndex].getColor().hashCode();
					}
				}
			}
		}
		return pixelMatrix;
	}

	int checkNumOfNeighboursInState(State[][] matrix, State interactingState, int rowIndex, int colIndex, Universe universe) {
		int neighbours = 0;

		for (int rowDisplacement = -1; rowDisplacement <= 1; rowDisplacement++) {
			for (int colDisplacement = -1; colDisplacement <= 1; colDisplacement++) {
				if (!(rowDisplacement == 0 && colDisplacement == 0)) {
					neighbours += universe.isCell(matrix, rowIndex, colIndex, rowDisplacement, colDisplacement, interactingState) ? 1 : 0;
				}
			}
		}

		return neighbours;
	}



	private void getInitialCells(Init init, int topLeftX, int topLeftY) {

		for (State[] allCell : states) {
			Arrays.fill(allCell, State.DEAD);
		}

		switch (init) {
			case BLOCK:
				states[topLeftX][topLeftY] = State.ALIVE;
				states[topLeftX][topLeftY+1] = State.ALIVE;
				states[topLeftX+1][topLeftY] = State.ALIVE;
				states[topLeftX+1][topLeftY+1] = State.ALIVE;
				break;

			case BLINKER:
				states[topLeftX][topLeftY] = State.ALIVE;
				states[topLeftX+1][topLeftY] = State.ALIVE;
				states[topLeftX+2][topLeftY] = State.ALIVE;
				break;

			case GLIDER:
				states[topLeftX][topLeftY+1] = State.ALIVE;
				states[topLeftX+1][topLeftY+2] = State.ALIVE;
				states[topLeftX+2][topLeftY] = State.ALIVE;
				states[topLeftX+2][topLeftY+1] = State.ALIVE;
				states[topLeftX+2][topLeftY+2] = State.ALIVE;
				break;

			case RANDOM:
				for (int rowIndex = 0; rowIndex < states.length; rowIndex++) {
					for (int colIndex = 0; colIndex < states[rowIndex].length; colIndex++) {
						if (random.nextInt(1_000_000) < 700_000) {
							states[rowIndex][colIndex] = State.ALIVE;
						}
					}
				}
				break;

		}
	}

	public State[][] getStates() {
		return states;
	}
}
