package com.example.blackjack.view;

import com.example.blackjack.domain.card.Card;
import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleOutput {

    public void printInitialCardStatus(List<Player> players, Dealer dealer) {
        System.out.println("딜러와 " + getPlayerNames(players) + "에게 2장의 카드를 나누었습니다.\n");
        System.out.println("딜러: " + dealer.getFirstCard());

        for (Player player : players) {
            System.out.println(player.getName() + "카드: " + player.getCards());
        }

        System.out.println();
    }

    private String getPlayerNames(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .collect(Collectors.joining(", "));
    }

    public void printFinishGame(Dealer dealer, List<Player> players){
        printFinalCards(dealer, players);
        printProfitSummary(players, dealer);
    }

    public void printFinalCards(Dealer dealer, List<Player> players) {
        System.out.println("\n");

        System.out.println("딜러 카드: " + formatCards(dealer.getCards()) + " - 결과: " + dealer.calculateScore());

        for (Player player : players) {
            System.out.println(player.getName() + "카드: " + formatCards(player.getCards()) + " - 결과: " + player.calculateScore());
        }
    }

    public void printProfitSummary(List<Player> players, Dealer dealer) {
        System.out.println("\n## 최종 수익");
        System.out.println("딜러: " + dealer.getMoney());

        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getMoney());
        }
    }

    private String formatCards(List<Card> cards) {
        return cards.stream()
                .map(Card::toString)
                .collect(Collectors.joining(", "));
    }
}
