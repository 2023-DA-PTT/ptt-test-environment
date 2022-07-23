package com.ptt.service;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class AuthServiceTest {
    @Inject
    AuthService service;

    private final String USERNAME = "darius";
    private final String PASSWORD = "password";

    @Test
    public void basicTest() {
        service.signUp(USERNAME, PASSWORD).subscribe().with(user -> {
           service.login(USERNAME, PASSWORD).subscribe().with(optSession -> {
             assertTrue(optSession.isPresent());
           });
        });
    }

    @Test
    public void failingTest() {
        service.signUp(USERNAME, PASSWORD).subscribe().with(user -> {
            service.login(USERNAME, "wrongPassword").subscribe().with(optSession -> {
                assertTrue(optSession.isEmpty());
            });
        });
    }
}