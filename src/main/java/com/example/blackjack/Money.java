package com.example.blackjack;

public class Money {
    private final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public Money plus(Money other) {
        return new Money(this.amount + other.amount);
    }

    public Money minus(Money other) {
        Money money = new Money(this.amount - other.amount);
        return money;
    }

    public Money times(double rate) {
        return new Money((int) (this.amount * rate));
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.valueOf(amount);
    }
}
