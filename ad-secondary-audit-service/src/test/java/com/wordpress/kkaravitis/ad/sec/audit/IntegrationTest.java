package com.wordpress.kkaravitis.ad.sec.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.wordpress.kkaravitis.ad.search.util.domain.AdInPageResults;
import com.wordpress.kkaravitis.ad.search.util.kafka.event.AdsInPageResultsEvent;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ActiveProfiles("test")

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EmbeddedKafka(
        topics = {
                "testTopic"
        },
        count = 3,
        ports = {0,0,0}
)

@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "ad.page.kafka.consumer.group.id=secondary_consumer"
})
@Import(KafkaTestConfig.class)
@SpringBootTest()
public class IntegrationTest {
    @MockBean
    AdInPageResultsRepository repository;

    @SpyBean
    AdAuditService adAuditService;

    @MockBean
    MongoClient mongoClient;

    @Autowired
    protected EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    KafkaTemplate<String, AdsInPageResultsEvent> kafkaTemplate;

    @AfterAll
    protected void tearDown() {
        embeddedKafkaBroker.destroy();
    }

    @Test
    public void test() throws InterruptedException, ExecutionException {
        // given
        AdInPageResults results = new AdInPageResults();
        results.setAdId("123");
        results.setPageNumber(0);
        results.setPerPage(1);
        AdsInPageResultsEvent event = new AdsInPageResultsEvent();
        event.setResults(List.of(results));
        event.setKey("test-key");

        // when
        kafkaTemplate.send("testTopic", event).get();
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(1, TimeUnit.SECONDS);

        // then
        then(adAuditService).should(times(1)).saveAdsInPageResultsEvent(any());
    }
}
