package com.example.blackjack;

import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;

import java.util.List;

public class Game {

    private final ConsoleInput input;
    private final ConsoleOutput output;

    public Game(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        List<Player> players = input.readPlayers();
        input.createBetting(players);
        System.out.println(players);
    }
}
