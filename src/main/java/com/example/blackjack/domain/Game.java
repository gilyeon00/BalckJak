package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.exception.GamerBustException;
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

        // 카드 분배
        initialCards(players, dealer, deck);

        // 블랙잭 판별
        boolean isAnyBlackJack = checkBlackJack(dealer, players);
        if (isAnyBlackJack) {
            output.printFinalCards(dealer, players);
            output.printProfitSummary(players, dealer);
            return;
        }

        try {
            askPlayersHitCard(players, deck);
            dealer.drawMoreCard(deck);

            // 승패 산정
        } catch (GamerBustException e) {
            // 추가 뽑기 중 21이 초과될 경우 (버스트 상태)
            Player bustedPlayer = e.getPlayer();
            dealer.winFrom(bustedPlayer);

            for (Player player : players) {
                if (!player.equals(bustedPlayer)) {
                    player.refund();
                }
            }
        }

        output.printFinalCards(dealer, players);
        output.printProfitSummary(players, dealer);
    }

    private boolean checkBlackJack(Dealer dealer, List<Player> players) {
        boolean isAnyBlackJack = false;
        // TODO:: 플레이어 2명이상이 블랙잭인 경우

        if (dealer.isBlackJack()) {
            isAnyBlackJack = true;
            for (Player player : players) {
                if (player.isBlackJack()) {
                    player.refund();
                    System.out.println(player.getName() + "와 딜러 모두 블랙잭이므로 무승부로 게임을 종료합니다");
                    return isAnyBlackJack;
                } else {
                    player.loseFrom(dealer);
                    dealer.winFrom(player);
                    System.out.println("딜러가 블랙잭이므로 딜러가 승리했습니다");
                }
            }
        } else {
            for (Player player : players) {
                if (player.isBlackJack()) {
                    isAnyBlackJack = true;
                    player.winFrom(dealer);
                    dealer.loseFrom(player);
                    return isAnyBlackJack;
                } else {
                    return isAnyBlackJack;
                }
            }
        }

        return isAnyBlackJack;
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
                    throw new GamerBustException(player);
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
