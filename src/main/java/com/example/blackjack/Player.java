package com.example.blackjack;

import com.example.blackjack.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 플레이어는 이름과 보유 금액(Money), 배팅 금액, 카드 목록을 가진다.
 * 게임 시작 시 초기 자산은 0원이며, 베팅은 음수(대출 개념)를 허용한다.
 */
public class Player {

    private String name;
    private Money money;
    private Money betAmount;
    private List<Card> cards;

    public Player(String name) {
        this.name = name;
        this.money = new Money(0);
        this.betAmount = new Money(0);
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Money getBetAmount() {
        return betAmount;
    }

    public Money getMoney() {
        return money;
    }

    public void betMoney(Money betAmount){
        this.betAmount = betAmount;
    }

    public void win() {
        this.money = this.money.plus(betAmount);
    }

    public void lose() {
        this.money = this.money.minus(betAmount);
    }

    public void receiveCard (Card card){
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
    }

    public int calculateScore() {
        int sum = 0;
        int aceCount = 0;

        for (Card card : cards) {
            switch (card.getType()){
                case "J":
                case "K":
                case "Q":
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

    public void refund() {
        this.money.plus(betAmount);
        this.betAmount.minus(betAmount);
        System.out.println(name + "은 베팅 금액을 돌려받습니다.");
    }
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", money=" + money +
                ", cards=" + cards +
                '}';
    }
}
