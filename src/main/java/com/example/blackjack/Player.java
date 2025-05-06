package com.example.blackjack;

import com.example.blackjack.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * 플레이어는 이름과 보유 금액(Money), 카드 목록을 가진다.
 * 게임 시작 시 초기 자산은 0원이며, 베팅은 음수(대출 개념)를 허용한다.
 */
public class Player {

    private String name;
    private Money money;
    private List<Card> cards;

    public Player(String name) {
        this.name = name;
        this.money = new Money(0);
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void betMoney(Money money){
        this.money = money;
    }

    public void receiveCard (Card card){
        cards.add(card);
    }

    public List<Card> getCards() {
        return cards;
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
