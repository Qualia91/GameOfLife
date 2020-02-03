package com.nick.wood.game_of_life.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MainModelTest {

	@Test
	void isCellAliveTest() {
		MainModel mainModel = new MainModel(10, 10, UniverseShape.FLAT);

		State[][] stateMatrix = new State[3][3];

		Random random = new Random();

		stateMatrix[0][0] = State.DEAD;
		stateMatrix[0][1] = State.ALIVE;
		stateMatrix[0][2] = State.DEAD;
		stateMatrix[1][0] = State.ALIVE;
		stateMatrix[1][1] = State.DEAD;
		stateMatrix[1][2] = State.ALIVE;
		stateMatrix[2][0] = State.DEAD;
		stateMatrix[2][1] = State.DEAD;
		stateMatrix[2][2] = State.ALIVE;

		// top left
		boolean cellAlive1 = mainModel.isCellAlive(stateMatrix, 1, 1, -1, -1);
		boolean cellAlive2 = mainModel.isCellAlive(stateMatrix, 1, 1, -1, 0);
		boolean cellAlive3 = mainModel.isCellAlive(stateMatrix, 1, 1, -1, 1);
		boolean cellAlive4 = mainModel.isCellAlive(stateMatrix, 1, 1, 0, -1);
		boolean cellAlive5 = mainModel.isCellAlive(stateMatrix, 1, 1, 0, 0);
		boolean cellAlive6 = mainModel.isCellAlive(stateMatrix, 1, 1, 0, 1);
		boolean cellAlive7 = mainModel.isCellAlive(stateMatrix, 1, 1, 1, -1);
		boolean cellAlive8 = mainModel.isCellAlive(stateMatrix, 1, 1, 1, 0);
		boolean cellAlive9 = mainModel.isCellAlive(stateMatrix, 1, 1, 1, 1);

		assert (cellAlive1 == stateMatrix[0][0].equals(State.ALIVE));
		assert (cellAlive2 == stateMatrix[0][1].equals(State.ALIVE));
		assert (cellAlive3 == stateMatrix[0][2].equals(State.ALIVE));
		assert (cellAlive4 == stateMatrix[1][0].equals(State.ALIVE));
		assert (cellAlive5 == stateMatrix[1][1].equals(State.ALIVE));
		assert (cellAlive6 == stateMatrix[1][2].equals(State.ALIVE));
		assert (cellAlive7 == stateMatrix[2][0].equals(State.ALIVE));
		assert (cellAlive8 == stateMatrix[2][1].equals(State.ALIVE));
		assert (cellAlive9 == stateMatrix[2][2].equals(State.ALIVE));

	}

	@Test
	void updateBlockTest() {
		MainModel mainModel = new MainModel(3, 3, UniverseShape.FLAT);

		State[][] stateMatrix = new State[3][3];

		Random random = new Random();

		stateMatrix[0][0] = State.ALIVE;
		stateMatrix[0][1] = State.ALIVE;
		stateMatrix[0][2] = State.DEAD;

		stateMatrix[1][0] = State.ALIVE;
		stateMatrix[1][1] = State.ALIVE;
		stateMatrix[1][2] = State.DEAD;

		stateMatrix[2][0] = State.DEAD;
		stateMatrix[2][1] = State.DEAD;
		stateMatrix[2][2] = State.DEAD;

		State[][] update = mainModel.update(stateMatrix, 3, 3, UniverseShape.FLAT);

		for (int x = 0; x < update.length; x++) {
			for (int y = 0; y < update[x].length; y++) {
				assert (update[x][y].equals(stateMatrix[x][y]));
			}
		}

	}

	@Test
	void updateBlinkerTest() {
		MainModel mainModel = new MainModel(3, 3, UniverseShape.FLAT);

		State[][] stateMatrix = new State[3][3];

		Random random = new Random();

		stateMatrix[0][0] = State.DEAD;
		stateMatrix[0][1] = State.DEAD;
		stateMatrix[0][2] = State.DEAD;

		stateMatrix[1][0] = State.ALIVE;
		stateMatrix[1][1] = State.ALIVE;
		stateMatrix[1][2] = State.ALIVE;

		stateMatrix[2][0] = State.DEAD;
		stateMatrix[2][1] = State.DEAD;
		stateMatrix[2][2] = State.DEAD;

		for (int iterations = 0; iterations < 10; iterations++) {
			State[][] update = mainModel.update(stateMatrix, 3, 3, UniverseShape.FLAT);

			for (int x = 0; x < update.length; x++) {
				for (int y = 0; y < update[x].length; y++) {
					assert (update[x][y].equals(stateMatrix[y][x]));
				}
			}

			stateMatrix = update;
		}

	}

}