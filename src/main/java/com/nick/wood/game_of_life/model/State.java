package com.nick.wood.game_of_life.model;

import java.awt.*;

public enum State {
	DEAD(Color.WHITE),
	ALIVE(Color.BLACK);


	private final Color color;

	State(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
