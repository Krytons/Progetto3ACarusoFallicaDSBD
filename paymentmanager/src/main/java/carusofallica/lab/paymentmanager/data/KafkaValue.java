package carusofallica.lab.paymentmanager.data;

public class KafkaValue {

    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public KafkaValue setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
