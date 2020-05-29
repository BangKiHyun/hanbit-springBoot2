package me.bang.springbootweb.config;

import me.bang.springbootweb.domain.enums.SocialType;
import me.bang.springbootweb.oauth.ClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CompositeFilter;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;

import static me.bang.springbootweb.domain.enums.SocialType.*;

@Configuration
@EnableWebSecurity // 웹에서 시큐리티 기능을 사용하겠다는 애노테이션
@EnableOAuth2Client // OAuth2 설정을 위한 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 요청,권한,기타 설정에대해서 필수적으로 최적화한 설정을 위한 시큐리티 설정
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        http
                .authorizeRequests()
                .antMatchers("/", "/login/**", "/css/**", "images/**", "/js/**",
                        "/console?**").permitAll()
                .antMatchers("/facebook").hasAnyAuthority(FACEBOOK.getRoleType())
                .antMatchers("/google").hasAnyAuthority(GOOGLE.getRoleType())
                .antMatchers("/kakao").hasAnyAuthority(KAKAO.getRoleType())
                .anyRequest().authenticated() // 설정한 요청 이외의 리퀘스트 요청
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint( // 인증의 진입 지점
                        "/login")) // 인증되지 않은 사용자가 허용되지 않은 경로로 리퀘스트 요청할 경우 "/login"으로 이동
                .and()
                .formLogin()
                .successForwardUrl("/board/list") // 로그인 성공시 설정된 경로로 포워딩
                .and()
                .logout()// 로그이웃에 대한 설정
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // 성공했을 때 포워딩될 URL 설정
                .deleteCookies("JSESSIONID") // 로그인 성공시 삭제될 쿠키값 설정
                .invalidateHttpSession(true) //세션 무효화 설정
                .and()
                .addFilterBefore(filter, CsrfFilter.class) // 문자 인코딩 필터(filter) 보다 CsrfFilter 를 먼저 수행하게 설정
                .csrf().disable();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    private Filter oauth2Filter() { // 각 소셜 미디어 필터를 리스트 형식으로 한꺼번에 설정하여 반환
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(oauth2Filter(facebook(), "/login/facebook", FACEBOOK));
        filters.add(oauth2Filter(google(), "/login/google", GOOGLE));
        filters.add(oauth2Filter(kakao(), "/login/kakao", KAKAO));
        filter.setFilters(filters);
        return filter;
    }

    private Filter oauth2Filter(ClientResource client, String path, SocialType socialType) { // 각 소셜 미디어 타입을 받아서 필터 설정
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oAuth2ClientContext);

        filter.setRestTemplate(template);
        filter.setTokenServices(new ClientResource.UserTokenService(client, socialType));
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> response.sendRedirect("/" + socialType.getValue() + "/complete"));
        filter.setAuthenticationFailureHandler((request, response, exception) -> response.sendRedirect("/error"));
        return filter;
    }

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
