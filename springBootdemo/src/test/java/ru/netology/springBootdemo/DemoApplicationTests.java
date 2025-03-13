package ru.netology.springBootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    private GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);


    @Test
    void contextLoadsDev() {
        Integer devAppPort = devApp.getMappedPort(8080);
        ResponseEntity<String> entityFromDevApp = restTemplate.getForEntity("http://localhost:" + devAppPort + "/profile", String.class);
        assertEquals("Current profile is dev", entityFromDevApp.getBody());
    }

    @Test
    void contextLoadsProd() {
        Integer prodAppPort = prodApp.getMappedPort(8081);
        ResponseEntity<String> entityFromProdApp = restTemplate.getForEntity("http://localhost:" + prodAppPort  + "/profile", String.class);
        assertEquals("Current profile is prod", entityFromProdApp.getBody());
    }

}