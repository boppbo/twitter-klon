package de.hska.twitterklon.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@ComponentScan(basePackages = {"de.hska.twitterklon"})
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class })
@EnableSpringDataWebSupport
public class ApplicationConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfiguration.class, args);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        return JacksonObjectMapperFactory.objectMapperForRestEndpoint();
    }
}

