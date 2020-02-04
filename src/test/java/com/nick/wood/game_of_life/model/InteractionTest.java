package com.nick.wood.game_of_life.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InteractionTest {

	@Test
	void interactionCreationTest() {

		Interaction[] aliveInteractions = new Interaction[] {
				new Interaction(State.ALIVE, new int[] {2, 3})
		};

		Interaction[] deadInteractions = new Interaction[] {
				new Interaction(State.ALIVE, new int[] {3})
		};

		assert (!aliveInteractions[0].getPredicateForAffectingCriteria().test(1));
		assert aliveInteractions[0].getPredicateForAffectingCriteria().test(2);
		assert aliveInteractions[0].getPredicateForAffectingCriteria().test(3);
		assert (!aliveInteractions[0].getPredicateForAffectingCriteria().test(4));

		assert (!deadInteractions[0].getPredicateForAffectingCriteria().test(1));
		assert (!deadInteractions[0].getPredicateForAffectingCriteria().test(2));
		assert deadInteractions[0].getPredicateForAffectingCriteria().test(3);
		assert (!deadInteractions[0].getPredicateForAffectingCriteria().test(4));
	}

}