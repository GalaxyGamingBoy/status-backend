package xyz.mariosm.status.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.mariosm.status.data.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByUsername(String username);
}
