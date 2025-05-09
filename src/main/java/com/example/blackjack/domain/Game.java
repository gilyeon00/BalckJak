package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.exception.PlayerBustException;
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
        // 블랙잭 여부 판단

        try {
            askPlayersHitCard(players, deck);
        } catch (PlayerBustException e) {
            // 추가 뽑기 중 21이 초과될 경우
            Player bustedPlayer = e.getPlayer();
            dealer.winFrom(bustedPlayer);

            for (Player player : players) {
                if (!player.equals(bustedPlayer)) {
                    player.refund();
                }
            }
        }
        printProfitSummary(players, dealer);
    }

    private void printProfitSummary(List<Player> players, Dealer dealer) {
        System.out.println("\n## 최종 수익");
        System.out.println("딜러: " + dealer.getMoney());

        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getMoney());
        }
    }

    private void initialCards(List<Player> players, Dealer dealer, Deck deck) {
        for (int i = 0; i < INITIAL_CARD; i++) {
            for (Player player : players) {
                player.receiveCard(deck.drawCard());
            }
            dealer.receiveCard(deck.drawCard());
        }

        System.out.println("딜러와 " + getPlayerNames(players) + "에게 2장의 카드를 나누었습니다.\n");
        System.out.println("딜러: " + dealer.getFirstCard());  // 첫번째 카드만 공개
        for (Player player : players) {
            System.out.println(player.getName() + "카드: " + player.getCards());
        }
        System.out.println("\n");
    }

    private String getPlayerNames(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    private void askPlayersHitCard(List<Player> players, Deck deck) {
        for (Player player : players) {
            while (true) {
                if (player.isBust()) {
                    System.out.println(player.getName() + "의 카드 총합이 21이 넘어 게임이 종료되었습니다.");
                    throw new PlayerBustException(player);
                }

                if (!input.askDrawCard(player)) {
                    break;
                }

                player.receiveCard(deck.drawCard());
                System.out.println(player.getName() + "카드: " + player.getCards());
            }
        }
    }



}
