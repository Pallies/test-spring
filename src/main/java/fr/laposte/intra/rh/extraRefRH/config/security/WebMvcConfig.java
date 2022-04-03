package fr.laposte.intra.rh.extraRefRH.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@PropertySource("classpath:security-config.properties")
public class WebMvcConfig {

    @Value("${registry.add-mapping}")
    private String mapping;
    @Value("${allowed-origins}")
    private String origins;
    @Value("${allowed-methods}")
    private String methods;
    @Value("${max-age}")
    private String maxAge;
    @Value("${allowed-headers}")
    private String allowedHeaders;
    @Value("${exposed-headers}")
    private String exposedHeaders;
    @Value("${allow-credentials}")
    private String credentials;

    /**
     * Configuration globales
     * des Cross-Origin Ressource sharing
     *
     * @return accord de l'origine des demandes
     */
    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(mapping)
                        .allowedOrigins(origins)
                        .allowedMethods(methods)
                        .maxAge(Long.parseLong(maxAge))
                        .allowedHeaders(allowedHeaders)
                        .exposedHeaders(exposedHeaders)
                        .allowCredentials(Boolean.parseBoolean(credentials));
            }
        };
    }
}
