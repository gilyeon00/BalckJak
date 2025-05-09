package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Money;
import com.example.blackjack.card.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Gamer {

    protected List<Card> cards = new ArrayList<>();
    protected Money money = new Money(0);


    public void receiveCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Money getMoney() {
        return money;
    }

    public int calculateScore() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : cards) {
            switch (card.getType()) {
                case "K":
                case "Q":
                case "J":
                    sum += 10;
                    break;
                case "A":
                    aceCount++;
                    sum += 1;
                    break;
                default:
                    sum += Integer.parseInt(card.getType());
            }
        }

        while (aceCount > 0 && sum + 10 <= 21) {
            sum += 10;
            aceCount--;
        }

        return sum;
    }

    public boolean isBust() {
        return calculateScore() > 21;
    }

    public void winFrom(Gamer gamer) {
        this.money = this.money.plus(gamer.getBetAmount());
    }

    public abstract Money getBetAmount();
}