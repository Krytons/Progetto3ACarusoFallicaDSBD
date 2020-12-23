package carusofallica.lab.paymentmanager.data;

public class KafkaMessage {

    private String key;
    private KafkaValue value;

    public String getOrder_paid() {
        return key;
    }

    public KafkaMessage setOrder_paid(String order_paid) {
        this.key = order_paid;
        return this;
    }

    public KafkaValue getValue() {
        return value;
    }

    public KafkaMessage setValue(KafkaValue value) {
        this.value = value;
        return this;
    }
}
