package xyz.mariosm.status.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.exception.DuplicateRecordException;
import xyz.mariosm.status.exception.NotFoundException;
import xyz.mariosm.status.repository.ProjectRepository;
import xyz.mariosm.status.service.ProjectService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private final ProjectRepository projectRepository;

    @Override
    public Project insert(Project project) {
        project.updateModified();

        try {
            return projectRepository.save(project);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateRecordException("project", project.getName());
        }
    }

    @Override
    public Project fetch(UUID id) {
        return projectRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException("project", id.toString()));
    }

    @Override
    public Page<Project> all(int page, int size) {
        return projectRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void delete(UUID id) {
        projectRepository.delete(this.fetch(id));
    }
}
