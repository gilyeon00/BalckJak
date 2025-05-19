package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.dto.PlayerPartition;
import com.example.blackjack.exception.GamerBustException;
import com.example.blackjack.view.ConsoleInput;
import com.example.blackjack.view.ConsoleOutput;

import java.util.ArrayList;
import java.util.List;

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
        List<Player> players = init();

        Dealer dealer = new Dealer();
        Deck deck = new Deck();

        // 카드 분배
        initialCards(players, dealer, deck);

        // 블랙잭 판별
        if (rule.hasAnyBlackjack(dealer, players)) {
            output.printFinishGame(dealer, players);
            return;
        }

        // Player 카드 Hit 여부 판단
        PlayerPartition result = partitionPlayersByBust(players, deck);

        try {
            dealer.drawMoreCard(deck);
        } catch (GamerBustException e) {
            rule.handleBust(e.getGamer(), players);
            output.printFinishGame(dealer, players);
            return;
        }

        // 승패 산정
        rule.resolve(dealer, result);

        output.printFinishGame(dealer, players);
    }

    private List<Player> init(){
        List<Player> players = input.readPlayers();
        input.createBetting(players);
        return players;
    }


    private void initialCards(List<Player> players, Dealer dealer, Deck deck) {
        deck.initialCards(players, dealer, INITIAL_CARD);
        output.printInitialCardStatus(players, dealer);
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

}
