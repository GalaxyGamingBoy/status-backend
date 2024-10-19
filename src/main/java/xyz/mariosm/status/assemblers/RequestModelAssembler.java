package xyz.mariosm.status.assemblers;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import xyz.mariosm.status.controller.ProjectController;
import xyz.mariosm.status.controller.RequestController;
import xyz.mariosm.status.data.Request;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class RequestModelAssembler implements
                                   RepresentationModelAssembler<Request, EntityModel<Request>> {
    private static final Logger log = LoggerFactory.getLogger(RequestModelAssembler.class);

    @NotNull
    @Override
    public EntityModel<Request> toModel(@NotNull Request entity) {
        EntityModel<Request> model = EntityModel.of(entity);

        model.add(linkTo(methodOn(RequestController.class).all(0)).withRel("requests"),
                  linkTo(methodOn(ProjectController.class).one(entity.getProject().getId())).withRel("project"),
                  linkTo(methodOn(RequestController.class).one(entity.getId())).withSelfRel()
                                                                               .andAffordance(afford(methodOn(
                                                                                   RequestController.class).update(
                                                                                   entity.getId(), null)))
                                                                               .andAffordance(afford(methodOn(
                                                                                   RequestController.class).delete(
                                                                                   entity.getId()))));

        return model;
    }

    @NotNull
    public CollectionModel<EntityModel<Request>> toCollectionModel(@NotNull Page<Request> entities, int page) {
        CollectionModel<EntityModel<Request>> model = RepresentationModelAssembler.super.toCollectionModel(entities);

        model.add(
            linkTo(methodOn(RequestController.class).all(page)).withSelfRel()
                                                               .andAffordance(afford(
                                                                   methodOn(RequestController.class).newRequest(null))),
            linkTo(methodOn(RequestController.class).all(0)).withRel("pageFirst"),
            linkTo(methodOn(RequestController.class).all(entities.getTotalPages() - 1)).withRel("pageLast")
                 );

        if (entities.hasNext()) {
            model.add(linkTo(methodOn(RequestController.class).all(page + 1)).withRel("pageNext"));
        }

        if (entities.hasPrevious()) {
            model.add(linkTo(methodOn(RequestController.class).all(page - 1)).withRel("pagePrev"));
        }

        return model;
    }
}
