package com.nick.wood.game_of_life.model;

import java.util.function.Predicate;

public class Interaction {

	State interactingState;
	int[] affectingAmountCriteria;
	private Predicate<Integer> predicateForAffectingCriteria;

	public Interaction(State interactingState, int[] affectingAmountCriteria) {
		this.interactingState = interactingState;
		this.affectingAmountCriteria = affectingAmountCriteria;
		for (int affectingAmountCriterion : affectingAmountCriteria) {
			if (predicateForAffectingCriteria == null) {
				predicateForAffectingCriteria = x -> x == affectingAmountCriterion;
			}
			predicateForAffectingCriteria = predicateForAffectingCriteria.or(x -> x == affectingAmountCriterion);
		}
	}

	Predicate<Integer> getPredicateForAffectingCriteria() {
		return predicateForAffectingCriteria;
	}
}
