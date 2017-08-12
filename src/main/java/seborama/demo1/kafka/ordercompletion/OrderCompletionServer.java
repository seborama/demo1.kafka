package seborama.demo1.kafka.ordercompletion;

import seborama.demo1.kafka.ConsoleArguments;
import seborama.demo1.kafka.KafkaOrderConsumer;

import java.io.IOException;

public class OrderCompletionServer {

    public static void main(String[] args) throws IOException {
        ConsoleArguments consoleArguments = new ConsoleArguments(args);

        try (KafkaOrderConsumer consumer = OrderDispatchConsumer.create(1000)) {
            System.out.println("Order completion server running...");
            consumer.consumerLoop();
        }
    }
}
