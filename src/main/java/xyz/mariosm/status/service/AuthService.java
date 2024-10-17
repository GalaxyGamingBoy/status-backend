package xyz.mariosm.status.service;

import xyz.mariosm.status.data.User;

import java.util.Map;

public interface AuthService {
    Map<String, Object> register(User user);
    Map<String, Object> login(User user);
}
