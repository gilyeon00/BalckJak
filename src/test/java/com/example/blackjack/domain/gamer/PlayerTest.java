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
        player.receiveCard(new Card("K", Pattern.HEART));

        dealer.receiveCard(new Card("10", Pattern.DIAMOND));
        dealer.receiveCard(new Card("9", Pattern.CLUB));

        player.winFrom(dealer);
        dealer.loseFrom(player);

        assertEquals(1500, player.getMoney().getAmount());
        assertEquals(-1500, dealer.getMoney().getAmount());
    }
}