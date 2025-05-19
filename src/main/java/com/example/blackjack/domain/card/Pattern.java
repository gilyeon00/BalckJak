package com.example.blackjack.domain.card;

public enum Pattern {
    HEART("하트")
    , DIAMOND("다이아몬드")
    , CLUB("클로버")
    , SPADE("스페이드");

    private final String koreanDescription;

    Pattern(String koreanDescription) {
        this.koreanDescription = koreanDescription;
    }

    public String getKoreanDescription() {
        return koreanDescription;
    }
}
