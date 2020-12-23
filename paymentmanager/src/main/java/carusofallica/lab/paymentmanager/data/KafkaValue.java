package carusofallica.lab.paymentmanager.data;

import java.sql.Timestamp;

public class KafkaValue {

    private String orderId;
    private Integer userId;
    private Double amountPaid;
    private long timestamp;
    private String parameters;
    private String body;

    public String getOrderId() {
        return orderId;
    }

    public KafkaValue setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public KafkaValue setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public KafkaValue setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public KafkaValue setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getParameters() {
        return parameters;
    }

    public KafkaValue setParameters(String parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getBody() {
        return body;
    }

    public KafkaValue setBody(String body) {
        this.body = body;
        return this;
    }

}
