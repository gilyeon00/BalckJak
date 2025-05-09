package com.example.blackjack.exception;

import com.example.blackjack.domain.gamer.Player;

public class PlayerBustException extends RuntimeException {

    private final Player player;

    public PlayerBustException(Player player) {
        super(player.getName() + "의 카드 합이 21을 초과하여 게임이 종료됩니다.");
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
