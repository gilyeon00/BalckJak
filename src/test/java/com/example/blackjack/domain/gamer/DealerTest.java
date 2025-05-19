package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.card.Card;
import com.example.blackjack.domain.card.Pattern;
import com.example.blackjack.domain.Deck;
import com.example.blackjack.exception.GamerBustException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DealerTest {

    @Test
    @DisplayName("딜러가 점수 16 이하일 경우 카드 뽑고 버스트 시 예외 발생")
    void drawMoreCard_shouldThrowGamerBustException_whenScoreExceeds21() {
        // given
        Dealer dealer = new Dealer();
        Deck mockDeck = mock(Deck.class);

        // 초기 점수: 10 + 6 = 16
        dealer.receiveCard(new Card("10", Pattern.SPADE));
        dealer.receiveCard(new Card("6", Pattern.HEART));

        // 다음 카드: 8 → 총합 24 → Bust
        when(mockDeck.drawCard()).thenReturn(new Card("8", Pattern.CLUB));

        // expect
        assertThrows(GamerBustException.class, () -> dealer.drawMoreCard(mockDeck));
    }

    @Test
    @DisplayName("딜러가 17점 이상이면 카드 추가 없이 그대로 유지")
    void drawMoreCard_shouldNotDraw_whenScoreIs17OrMore() {
        // given
        Dealer dealer = new Dealer();
        Deck mockDeck = mock(Deck.class);

        // 점수: 10 + 7 = 17
        dealer.receiveCard(new Card("10", Pattern.SPADE));
        dealer.receiveCard(new Card("7", Pattern.HEART));

        // when
        dealer.drawMoreCard(mockDeck);

        // then
        verify(mockDeck, never()).drawCard(); // drawCard() 호출 안 됨
    }
}
