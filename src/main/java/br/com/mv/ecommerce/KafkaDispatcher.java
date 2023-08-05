package br.com.mv.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

class KafkaDispatcher<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    KafkaDispatcher(){
    this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
        return prop;
    }

     void send(String topic, String key, T value) throws ExecutionException, InterruptedException {
         var record = new ProducerRecord<>(topic, key, value);
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

    @Override
    public void close()  {
        producer.close();
    }
}
