package xyz.mariosm.status.http;

import lombok.NonNull;

public record UserPayload(@NonNull String username, @NonNull String password) {}
