package app.kafka;

/**
 * @author saikat
 * Created on 30/3/2019
 */
public interface KafkaConstants {
    public static String KAFKA_BROKERS = "localhost:9092";
    public static String CLIENT_ID="jenkins-matrix-exporter";
    public static String XML_TOPIC_NAME="producerBus";
    public static String LOG_TOPIC_NAME="logsBus";
}
