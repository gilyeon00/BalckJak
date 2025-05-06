package com.example.blackjack;

import com.example.blackjack.card.Card;
import com.example.blackjack.card.Pattern;

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
                "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "Jack", "Queen", "King"
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
}
