package aigc.backend.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class EnableCORS {
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("null") CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://graceful-unity-production.up.railway.app")
                                                      .allowedMethods("GET", "POST", "PUT", "DELETE")
                                                      .allowedHeaders("Authorization", "Content-Type", "Cookie")
                                                      .allowCredentials(true);
            }
        };
    }   
}
