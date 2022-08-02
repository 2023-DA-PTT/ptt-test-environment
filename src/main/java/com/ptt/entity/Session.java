package com.ptt.entity;

import java.util.UUID;

public class Session {
    private final UUID token;
    private final User user;
    
    public Session(UUID token, User user) {
        this.token = token;
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }
    
    public User getUser() {
        return user;
    }
}
