package com.example.blackjack.domain.gamer;

import com.example.blackjack.card.Card;
import com.example.blackjack.card.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GamerTest {

    private Gamer gamer;

    @BeforeEach
    void setUp() {
        gamer = new Gamer() {};
    }

    @Test
    @DisplayName("카드를 받으면 카드 목록에 추가된다")
    void receiveCard_addCard() {
        Card card = new Card("10", Pattern.SPADE);
        gamer.receiveCard(card);

        assertEquals(1, gamer.getCards().size());
        assertEquals("10", gamer.getCards().get(0).getType());
    }

    @Test
    @DisplayName("J, Q, K는 10점으로 계산된다")
    void calculateScore_10Card() {
        gamer.receiveCard(new Card("J", Pattern.HEART));
        gamer.receiveCard(new Card("Q", Pattern.CLUB));
        gamer.receiveCard(new Card("K", Pattern.DIAMOND));

        assertEquals(30, gamer.calculateScore());
    }

    @Test
    @DisplayName("점수가 21 초과되면 bust로 판단된다")
    void isBust_whenOver21() {
        gamer.receiveCard(new Card("10", Pattern.HEART));
        gamer.receiveCard(new Card("9", Pattern.SPADE));
        gamer.receiveCard(new Card("5", Pattern.CLUB));

        assertTrue(gamer.isBust());
    }

}