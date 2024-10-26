package xyz.mariosm.status.assemblers;

import com.influxdb.query.FluxTable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import xyz.mariosm.status.controller.DataController;
import xyz.mariosm.status.controller.ProjectController;
import xyz.mariosm.status.controller.RequestController;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.Request;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DataModelAssembler implements
                                RepresentationModelAssembler<FluxTable, EntityModel<FluxTable>> {

    public EntityModel<FluxTable> toModel(FluxTable entity, Request request, String time) {
        EntityModel<FluxTable> model =
            EntityModel.of(entity,
                           linkTo(methodOn(DataController.class).one(request.getId(), time)).withSelfRel(),
                           linkTo(methodOn(DataController.class).allFromProject(request.getProject().getId(),
                                                                                time)).withRel("projectData"),
                           linkTo(methodOn(DataController.class).all(time)).withRel("allData"),
                           linkTo(methodOn(RequestController.class).one(request.getId())).withRel("request"),
                           linkTo(methodOn(ProjectController.class).one(request.getProject().getId())).withRel("project"));

        return model;
    }

    @Override
    public EntityModel<FluxTable> toModel(FluxTable entity) {
        return EntityModel.of(entity);
    }
}
