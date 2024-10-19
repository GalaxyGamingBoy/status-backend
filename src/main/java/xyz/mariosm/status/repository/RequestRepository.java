package xyz.mariosm.status.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.Request;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, UUID>, PagingAndSortingRepository<Request, UUID> {
    Page<Request> findAllByProject(Project project, Pageable pageable);
    List<Request> findAllByProject(Project project);
    List<Request> findAll();
}
