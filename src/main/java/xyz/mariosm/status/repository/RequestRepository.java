package xyz.mariosm.status.repository;

import org.springframework.data.repository.CrudRepository;
import xyz.mariosm.status.data.Request;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID> {}
