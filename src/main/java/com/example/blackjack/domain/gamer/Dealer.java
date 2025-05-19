package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Deck;
import com.example.blackjack.domain.Money;
import com.example.blackjack.domain.card.Card;
import com.example.blackjack.exception.GamerBustException;

import java.util.ArrayList;

public class Dealer extends Gamer {

    @Override
    public void winFrom(Gamer gamer) {
        gamer.pay(gamer.getBetAmount());
        this.money = this.money.plus(gamer.getBetAmount());
    }

    @Override
    public Money getBetAmount() {
        return new Money(0); // 딜러는 베팅하지 않음
    }


    public Card getFirstCard() {
        if (cards.isEmpty()) throw new IllegalStateException("카드가 존재하지 않습니다.");
        return cards.get(0);
    }

    public void drawMoreCard(Deck deck) {
        while (calculateScore() <= 16) {
            receiveCard(deck.drawCard());
            System.out.println("딜러가 카드 한 장을 뽑았습니다");
            if (isBust()) {
                throw new GamerBustException(this);
            }
        }
    }

    public Dealer() {
        this.money = new Money(0);
        this.cards = new ArrayList<>();
    }
}
