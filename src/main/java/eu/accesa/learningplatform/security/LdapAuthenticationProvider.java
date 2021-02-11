package eu.accesa.learningplatform.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.ArrayList;
import java.util.Hashtable;

@Component
public class LdapAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapAuthenticationProvider.class);

    private static final String AUTHENTICATION_TYPE = "simple";
    public static final String ACCESA_DOMAIN = "accesa";
    private static final String LDAP_CTX_FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";

    @Value("${ldap.urls}")
    private String ldapUrls;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();

        Object credentials = authentication.getCredentials();
        if (credentials != null && authenticate(username, credentials.toString())) {
            return new UsernamePasswordAuthenticationToken(
                    ACCESA_DOMAIN + "\\" + username,
                    credentials.toString(),
                    new ArrayList<>()
            );
        }
        throw new BadCredentialsException("Username or password are invalid!");
    }

    public boolean authenticate(String username, String password) {
        Hashtable<String, String> environment = new Hashtable<>();
        environment.put(Context.INITIAL_CONTEXT_FACTORY, LDAP_CTX_FACTORY);
        environment.put(Context.PROVIDER_URL, ldapUrls);
        environment.put(Context.SECURITY_AUTHENTICATION, AUTHENTICATION_TYPE);
        environment.put(Context.SECURITY_PRINCIPAL, ACCESA_DOMAIN + "\\" + username);
        environment.put(Context.SECURITY_CREDENTIALS, password);

        try {
            DirContext authContext = new InitialDirContext(environment);
            LOGGER.info("User is authenticated");
            authContext.close();
        } catch (NamingException e) {
            LOGGER.info("User is NOT authenticated");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
