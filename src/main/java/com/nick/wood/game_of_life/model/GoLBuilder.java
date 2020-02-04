package com.nick.wood.game_of_life.model;

import com.nick.wood.game_of_life.model.universe.Universe;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GoLBuilder {

	protected Optional<Integer> width = Optional.empty();
	protected Optional<Integer> height = Optional.empty();
	protected Optional<Universe> universe = Optional.empty();
	protected Optional<State[][]> states = Optional.empty();
	protected Optional<Map<State, Interaction[]>> rulesMap = Optional.empty();
	protected Optional<Integer> cellSize = Optional.empty();
	protected Optional<Init> init = Optional.empty();
	protected Optional<Integer> topLeftX = Optional.empty();
	protected Optional<Integer> topLeftY = Optional.empty();

	public GoLBuilder() {
	}

	public GameOfLife build() {
		return new GameOfLife(this);
	}

	public GoLBuilder setWidth(int width) {
		this.width = Optional.of(width);
		return this;
	}

	public GoLBuilder setHeight(int height) {
		this.height = Optional.of(height);
		return this;
	}

	public GoLBuilder setUniverse(Universe universe) {
		this.universe = Optional.of(universe);
		return this;
	}

	public GoLBuilder setStates(State[][] states) {
		this.states = Optional.of(states);
		return this;
	}

	public GoLBuilder setRulesMap(Map<State, Interaction[]> rulesMap) {
		this.rulesMap = Optional.of(rulesMap);
		return this;
	}

	public GoLBuilder addRule(State state, Interaction[] interaction) {
		if (rulesMap.isEmpty()) {
			HashMap<State, Interaction[]> map = new HashMap<>();
			this.rulesMap = Optional.of(map);
		}
		this.rulesMap.get().putIfAbsent(state, interaction);
		return this;
	}

	public GoLBuilder setCellSize(int cellSize) {
		this.cellSize = Optional.of(cellSize);
		return this;
	}

	public GoLBuilder setInitialisation(Init init) {
		this.init = Optional.of(init);
		return this;
	}

	public GoLBuilder setTopLeftX(int topLeftX) {
		this.topLeftX = Optional.of(topLeftX);
		return this;
	}

	public GoLBuilder setTopLeftY(int topLeftY) {
		this.topLeftY = Optional.of(topLeftY);
		return this;
	}
}
