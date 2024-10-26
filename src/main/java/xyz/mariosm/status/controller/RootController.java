package xyz.mariosm.status.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class RootController {
    List<Link> root() {
        return List.of(
            linkTo(methodOn(RootController.class).root()).withSelfRel(),
            linkTo(methodOn(AuthController.class).root()).withRel("auth"),
            linkTo(methodOn(ProjectController.class).root()).withRel("projects"),
            linkTo(methodOn(RequestController.class).root()).withRel("requests"),
            linkTo(methodOn(DataController.class).root()).withRel("data")
                      );
    }
}
