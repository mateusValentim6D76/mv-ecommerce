package br.com.mv.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String, String>(properties());
        var sales = new HashMap<>();
        //sales.put("iPhone11Pro","4000");
        //sales.put("MacBook Pro 256GB","27000");
        var value = "123456, 454872, 78964, 96578";
        var record = new ProducerRecord<String, String>("ECOMMERCE_NEW_ORDER", value, value);
        producer.send(record, getCallback()).get();
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

    private static Properties properties(){
        var prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return prop;
    }
    }
