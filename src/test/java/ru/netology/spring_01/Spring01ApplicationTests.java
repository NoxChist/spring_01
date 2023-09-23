package ru.netology.spring_01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Spring01ApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;
    public static final GenericContainer<?> devapp = new GenericContainer<>("devapp:latest").withExposedPorts(8080);
    public static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp:latest").withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {
        int devappPort = devapp.getMappedPort(8080);
        int prodappPort = prodapp.getMappedPort(8081);
        String devResponse;
        String prodResponse;
        String devResponseExpectation = "Current profile is dev";
        String prodResponseExpectation = "Current profile is production";

        devResponse = restTemplate.getForEntity("http://localhost:" + devappPort + "/profile", String.class).toString();
        prodResponse = restTemplate.getForEntity("http://localhost:" + prodappPort + "/profile", String.class).toString();

        Assertions.assertEquals(devResponseExpectation, devResponse);
        Assertions.assertEquals(prodResponseExpectation, prodResponse);
    }

}
