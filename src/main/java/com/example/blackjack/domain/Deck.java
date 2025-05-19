package com.example.blackjack.domain;

import com.example.blackjack.domain.card.Card;
import com.example.blackjack.domain.card.Pattern;
import com.example.blackjack.domain.gamer.Dealer;
import com.example.blackjack.domain.gamer.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards;


    /*
        - 52장의 무작위의 카드 리스트를 가지고 있음
        - 카드를 1장씩 뽑을 수 있음
     */

    public Deck() {
        this.cards = generateInitialDeck();
    }

    public List<Card> generateInitialDeck () {
        List<String> types = Arrays.asList(
                "A", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "J", "Q", "K"
        );
        List<Card> deck = new ArrayList<>();

        for (Pattern pattern : Pattern.values()) {
            for (String type : types) {
                deck.add(new Card(type, pattern));
            }
        }

        Collections.shuffle(deck);
        return deck;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("덱에 카드가 없습니다.");
        }
        return cards.remove(0);
    }

    public int remainingCards() {
        return cards.size();
    }

    public void initialCards(List<Player> players, Dealer dealer, int INITIAL_CARD) {
        for (int i = 0; i < INITIAL_CARD; i++) {
            for (Player player : players) {
                player.receiveCard(drawCard());
            }
            dealer.receiveCard(drawCard());
        }
    }
}
