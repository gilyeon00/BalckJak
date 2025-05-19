package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;

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
}
