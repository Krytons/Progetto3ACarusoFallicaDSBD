package payment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "A payment must have an user_id value")
    private Integer userId;

    @NotNull(message = "A payment must have an order_id value")
    private String orderId;

    @NotNull(message = "A payment must have an amount_payed value")
    private Double amountPayed;

    @NotNull(message = "A payment must have a creation date")
    private Timestamp createdAt;

    @NotNull(message = "A payment must have a modification date")
    private Timestamp modifiedAt;


    public Integer getId() {
        return id;
    }

    public Payment setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Payment setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public Payment setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Double getAmountPayed() {
        return amountPayed;
    }

    public Payment setAmountPayed(Double amountPayed) {
        this.amountPayed = amountPayed;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Payment setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public Payment setModifiedAt(Timestamp modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }
}
