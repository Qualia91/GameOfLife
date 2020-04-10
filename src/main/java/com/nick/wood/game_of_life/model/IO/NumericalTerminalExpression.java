package com.nick.wood.game_of_life.model.IO;

import java.util.StringTokenizer;

public class NumericalTerminalExpression implements Expression {

	private int data;

	public NumericalTerminalExpression(int data) {
		this.data = data;
	}

	@Override
	public boolean interpret(String context) {
		StringTokenizer st = new StringTokenizer(context);

		while (st.hasMoreTokens()) {
			String test = st.nextToken();
			try {
				int i = Integer.parseInt(test);
				if (i == data) {
					return true;
				}
			} catch (Exception ignored) {

			}
		}

		return false;
	}
}
