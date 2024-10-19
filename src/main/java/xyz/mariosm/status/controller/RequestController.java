package xyz.mariosm.status.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.status.assemblers.RequestModelAssembler;
import xyz.mariosm.status.data.Request;
import xyz.mariosm.status.http.RequestPayload;
import xyz.mariosm.status.service.DataService;
import xyz.mariosm.status.service.ProjectService;
import xyz.mariosm.status.service.RequestService;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(path = "/request")
@RequiredArgsConstructor
public class RequestController {
    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final RequestService requestService;

    @Autowired
    private final RequestModelAssembler assembler;

    @GetMapping(path = "/")
    public Links root() {
        return Links.of(
            linkTo(methodOn(RequestController.class).root()).withSelfRel(),
            linkTo(methodOn(RequestController.class).all(0)).withRel("all"),
            linkTo(methodOn(RequestController.class).newRequest(null)).withRel("new")
                       );
    }

    @PostMapping(path = "/new")
    public ResponseEntity<?> newRequest(@Valid @RequestBody RequestPayload payload) {
        Request request = requestService.insert(new Request(payload, projectService));
        requestService.refreshFromDB();

        return ResponseEntity.created(linkTo(methodOn(RequestController.class).newRequest(payload)).toUri())
                             .body(assembler.toModel(request));
    }

    @GetMapping(path = "/{id}")
    public EntityModel<Request> one(@PathVariable UUID id) {
        return assembler.toModel(requestService.fetch(id));
    }

    @PutMapping(path = "/{id}")
    public EntityModel<Request> update(@PathVariable UUID id, @RequestBody @Valid RequestPayload payload) {
        Request model = requestService.update(id, new Request(payload, projectService));
        requestService.refreshFromDB();
        return assembler.toModel(model);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        requestService.delete(id);
        requestService.refreshFromDB();

        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/project/{id}")
    public CollectionModel<EntityModel<Request>> allFromProject
        (@PathVariable UUID id,
         @RequestParam(name = "page", defaultValue = "0", required = false) int page) {

        return assembler.toCollectionModel(requestService.fetchAllFromProject(id, page, 8), page);
    }

    @GetMapping(path = "/all")
    public CollectionModel<EntityModel<Request>> all(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page) {
        return assembler.toCollectionModel(requestService.fetchAll(page, 8), page);
    }
}
