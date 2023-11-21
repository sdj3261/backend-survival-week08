package kr.megaptera.assignment.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Money {
    @Column(name = "money")
    private Long value;

    public Money() {

    }

    public Long getValue() {
        return value;
    }

    public Money(Long value) {
        this.value = value;
    }

    public Money times(int multiplier) {
        return new Money(value * multiplier);
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(value, money.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}