package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Money;
import com.example.blackjack.card.Card;

import java.util.ArrayList;

public class Dealer extends Gamer {

    public void receiveCard (Card card){
        cards.add(card);
    }

    public Money getMoney() {
        return money;
    }

    @Override
    public Money getBetAmount() {
        return new Money(0); // 딜러는 베팅하지 않음
    }


    public Card getFirstCard() {
        if (cards.isEmpty()) throw new IllegalStateException("카드가 존재하지 않습니다.");
        return cards.get(0);
    }

    public Dealer() {
        this.money = new Money(0);
        this.cards = new ArrayList<>();
    }
}
