package xyz.mariosm.status.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;
import xyz.mariosm.status.data.User;
import xyz.mariosm.status.http.UserPayload;
import xyz.mariosm.status.service.AuthService;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private final AuthService authService;

    @GetMapping(path = "/")
    List<Link> root() {
        return List.of(
            linkTo(methodOn(AuthController.class).root()).withSelfRel(),
            linkTo(methodOn(AuthController.class).login(null)).withRel("login"),
            linkTo(methodOn(AuthController.class).register(null)).withRel("register")
                      );
    }

    @PostMapping(path = "/register")
    EntityModel<Map<String, Object>> register(@RequestBody UserPayload payload) {
        return EntityModel.of(authService.register(new User(payload)),
                              linkTo(methodOn(AuthController.class).register(payload)).withSelfRel(),
                              linkTo(methodOn(AuthController.class).login(payload)).withRel("login"));
    }

    @PostMapping(path = "/login")
    EntityModel<Map<String, Object>> login(@RequestBody UserPayload payload) {
        Map<String, Object> data = authService.login(new User(payload));
        return EntityModel.of(data, linkTo(methodOn(AuthController.class).login(payload)).withSelfRel());
    }
}
