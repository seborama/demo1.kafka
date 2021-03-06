package seborama.demo1.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;

import static org.apache.kafka.common.utils.Utils.sleep;

public class KafkaOrderProducer implements Closeable {

    private final String topicName;
    private final Producer<String, String> producer;
    private final int sleepDuration;

    public static KafkaOrderProducer create(final String topicName, int sleepDuration) {
        Properties props = configure();
        Producer<String, String> producer = new KafkaProducer<>(props);
        return new KafkaOrderProducer(topicName, sleepDuration, producer);
    }

    KafkaOrderProducer(final String topicName,
                       final int sleepDuration, final Producer<String, String> producer) {
        this.topicName = topicName;
        this.producer = producer;
        this.sleepDuration = sleepDuration;
    }

    public void sendMessage(final String key, final String msg) {
        producer.send(new ProducerRecord<>(topicName, key, msg));
        System.out.println("Sent:" + msg);
        sleep(sleepDuration);
    }

    private static Properties configure() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
//        props.put("advertised.host.name", "127.0.0.1");
//        props.put("advertised.port", "9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }

    @Override
    public void close() throws IOException {
        System.out.printf("Closing producer - TopicAdmin name: %s\n", topicName);
        producer.close();
        System.out.printf("Closed producer - TopicAdmin name: %s\n", topicName);
    }
}
