package com.example.blackjack;

import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;

import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private static final Integer INITIAL_CARD = 2;

    public Game(ConsoleInput input, ConsoleOutput output) {
        this.input = input;
        this.output = output;
    }

    public void run() {
        List<Player> players = input.readPlayers();
        input.createBetting(players);

        Dealer dealer = new Dealer();
        Deck deck = new Deck();

        initialCards(players, dealer, deck);
        System.out.println(players);
    }

    private void initialCards(List<Player> players, Dealer dealer, Deck deck) {
        for (int i = 0; i < INITIAL_CARD; i++) {
            for (Player player : players) {
                player.receiveCard(deck.drawCard());
            }
            dealer.receiveCard(deck.drawCard());
        }

        System.out.println("딜러와 " + getPlayerNames(players) + "에게 2장의 카드를 나누었습니다.");
        System.out.println("딜러: " + dealer.getFirstCardDescription());  // 첫번째 카드만 공개
        for (Player player : players) {
            System.out.println(player.getName() + "카드: " + player.getCards());
        }
    }

    private String getPlayerNames(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }
}
