package com.ptt.service;

import com.ptt.entity.Session;
import com.ptt.entity.User;
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
                .filter(u -> u.password().equals(password) && u.username().equals(username))
                .findFirst()
                .map(u -> {
                    Session st = new Session(UUID.randomUUID(), u);
                    validTokens.add(st);
                    return st;
                }));
    }

    public Uni<Boolean> isLoggedIn(String sessionToken) {
        return Uni.createFrom()
                .item(validTokens.stream().anyMatch(s -> s.token().toString().equals(sessionToken)));
    }
}
