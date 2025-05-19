package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Money;
import com.example.blackjack.domain.card.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class Gamer {

    protected String name;
    protected List<Card> cards = new ArrayList<>();
    protected Money money = new Money(0);


    public String getName() {
        return name;
    }

    public void receiveCard(Card card) {
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public Money getMoney() {
        return money;
    }

    public void pay(Money amount) {
        this.money = this.money.minus(amount);
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

    public abstract void winFrom(Gamer gamer);

    public boolean isBlackJack() {
        return cards.size() == 2 && calculateScore() == 21;
    }

    public abstract Money getBetAmount();

}