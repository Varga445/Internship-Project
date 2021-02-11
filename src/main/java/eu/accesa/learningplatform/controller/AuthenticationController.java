package eu.accesa.learningplatform.controller;

import eu.accesa.learningplatform.authentication.AccessToken;
import eu.accesa.learningplatform.authentication.AuthRequest;
import eu.accesa.learningplatform.service.AuthenticationService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@OpenAPIDefinition
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    public AccessToken authenticate(@RequestBody AuthRequest authRequest) {
        return authenticationService.authenticate(authRequest);
    }
}
