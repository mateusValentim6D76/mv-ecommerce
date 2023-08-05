package br.com.mv.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;

public class Order {

    private UUID userId, orderId;
    private BigDecimal amount;

    public Order(UUID userId, UUID orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
    }
}
