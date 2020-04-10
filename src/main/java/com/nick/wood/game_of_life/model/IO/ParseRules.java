package com.nick.wood.game_of_life.model.IO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseRules {

	private static void ReadRulesFile(String fileName) throws IOException {

		// read states file
		List<String> states = Files.readAllLines(Paths.get(fileName));

		// create all expressions
		List<Expression> stateExpressions = new ArrayList<>();

		for (String state : states) {
			stateExpressions.add(new TerminalExpression(state));
		}

		TerminalExpression bracketOpen = new TerminalExpression("{");
		TerminalExpression bracketClosed = new TerminalExpression("}");

		List<Expression> numberExpressions = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			numberExpressions.add(new NumericalTerminalExpression(i));
		}

		// create interpreter tree
		for (Expression stateExpression : stateExpressions) {
			Expression stateExpressionTree = new AndExpression(stateExpression, bracketOpen);
		}

		String file = Files.readString(Paths.get(fileName));



	}

}
