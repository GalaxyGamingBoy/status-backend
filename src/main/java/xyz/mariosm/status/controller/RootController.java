package xyz.mariosm.status.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class RootController {
    @GetMapping(path = "/")
    Links root() {
        return Links.of(
            linkTo(methodOn(RootController.class).root()).withSelfRel(),
            linkTo(methodOn(AuthController.class).root()).withRel("auth"),
            linkTo(methodOn(ProjectController.class).root()).withRel("projects"),
            linkTo(methodOn(RequestController.class).root()).withRel("requests"),
            linkTo(methodOn(DataController.class).root()).withRel("data")
                       );
    }
}
