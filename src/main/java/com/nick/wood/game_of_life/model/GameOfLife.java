package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.model.GameGeneral.Game;
import com.nick.wood.game_of_life.model.universe.Universe;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameOfLife implements Game {

	private final Universe universe;
	Random random = new Random();
	private State[][] states;
	private final int[][] pixelMatrix;
	private final Map<State, Interaction[]> rulesMap = new HashMap<>();

	public GameOfLife(Universe universe, State[][] startingStateMatrix) {

		this.states = startingStateMatrix;
		this.pixelMatrix = new int[startingStateMatrix.length][startingStateMatrix[0].length];
		this.universe = universe;

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

	public GameOfLife(int width, int height, Universe universe) {

		this.states = new State[width][height];
		this.pixelMatrix = new int[width][height];
		this.universe = universe;
		getInitialCells();

		createRuleMap();

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

	@Override
	public void drawUserStates(Integer x, Integer y) {
		states[x][y] = State.ALIVE;
	}

	@Override
	public int[][] getPixelMatrix() {
		for (int rowIndex = 0; rowIndex < states.length; rowIndex++) {
			for (int colIndex = 0; colIndex < states[rowIndex].length; colIndex++) {
				pixelMatrix[rowIndex][colIndex] = states[rowIndex][colIndex].getColor().hashCode();
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



	private void getInitialCells() {

		for (State[] allCell : states) {
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
		for (int rowIndex = 0; rowIndex < states.length; rowIndex++) {
			for (int colIndex = 0; colIndex < states[rowIndex].length; colIndex++) {
				if (random.nextInt(1_000_000) < 700_000) {
					states[rowIndex][colIndex] = State.ALIVE;
				}
			}
		}
	}

	public State[][] getStates() {
		return states;
	}
}
