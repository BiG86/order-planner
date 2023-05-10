package it.snorcini.dev.orderplanner.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {
    INITIAL("initial"), DELIVERY("delivery"), COMPLETED("completed");

    private String status;
    OrderStatus(final String status) {
        this.status = status;
    }
}
