package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.domain.gamer.PlayerPartition;

import java.util.List;

public class Rule {

    public boolean hasAnyBlackjack(Dealer dealer, List<Player> players) {
        if (dealer.isBlackJack()) {
            for (Player player : players) {
                if (player.isBlackJack()) {
                    player.refund();
                    return true;
                } else {
                    dealer.winFrom(player);
                    player.loseFrom(dealer);
                }
            }
            return true;
        }

        for (Player player : players) {
            if (player.isBlackJack()) {
                player.winFrom(dealer);
                dealer.loseFrom(player);
                return true;
            }
        }
        return false;
    }

    public void resolve(Dealer dealer, PlayerPartition partition) {
        for (Player busted : partition.busted()) {
            dealer.winFrom(busted);
            busted.loseFrom(dealer);
        }

        for (Player player : partition.survived()) {
            if (dealer.isBust()) {
                player.winFrom(dealer);
                dealer.loseFrom(player);
            } else {
                int playerScore = player.calculateScore();
                int dealerScore = dealer.calculateScore();

                if (playerScore > dealerScore) {
                    player.winFrom(dealer);
                    dealer.loseFrom(player);
                } else if (playerScore < dealerScore) {
                    dealer.winFrom(player);
                    player.loseFrom(dealer);
                } else {
                    player.refund();
                }
            }
        }
    }
}
