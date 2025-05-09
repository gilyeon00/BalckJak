package com.example.blackjack.domain;

import com.example.blackjack.card.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    @DisplayName("게임 초기화 시 덱은 52개의 유니크한 카드로 구성된다")
    void deckHasUniqueCards() {
        Deck deck = new Deck();
        Set<String> uniqueCardSet = new HashSet<>();

        for (Card card : deck.getCards()) {
            String uniqueKey = card.getType() + "-" + card.getPattern();
            uniqueCardSet.add(uniqueKey);
        }

        assertEquals(52, uniqueCardSet.size(), "중복되지 않는 카드가 52장이어야 합니다");
    }

    @Test
    @DisplayName("덱에서 카드를 1장 뽑으면 덱 크기는 1 줄어든다")
    public void drawCard(){
        Deck deck = new Deck();
        deck.drawCard();
        assertEquals(51, deck.remainingCards());
    }

    @Test
    @DisplayName("덱에서 52장 카드를 모두 뽑으면 예외가 발생한다.")
    public void drawCard_fromEmptyDeck(){
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            deck.drawCard();
        }
        assertThrows(IllegalStateException.class, deck::drawCard);
    }
}