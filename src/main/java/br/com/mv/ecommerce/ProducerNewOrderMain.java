package br.com.mv.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class ProducerNewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            var key = UUID.randomUUID().toString();
            var value = key + ",123456,454872";
            var email = "Thank you for your order! We are processing your order!";
            var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", key, value);
            var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", key, email);
            producer.send(record, getCallback()).get();
            producer.send(emailRecord, getCallback()).get();
        }
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Tempo de execução: " + executionTime + " milissegundos");
    }
    private static Callback getCallback() {
        return (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                return;
            }
            System.out.println("Success! sending message " + metadata.topic() + ":::partition" + metadata.partition() + "/offset " + metadata.offset() + "/ timestamp" + metadata.timestamp());
        };
    }
    private static Properties properties() {
        var prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return prop;
    }
}
