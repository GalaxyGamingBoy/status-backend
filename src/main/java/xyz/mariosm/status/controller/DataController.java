package xyz.mariosm.status.controller;

import com.influxdb.query.FluxTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.status.assemblers.DataModelAssembler;
import xyz.mariosm.status.data.Project;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.repository.RequestRepository;
import xyz.mariosm.status.service.DataService;
import xyz.mariosm.status.service.ProjectService;
import xyz.mariosm.status.service.RequestService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(path = "/data")
@RequiredArgsConstructor
public class DataController {
    @Autowired
    private final DataService dataService;

    @Autowired
    private final RequestService requestService;

    @Autowired
    private final RequestRepository requestRepository;

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final DataModelAssembler assembler;

    @GetMapping(path = "/")
    public Links root() {
        return Links.of(
            linkTo(methodOn(DataController.class).root()).withSelfRel(),
            linkTo(methodOn(DataController.class).all(null)).withRel("all"),
            linkTo(methodOn(DataController.class).allFromProject(null, null)).withRel("project"),
            linkTo(methodOn(DataController.class).one(null, null)).withRel("request")
                       );
    }

    @GetMapping(path = "/all")
    public CollectionModel<EntityModel<FluxTable>> all(
        @RequestParam(name = "t", defaultValue = "12h", required = false) String time) {
        return CollectionModel.of(this.dataService.fetchAll(time).stream().map(assembler::toModel).toList(),
                                  linkTo(methodOn(DataController.class).all(time)).withSelfRel());
    }

    @GetMapping(path = "/request/{id}")
    public EntityModel<FluxTable> one(@PathVariable UUID id,
                                      @RequestParam(name = "t", defaultValue = "12h", required = false) String time) {
        log.info(id);
        Request request = this.requestService.fetch(id);
        return this.assembler.toModel(this.dataService.fetchRequest(request, time), request, time);
    }

    @GetMapping(path = "/project/{id}")
    public CollectionModel<EntityModel<FluxTable>> allFromProject(@PathVariable UUID id,
                                                                  @RequestParam(name = "t", defaultValue = "12h",
                                                                      required = false) String time) {
        Project project = this.projectService.fetch(id);
        List<Request> requests = this.requestRepository.findAllByProject(project);
        List<EntityModel<FluxTable>> data =
            requests.stream().map((request) ->
                                      this.assembler.toModel(this.dataService.fetchRequest(request, time), request, time)
                                 ).toList();

        return CollectionModel.of(data,
                                  linkTo(methodOn(DataController.class).allFromProject(id, time)).withSelfRel(),
                                  linkTo(methodOn(DataController.class).all(time)).withRel("allData"),
                                  linkTo(methodOn(ProjectController.class).one(project.getId())).withRel("project"));
    }
}
