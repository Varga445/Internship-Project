package eu.accesa.learningplatform.util;

import java.io.IOException;
import java.util.regex.Pattern;

import eu.accesa.learningplatform.authentication.token.validation.Jwt;
import eu.accesa.learningplatform.authentication.token.validation.JwtClaims;

public final class AuthUtils {

    private AuthUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Parse the jwt token from Authorization header.
     *
     * @param authorizationHeader authorization header.
     * @return JWT token
     */
    public static String extractBearerToken(String authorizationHeader) {
        String jwt = null;
        if (authorizationHeader != null) {
            String[] parts = authorizationHeader.split(" ");
            if (parts.length == 2) {
                String scheme = parts[0];
                String credentials = parts[1];
                Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
                if (pattern.matcher(scheme).matches()) {
                    jwt = credentials;
                }
            }
        }
        return jwt;
    }

    public static <T> T getClaim(String jwtToken, String key) {
        Jwt token = new Jwt(jwtToken);
        try {
            JwtClaims claims = token.getClaims();
            return claims.containsKey(key) ? ((T) claims.get(key)) : null;
        } catch (IOException e) {
            return null;
        }
    }

}
