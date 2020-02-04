package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.model.universe.FlatUniverse;
import com.nick.wood.game_of_life.model.universe.SphericalUniverse;
import com.nick.wood.game_of_life.model.universe.Universe;
import org.junit.jupiter.api.Test;

import java.util.Random;

class GameModelTest {

	@Test
	void isCellAliveTest() {

		Universe universe = new FlatUniverse();

		State[][] stateMatrix = new State[3][3];

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
		boolean cellAlive1 = universe.isCellAlive(stateMatrix, 1, 1, -1, -1);
		boolean cellAlive2 = universe.isCellAlive(stateMatrix, 1, 1, -1, 0);
		boolean cellAlive3 = universe.isCellAlive(stateMatrix, 1, 1, -1, 1);
		boolean cellAlive4 = universe.isCellAlive(stateMatrix, 1, 1, 0, -1);
		boolean cellAlive5 = universe.isCellAlive(stateMatrix, 1, 1, 0, 0);
		boolean cellAlive6 = universe.isCellAlive(stateMatrix, 1, 1, 0, 1);
		boolean cellAlive7 = universe.isCellAlive(stateMatrix, 1, 1, 1, -1);
		boolean cellAlive8 = universe.isCellAlive(stateMatrix, 1, 1, 1, 0);
		boolean cellAlive9 = universe.isCellAlive(stateMatrix, 1, 1, 1, 1);

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

		Universe universe = new FlatUniverse();

		GameModel gameModel = new GameModel(3, 3, universe);

		State[][] stateMatrix = new State[3][3];

		stateMatrix[0][0] = State.ALIVE;
		stateMatrix[0][1] = State.ALIVE;
		stateMatrix[0][2] = State.DEAD;

		stateMatrix[1][0] = State.ALIVE;
		stateMatrix[1][1] = State.ALIVE;
		stateMatrix[1][2] = State.DEAD;

		stateMatrix[2][0] = State.DEAD;
		stateMatrix[2][1] = State.DEAD;
		stateMatrix[2][2] = State.DEAD;

		State[][] update = gameModel.update(stateMatrix, universe);

		for (int x = 0; x < update.length; x++) {
			for (int y = 0; y < update[x].length; y++) {
				assert (update[x][y].equals(stateMatrix[x][y]));
			}
		}

	}

	@Test
	void updateBlinkerTest() {

		Universe universe = new FlatUniverse();

		GameModel gameModel = new GameModel(3, 3, universe);

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
			State[][] update = gameModel.update(stateMatrix, universe);

			for (int x = 0; x < update.length; x++) {
				for (int y = 0; y < update[x].length; y++) {
					assert (update[x][y].equals(stateMatrix[y][x]));
				}
			}

			stateMatrix = update;
		}

	}

	@Test
	void SphereBlinkerTest() {

		Universe universe = new SphericalUniverse();

		GameModel gameModel = new GameModel(5, 5, universe);

		State[][] stateMatrix1 = new State[][] {
				{State.DEAD, State.ALIVE, State.ALIVE, State.ALIVE, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix2 = new State[][] {
				{State.DEAD, State.DEAD, State.ALIVE, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.ALIVE, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.ALIVE, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix = stateMatrix1.clone();

		for (int iterations = 0; iterations < 10; iterations++) {
			State[][] update = gameModel.update(stateMatrix, universe);

			if (iterations%2 == 0) {
				for (int x = 0; x < update.length; x++) {
					for (int y = 0; y < update[x].length; y++) {
						assert (update[x][y].equals(stateMatrix2[x][y]));
					}
				}
			} else {
				for (int x = 0; x < update.length; x++) {
					for (int y = 0; y < update[x].length; y++) {
						assert (update[x][y].equals(stateMatrix1[x][y]));
					}
				}
			}

			stateMatrix = update;
		}

	}

	@Test
	void SphereBlinker2Test() {

		Universe universe = new SphericalUniverse();

		GameModel gameModel = new GameModel(5, 5, universe);

		State[][] stateMatrix1 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix2 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.ALIVE, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix = stateMatrix1.clone();

		for (int iterations = 0; iterations < 10; iterations++) {
			State[][] update = gameModel.update(stateMatrix, universe);

			if (iterations%2 == 0) {
				for (int x = 0; x < update.length; x++) {
					for (int y = 0; y < update[x].length; y++) {
						assert (update[x][y].equals(stateMatrix2[x][y]));
					}
				}
			} else {
				for (int x = 0; x < update.length; x++) {
					for (int y = 0; y < update[x].length; y++) {
						assert (update[x][y].equals(stateMatrix1[x][y]));
					}
				}
			}

			stateMatrix = update;
		}

	}

	@Test
	void SphereGliderTest() {

		Universe universe = new SphericalUniverse();

		GameModel gameModel = new GameModel(5, 5, universe);

		State[][] stateMatrix1 = new State[][] {
			{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
			{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
			{State.DEAD, State.DEAD, State.ALIVE, State.DEAD, State.ALIVE},
			{State.DEAD, State.DEAD, State.DEAD, State.ALIVE, State.ALIVE},
			{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix2 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.ALIVE, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.ALIVE, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix3 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.ALIVE, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.ALIVE, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
		};

		State[][] stateMatrix4 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.ALIVE, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
		};

		State[][] stateMatrix5 = new State[][] {
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.DEAD, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.DEAD, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.ALIVE, State.DEAD},
				{State.ALIVE, State.DEAD, State.DEAD, State.DEAD, State.ALIVE},
		};

		State[][][] states = new State[4][][];
		states[0] = stateMatrix2;
		states[1] = stateMatrix3;
		states[2] = stateMatrix4;
		states[3] = stateMatrix5;

		State[][] stateMatrix = stateMatrix1.clone();

		for (int iterations = 0; iterations < 4; iterations++) {
			State[][] update = gameModel.update(stateMatrix, universe);

			for (int x = 0; x < update.length; x++) {
				for (int y = 0; y < update[x].length; y++) {
					assert (update[x][y].equals(states[iterations][x][y]));
				}
			}

			stateMatrix = update;
		}

	}

}