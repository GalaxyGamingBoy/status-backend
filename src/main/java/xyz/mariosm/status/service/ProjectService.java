package xyz.mariosm.status.service;

import org.springframework.data.domain.Page;
import xyz.mariosm.status.data.Project;

import java.util.UUID;

public interface ProjectService {
    Project insert(Project project);
    Project fetch(UUID id);
    Page<Project> all(int page, int size);
    void delete(UUID id);
}
