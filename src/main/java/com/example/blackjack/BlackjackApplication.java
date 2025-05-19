package com.example.blackjack;

import com.example.blackjack.domain.Game;
import com.example.blackjack.domain.Rule;
import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;

public class BlackjackApplication {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();
        Rule rule = new Rule();

        Game game = new Game(input, output, rule);
        game.run();
    }

}
