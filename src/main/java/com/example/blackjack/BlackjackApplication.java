package com.example.blackjack;

import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlackjackApplication {

    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        ConsoleOutput output = new ConsoleOutput();

        Game game = new Game(input, output);
        game.run();
    }

}
