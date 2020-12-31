package carusofallica.lab.paymentmanager.data;

public class KafkaMessage {

    private String key;
    private KafkaValue value;

    public String getKey() {
        return key;
    }

    public KafkaMessage setKey(String key) {
        this.key = key;
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
