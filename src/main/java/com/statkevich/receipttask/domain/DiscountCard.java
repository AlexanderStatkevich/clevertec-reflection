package com.statkevich.receipttask.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class DiscountCard {
    private final String cardNumber;
    private final BigDecimal discount;

    public DiscountCard(String cardNumber, BigDecimal discount) {
        this.cardNumber = cardNumber;
        this.discount = discount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return Objects.equals(cardNumber, that.cardNumber)
                && (discount.compareTo(that.discount) == 0);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, discount);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", discount=" + discount +
                '}';
    }
}
