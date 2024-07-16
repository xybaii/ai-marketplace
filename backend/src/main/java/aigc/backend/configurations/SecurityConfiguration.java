package aigc.backend.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import aigc.backend.security.AuthEntryPointJwt;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    // @Autowired
    // private AuthEntryPointJwt unauthorizedHandler;

    public SecurityConfiguration(
        JwtAuthenticationFilter jwtAuthenticationFilter,
        AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authReq -> authReq.requestMatchers( 
                                                        "/register",
                                                        "/api/register/process",
                                                        "/login",
                                                        "/api/login/process",
                                                        "/api/purchase/webhook",
                                                        "/",
                                                        "/main-B2H2UHHS.js",
                                                        "/polyfills-N6LQB2YD.js",
                                                        "/ngsw.json",
                                                        "/index.html",
                                                        "/*.js", 
                                                        "/*.css", 
                                                        "/favicon.ico", 
                                                        "/manifest.webmanifest", 
                                                        "/assets/**", 
                                                        "/images/**"
                                                        )
                                                         .permitAll()
                                                         .anyRequest()
                                                         .authenticated())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider);
                // .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler));

        return http.build();
    }


}
