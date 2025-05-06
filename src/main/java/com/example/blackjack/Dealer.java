package com.example.blackjack;

import com.example.blackjack.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Dealer {
    private Money money;
    private List<Card> cards;

    public void receiveCard (Card card){
        cards.add(card);
    }

    public void betMoney(Money money){
        this.money = money;
    }

    public Card getFirstCardDescription(){
        if (cards.isEmpty()) throw new IllegalStateException("카드가 존재하지 않습니다.");
        return cards.get(0);
    }

    public Dealer() {
        this.money = new Money(0);
        this.cards = new ArrayList<>();
    }
}
