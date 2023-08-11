package br.com.mv.ecommerce;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private String userId, orderId;
    private BigDecimal amount;
    private Date creationDate;


    public Order(String userId, String orderId, BigDecimal amount) {
        this.userId = userId;
        this.orderId = orderId;
        this.amount = amount;
        this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
