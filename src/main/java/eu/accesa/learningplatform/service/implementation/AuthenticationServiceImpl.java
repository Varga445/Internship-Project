package eu.accesa.learningplatform.service.implementation;

import eu.accesa.learningplatform.authentication.AccessToken;
import eu.accesa.learningplatform.authentication.AuthRequest;
import eu.accesa.learningplatform.authentication.OAuth2Constants;
import eu.accesa.learningplatform.authentication.PrivateClaimType;
import eu.accesa.learningplatform.security.LdapAuthenticationProvider;
import eu.accesa.learningplatform.service.AuthenticationService;
import eu.accesa.learningplatform.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TokenService tokenService;
    private final LdapAuthenticationProvider ldapAuthenticationProvider;

    public AuthenticationServiceImpl(TokenService tokenService,
                                     LdapAuthenticationProvider ldapAuthenticationProvider) {
        this.tokenService = tokenService;
        this.ldapAuthenticationProvider = ldapAuthenticationProvider;
    }

    @Transactional
    public AccessToken authenticate(AuthRequest authRequest) {
        return loginWithPassword(authRequest);
    }

    private AccessToken loginWithPassword(AuthRequest authRequest) {
        Authentication authenticate1 =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

        Authentication authenticate = ldapAuthenticationProvider.authenticate(authenticate1);

        if (authenticate != null) {

            String userName = authRequest.getUsername();
            String email = userName + "@accesa.eu";

            Map<String, String> privateClaimMap = privateClaims(email);

            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expirationTime = now.plus(1, ChronoUnit.DAYS);

            return getAccessToken(now, expirationTime, privateClaimMap, userName, email);

        } else throw new RuntimeException("Username or password not valid.");
    }

    private AccessToken getAccessToken(LocalDateTime now, LocalDateTime expirationTime,
                                       Map<String, String> privateClaimMap, String userName, String email) {
        return new AccessToken().accessToken(tokenService.generateJwtToken(userName, privateClaimMap, null))
                .tokenType(OAuth2Constants.BEARER_TYPE).active(true).refreshToken(tokenService.getRefreshToken(email))
                .expiresAt(java.sql.Date.valueOf(expirationTime.toLocalDate()))
                .expiresIn(Period.between(now.toLocalDate(), expirationTime.toLocalDate()).getDays())
                .expiresIn(ChronoUnit.SECONDS.between(now, expirationTime));
    }

    private static Map<String, String> privateClaims(String email) {
        Map<String, String> privateClaimMap = new HashMap<>();
        if (email != null) {
            privateClaimMap.put(PrivateClaimType.MAIL.getType(), email);
        }

        return privateClaimMap;
    }

    @Override
    public boolean verifyToken(String token) {
        return tokenService.verifyToken(token);
    }
}