package tp.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.util.Pair;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service("kafkaQueueService")
public class KafkaQueueService implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(KafkaQueueService.class);
    private static final String topic = "topic-tp-progcomp";
    private static ArrayList<Pair<String, String>> retrievedDatas = new ArrayList<>();

    @Autowired
    private KafkaTemplate<String, String> template;

    private final CountDownLatch latch = new CountDownLatch(3);

    @Override
    public void run(String... args) throws Exception {
        this.template.send(topic, "foo1");
        this.template.send(topic, "foo2");
        this.template.send(topic, "foo3");
        latch.await(60, TimeUnit.SECONDS);
        logger.info("All received");
    }

    @KafkaListener(topics = topic)
    public void listen(ConsumerRecord<?, ?> cr) throws Exception {
        logger.info(cr.toString());
        retrievedDatas.add(Pair.of(cr.topic(), cr.value().toString()));
        latch.countDown();
    }

    public void addToQueue(String data) {
        this.template.send(topic, data);
    }

    public static ArrayList<Pair<String, String>> getRetrievedDatas() {
        return retrievedDatas;
    }
}
