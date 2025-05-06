package com.example.blackjack.card;

public class Card {
    /*
        < type >
        2~10 숫자 카드
        Ace (1 or 11)
        Jack, Queen, King (10)

        < pattern >
        하트, 클로버, 스페이드, 다이아몬드 타입 존재
     */
    private String type;
    private Pattern pattern;

    public String getType() {
        return type;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Card(String type, Pattern pattern) {
        this.type = type;
        this.pattern = pattern;
    }
}
