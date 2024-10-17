package xyz.mariosm.status.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.data.User;
import xyz.mariosm.status.exception.DuplicateRecordException;
import xyz.mariosm.status.repository.UserRepository;
import xyz.mariosm.status.service.AuthService;
import xyz.mariosm.status.service.JwtService;

import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final AuthenticationManager manager;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;

    @Override
    public Map<String, Object> register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateRecordException("username", user.getUsername());
        }

        return Map.of("ok", true, "jwt", jwtService.generateToken(user));
    }

    @Override
    public Map<String, Object> login(User user) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        final String jwt = jwtService.generateToken(userDetailsService.loadUserByUsername(user.getUsername()));

        return Map.of("ok", true, "jwt", jwt);
    }
}
