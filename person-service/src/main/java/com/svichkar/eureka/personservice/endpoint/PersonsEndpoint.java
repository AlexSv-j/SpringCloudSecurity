package com.svichkar.eureka.personservice.endpoint;


import java.util.concurrent.TimeoutException;

import com.svichkar.eureka.personservice.clients.NotesClient;
import com.svichkar.eureka.personservice.domain.Person;
import com.svichkar.eureka.personservice.repository.PersonRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonsEndpoint {

    private Logger logger = LoggerFactory.getLogger(PersonsEndpoint.class);

    @Value("${eureka.instance.instance-id}")
    private String port;

    @Value("${my.test.property}")
    private String testProperty;

    private final PersonRepository repository;
    private final ApplicationContext applicationContext;
    private final NotesClient notesClient;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id) {
        return repository.findById(id).get();
    }

    @GetMapping("/testEndpoint")
    public String testEndpoint(@RequestHeader("CorrelationId") String correlationId) {
        String applicationName = applicationContext.getId();
        logger.debug("fetching motes from notes service start");
        final var result = notesClient.getNotes(correlationId);
        logger.debug("fetching motes from notes service end");
        return "applicationName = " + applicationName + ", " + "notes result = " + result + ", port = " + port;
    }

    @PostMapping
    public void setPerson(@RequestBody Person person) {
        repository.save(person);
    }

    @GetMapping("/testProperty")
    public String getTestProperty(@RequestHeader("CorrelationId") String correlationId) {
        logger.debug("Test correlation id - {}", correlationId);
        return testProperty;
    }

    @Retry(name = "testRetry", fallbackMethod = "recoveryRetry")
    @GetMapping("/testRetry")
    public String getRetry(@RequestHeader("CorrelationId") String correlationId) throws TimeoutException {
        logger.debug("Retry: Test correlation id - {}", 123);
        throw new TimeoutException("wrong");
//        throw new NullPointerException("wrong");
//        return testProperty;
    }

    public String recoveryRetry(@RequestHeader("CorrelationId") String correlationId, Throwable t) {
        logger.debug("Retry method used");
        return "recovery retry method used";
    }
}
