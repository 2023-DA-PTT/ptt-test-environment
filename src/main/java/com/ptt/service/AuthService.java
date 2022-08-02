package com.ptt.service;

import com.ptt.entity.Session;
import com.ptt.entity.User;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AuthService {
    private final List<User> users;
    private final List<Session> validTokens;

    public AuthService() {
        this.users = new ArrayList<>();
        this.validTokens = new ArrayList<>();
    }

    public Uni<User> signUp(String email, String password) {
        return Uni.createFrom().item(() -> {
            User u = new User(email, password);
            users.add(u);
            return u;
        });
    }

    public Uni<Optional<Session>> login(String username, String password) {
        return Uni.createFrom().item(users.stream()
                .filter(u -> u.getPassword().equals(password) && u.getUsername().equals(username))
                .findFirst()
                .map(u -> {
                    Session st = new Session(UUID.randomUUID(), u);
                    validTokens.add(st);
                    return st;
                }));
    }

    public Uni<Boolean> isLoggedIn(String sessionToken) {
        return Uni.createFrom()
                .item(validTokens.stream().anyMatch(s -> s.getToken().toString().equals(sessionToken)));
    }

    /**
     * Wipe testing data every 2 hours for minimal resource consumption
     */
    @Scheduled(cron = "* 0 * ? * * *")
    public void wipeTestData() {
        this.users.clear();
        this.validTokens.clear();
    }
}
