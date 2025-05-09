package com.example.blackjack.domain.gamer;

import com.example.blackjack.domain.Money;

import java.util.ArrayList;

/**
 * 플레이어는 이름과 보유 금액(Money), 배팅 금액, 카드 목록을 가진다.
 * 게임 시작 시 초기 자산은 0원이며, 베팅은 음수(대출 개념)를 허용한다.
 */
public class Player extends Gamer {

    private final String name;
    private Money betAmount;

    public Player(String name) {
        this.name = name;
        this.money = new Money(0);
        this.betAmount = new Money(0);
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void betMoney(Money betAmount) {
        this.betAmount = betAmount;
    }

    @Override
    public Money getBetAmount() {
        return betAmount;
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
