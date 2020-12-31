package carusofallica.lab.paymentmanager.data;

public class KafkaOrderValue extends KafkaValue{

    private String orderId;
    private Integer userId;
    private Double amountPaid;

    public String getOrderId() {
        return orderId;
    }

    public KafkaOrderValue setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public KafkaOrderValue setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public KafkaOrderValue setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }
}
