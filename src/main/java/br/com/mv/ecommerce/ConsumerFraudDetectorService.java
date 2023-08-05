package br.com.mv.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class ConsumerFraudDetectorService {

    public static void main(String[] args) throws InterruptedException {
        var fraudDetectorService = new ConsumerFraudDetectorService();
       try(var service = new KafkaService(ConsumerFraudDetectorService.class.getSimpleName(),"ECOMMERCE_NEW_ORDER", fraudDetectorService::parse)) {
           service.run();
       }
    }

        private void parse (ConsumerRecord < String, String > record){
            System.out.println("----------------------------");
            System.out.println("Processing new order, checking fraud");
            System.out.println(record.key());
            System.out.println(record.value());
            System.out.println("partition::: " + record.partition());
            System.out.println("offset::: " + record.offset());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                //ignore
                e.printStackTrace();
            }
            System.out.println("Order processed");
        }
}
