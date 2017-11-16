package de.hska.twitterklon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = "de.hska.twitterklon")
@PropertySource("classpath:application.properties")
public class TestApplicationConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Configuration
    @Profile("DEV")
    @PropertySource("classpath:dev.application.properties")
    public static class Dev {
    }

    @Configuration
    @Profile("DS")
    @PropertySource("classpath:DS.application.properties")
    public static class DS {
    }
}
