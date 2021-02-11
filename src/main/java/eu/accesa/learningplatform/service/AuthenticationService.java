package eu.accesa.learningplatform.service;

import eu.accesa.learningplatform.authentication.AccessToken;
import eu.accesa.learningplatform.authentication.AuthRequest;

public interface AuthenticationService {

    AccessToken authenticate(AuthRequest authRequest);

    boolean verifyToken(String token);
}