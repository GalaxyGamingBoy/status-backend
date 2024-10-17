package xyz.mariosm.status.assemblers;

import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import xyz.mariosm.status.controller.ProjectController;
import xyz.mariosm.status.data.Project;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {
    @NotNull
    @Override
    public EntityModel<Project> toModel(@NotNull Project entity) {
        EntityModel<Project> model =
            EntityModel.of(entity,
                           linkTo(methodOn(ProjectController.class).one(entity.getId())).withSelfRel(),
                           linkTo(methodOn(ProjectController.class).all(0)).withRel("projects"));
        return model;
    }
}
