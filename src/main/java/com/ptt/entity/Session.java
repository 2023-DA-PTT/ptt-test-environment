package com.ptt.entity;

import java.util.UUID;

public record Session(UUID token, User u) { }
