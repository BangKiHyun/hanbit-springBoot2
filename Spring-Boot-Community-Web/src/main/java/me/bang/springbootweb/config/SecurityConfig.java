package me.bang.springbootweb.config;

import me.bang.springbootweb.ouath.ClientResource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    @ConfigurationProperties("facebook")
    public ClientResource facebook() {
        return new ClientResource();
    }

    @Bean
    @ConfigurationProperties("google")
    public ClientResource google() {
        return new ClientResource();
    }

    @Bean
    @ConfigurationProperties("kakao")
    public ClientResource kakao() {
        return new ClientResource();
    }
}
