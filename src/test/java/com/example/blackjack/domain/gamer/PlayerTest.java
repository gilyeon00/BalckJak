package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Money;
import com.example.blackjack.domain.card.Card;
import com.example.blackjack.domain.card.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    @DisplayName("블랙잭 승리 시 베팅 금액의 1.5배 수익이 증가한다")
    void testWinFrom_blackjackBonus() {
        Player player = new Player("aa");
        player.betMoney(new Money(1000));

        Gamer dealer = new Dealer();
        player.receiveCard(new Card("A", Pattern.SPADE));
        player.receiveCard(new Card("K", Pattern.HEART));   // 21

        dealer.receiveCard(new Card("10", Pattern.DIAMOND));
        dealer.receiveCard(new Card("9", Pattern.CLUB));    // 19

        player.winFrom(dealer);
        dealer.loseFrom(player);

        assertEquals(1500, player.getMoney().getAmount());
        assertEquals(-1500, dealer.getMoney().getAmount());
    }

    @Test
    @DisplayName("일반 승리 시 베팅 금액만큼 수익이 증가한다")
    void testWinFrom_normalWin() {
        Player player = new Player("aa");
        player.betMoney(new Money(1000));

        Gamer dealer = new Dealer();
        player.receiveCard(new Card("10", Pattern.HEART));
        player.receiveCard(new Card("9", Pattern.SPADE));   // 19

        dealer.receiveCard(new Card("10", Pattern.CLUB));
        dealer.receiveCard(new Card("8", Pattern.DIAMOND));    // 18

        player.winFrom(dealer);
        dealer.loseFrom(player);

        assertEquals(1000, player.getMoney().getAmount());
        assertEquals(-1000, dealer.getMoney().getAmount());
    }
}