package xyz.mariosm.status.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.status.assemblers.ProjectModelAssembler;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.User;
import xyz.mariosm.status.http.ProjectPayload;
import xyz.mariosm.status.service.ProjectService;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(path = "/project")
@RequiredArgsConstructor
public class ProjectController {
    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final ProjectModelAssembler assembler;

    @Autowired
    private final UserDetailsService userDetailsService;

    @GetMapping(path = "/")
    public Links root() {
        return Links.of(linkTo(methodOn(ProjectController.class).all(0)).withRel("all"),
                        linkTo(methodOn(ProjectController.class).newProject(null)).withRel("new"));
    }

    @PostMapping(path = "/new")
    public EntityModel<Project> newProject(@RequestBody ProjectPayload payload) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userDetailsService.loadUserByUsername(username);

        Project project = new Project(payload, user);
        return assembler.toModel(projectService.insert(project));
    }

    @GetMapping(path = "/all")
    public CollectionModel<EntityModel<Project>> all(@RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Project> projects = projectService.all(page, 6);

        CollectionModel<EntityModel<Project>> model = CollectionModel.of(
            projects.stream().map(assembler::toModel).collect(Collectors.toList()),
            linkTo(methodOn(ProjectController.class).all(page)).withSelfRel(),
            linkTo(methodOn(ProjectController.class).all(0)).withRel("pageFirst"),
            linkTo(methodOn(ProjectController.class).all(projects.getTotalPages() - 1)).withRel("pageLast")
                                 );

        if (projects.hasNext()) {
            model.add(linkTo(methodOn(ProjectController.class).all(page + 1)).withRel("pageNext"));
        }

        if (projects.hasPrevious()) {
            model.add(linkTo(methodOn(ProjectController.class).all(page - 1)).withRel("pagePrev"));
        }

        return model;
    }

    @GetMapping(path = "/{id}")
    public EntityModel<Project> one(@PathVariable UUID id) {
        return assembler.toModel(projectService.fetch(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        projectService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
