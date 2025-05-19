package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Gamer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.domain.gamer.PlayerPartition;
import com.example.blackjack.exception.GamerBustException;
import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private final ConsoleInput input;
    private final ConsoleOutput output;
    private final Rule rule;
    private static final Integer INITIAL_CARD = 2;

    public Game(ConsoleInput input, ConsoleOutput output, Rule rule) {
        this.input = input;
        this.output = output;
        this.rule = rule;
    }

    public void run() {
        List<Player> players = input.readPlayers();
        input.createBetting(players);

        Dealer dealer = new Dealer();
        Deck deck = new Deck();

        // 카드 분배
        initialCards(players, dealer, deck);

        // 블랙잭 판별
        if (rule.hasAnyBlackjack(dealer, players)) {
            output.printFinalCards(dealer, players);
            output.printProfitSummary(players, dealer);
            return;
        }

        try {
            // Player 카드 Hit 여부 판단
            PlayerPartition result = partitionPlayersByBust(players, deck);
            List<Player> survivedPlayers = result.survived();
            List<Player> bustPlayers = result.busted();

            // Dealer 추가 카드
            dealer.drawMoreCard(deck);

            // 승패 산정
            resolveResult(dealer, survivedPlayers, bustPlayers);
        } catch (GamerBustException e) {
            // 추가 뽑기 중 21이 초과될 경우 (버스트 상태)
            Gamer bustedGamer = e.getGamer();
            if (bustedGamer instanceof Dealer) {
                System.out.println("딜러의 카드 합이 21을 넘어 모든 플레이어는 패에 상관없이 베팅금액을 돌려받습니다.");
                for (Player player : players) {
                    player.refund();
                }
            } else {
                System.out.println("게임이 비정상으로 종료되었습니다. 확인이 필요합니다");
            }
        }

        output.printFinalCards(dealer, players);
        output.printProfitSummary(players, dealer);
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

    private PlayerPartition partitionPlayersByBust(List<Player> players, Deck deck) {
        List<Player> survived = new ArrayList<>();
        List<Player> busted = new ArrayList<>();

        for (Player player : players) {
            while (true) {
                if (player.isBust()) {
                    System.out.println(player.getName() + "의 카드 총합이 21을 초과하여 패배했습니다.");
                    busted.add(player);
                    break;
                }

                if (!input.askDrawCard(player)) {
                    break;
                }

                player.receiveCard(deck.drawCard());
                System.out.println(player.getName() + "카드: " + player.getCards());
            }

            if (!player.isBust()) {
                survived.add(player);
            }
        }

        return new PlayerPartition(survived, busted);
    }


    private void resolveResult(Dealer dealer, List<Player> survivedPlayers, List<Player> bustPlayers) {
        for (Player bustPlayer : bustPlayers) {
            dealer.winFrom(bustPlayer);
            bustPlayer.loseFrom(dealer);
        }

        for (Player player : survivedPlayers) {
            if (dealer.isBust()) {
                player.winFrom(dealer);
                dealer.loseFrom(player);
                continue;
            }

            int playerScore = player.calculateScore();
            int dealerScore = dealer.calculateScore();

            if (playerScore > dealerScore) {
                player.winFrom(dealer);
                dealer.loseFrom(player);
            } else if (playerScore < dealerScore) {
                dealer.winFrom(player);
                player.loseFrom(dealer);
            } else {
                player.refund(); // 무승부
            }
        }
    }

}
