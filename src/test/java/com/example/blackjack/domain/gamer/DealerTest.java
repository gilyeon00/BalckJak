package com.example.blackjack.domain.gamer;

import com.example.blackjack.card.Card;
import com.example.blackjack.card.Pattern;
import com.example.blackjack.domain.Deck;
import com.example.blackjack.exception.GamerBustException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealerTest {

    @Test
    @DisplayName("딜러 점수 21 초과 시 GamerBustException 발생")
    void test_dealerBust_throwsException() {
        Dealer dealer = new Dealer();
        Deck deck = mock(Deck.class);

        // 딜러의 초기 카드: 10, 6 → 총 16점
        dealer.receiveCard(new Card("10", Pattern.HEART));
        dealer.receiveCard(new Card("6", Pattern.CLUB));

        // 딜러가 추가로 받은 카드: 7 → 총 23점 (Bust)
        when(deck.drawCard()).thenReturn(new Card("7", Pattern.SPADE));

        assertThrows(GamerBustException.class, () -> {
                dealer.receiveCard(deck.drawCard());
                if (dealer.isBust()) {
                    throw new GamerBustException(dealer);
                }
        });
    }
}