package eu.accesa.learningplatform.security;

import eu.accesa.learningplatform.controller.AuthTokenValidator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class LdapWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final AuthTokenValidator authTokenValidator;

    public LdapWebSecurityConfigurerAdapter(AuthTokenValidator authTokenValidator) {
        this.authTokenValidator = authTokenValidator;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public FilterRegistrationBean<AuthTokenValidator> loggingFilter(){
        FilterRegistrationBean<AuthTokenValidator> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(authTokenValidator);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll();

        http
                .logout()
                .deleteCookies("SESSIONID")
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login");
    }
}
