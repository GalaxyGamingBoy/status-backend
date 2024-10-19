package xyz.mariosm.status.assemblers;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import xyz.mariosm.status.controller.ProjectController;
import xyz.mariosm.status.data.Project;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProjectModelAssembler implements RepresentationModelAssembler<Project, EntityModel<Project>> {
    @NotNull
    @Override
    public EntityModel<Project> toModel(@NotNull Project entity) {
        EntityModel<Project> model =
            EntityModel.of(entity,
                           linkTo(methodOn(ProjectController.class).one(entity.getId())).withSelfRel()
                               .andAffordance(afford(methodOn(ProjectController.class).delete(entity.getId()))),
                           linkTo(methodOn(ProjectController.class).all(0)).withRel("projects"));
        return model;
    }

    public CollectionModel<EntityModel<Project>> toCollectionModel(Page<Project> entities, int page) {
        CollectionModel<EntityModel<Project>> model = RepresentationModelAssembler.super.toCollectionModel(entities);

        model.add(linkTo(methodOn(ProjectController.class).all(page)).withSelfRel()
                      .andAffordance(afford(methodOn(ProjectController.class).newProject(null))),
                  linkTo(methodOn(ProjectController.class).all(0)).withRel("pageFirst"),
                  linkTo(methodOn(ProjectController.class).all(entities.getTotalPages() - 1)).withRel("pageLast"));

        if (entities.hasNext()) {
            model.add(linkTo(methodOn(ProjectController.class).all(page + 1)).withRel("pageNext"));
        }

        if (entities.hasPrevious()) {
            model.add(linkTo(methodOn(ProjectController.class).all(page - 1)).withRel("pagePrev"));
        }

        return model;
    }
}
