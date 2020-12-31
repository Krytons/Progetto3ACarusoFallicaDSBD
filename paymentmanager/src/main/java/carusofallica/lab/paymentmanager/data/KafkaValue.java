package carusofallica.lab.paymentmanager.data;

import java.sql.Timestamp;

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
