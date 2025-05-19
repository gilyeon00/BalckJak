package com.example.blackjack.exception;

import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Gamer;
import com.example.blackjack.domain.gamer.Player;

public class GamerBustException extends RuntimeException {

    private final Gamer gamer;

    public GamerBustException(Gamer gamer) {
        super(gamer.getName() + "의 카드 합이 21을 초과하여 게임이 종료됩니다.");
        this.gamer = gamer;
    }

    public Player getPlayer() {
        return (Player)gamer;
    }

    public Dealer getDealer() {
        return (Dealer)gamer;
    }

}
