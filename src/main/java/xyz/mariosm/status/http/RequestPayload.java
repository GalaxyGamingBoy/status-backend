package xyz.mariosm.status.http;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequestPayload(@NotNull UUID project,
                             @NotNull String name,
                             @NotNull String uri,
                             @NotNull String method,
                             @NotNull Integer code,
                             @NotNull Long interval,
                             String token) {}
