package xyz.mariosm.status.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.User;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends CrudRepository<Project, UUID>, PagingAndSortingRepository<Project, UUID> {
    Optional<Project> findByName(String name);
    Page<Project> findAllByCreator(User creator, Pageable pageable);
}
