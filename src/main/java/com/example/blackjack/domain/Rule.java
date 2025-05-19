package com.example.blackjack.domain;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Gamer;
import com.example.blackjack.domain.gamer.Player;
import com.example.blackjack.dto.PlayerPartition;

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

    public void handleBust(Gamer gamer, List<Player> players) {
        if (gamer instanceof Dealer) {
            System.out.println("딜러의 카드 합이 21을 넘어 모든 플레이어는 패에 상관없이 베팅금액을 돌려받습니다.");
            for (Player player : players) {
                player.refund();
            }
        } else if (gamer instanceof Player player) {
            System.out.println(player.getName() + "의 카드 총합이 21을 초과하여 패배했습니다.");
        } else {
            System.out.println("알 수 없는 게이머 타입입니다. 확인이 필요합니다.");
        }
    }
}
