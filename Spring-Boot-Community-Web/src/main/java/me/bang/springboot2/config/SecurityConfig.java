package me.bang.springboot2.config;

import me.bang.springboot2.ouath2.CustomOAuth2Provider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static me.bang.springboot2.enums.SocialType.*;

@Configuration
@EnableWebSecurity //웹에서 시큐리티 기능을 사용하겠다는 어노테이션
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception { //원하는 형식의 시큐리티 설정, 최적화
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        http
                .authorizeRequests()// 인증 메커니즘을 요청한 HttpServletRequest 기반으로 설정
                .antMatchers("/", "/oauth2/**", "/login/**", "/css/**", "/images/**", "/js/**", "/console/**").permitAll() //설정한 리퀘스트 패턴을 누구나 접근할 수 있도록 설정
                .antMatchers("/facebook").hasAuthority(FACEBOOK.getRoleType()) //요청 방식을 리스트 형식으로 설정
                .antMatchers("/google").hasAuthority(GOOGLE.getRoleType())
                .antMatchers("/kakao").hasAuthority(KAKAO.getRoleType())
                .anyRequest().authenticated() //설정한 요청 이외의 리퀘스트 요청을 표현 / 해당 요청은 인증된 사용자만 사용할 수 있음
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/loginFailure")
                .and()
                .headers().frameOptions().disable()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login")) //인증의 진입 지점, 인증되지 않은 사용자가 허용하지 않는 경로로 리퀘스트 할 경우 /login으로 이동
                .and()
                .formLogin()
                .successForwardUrl("/board/list") //로그인에 성공하면 설정된 경로로 포워딩됨
                .and()
                .logout() //로그아웃에 대한 설정
                .logoutUrl("/logout") //코드에서는 로그아웃이 수행될 URL
                .logoutSuccessUrl("/") //로그아웃이 성공했을 때 포워딩될 URL
                .deleteCookies("JSESSIONID") // 로그아웃을 성공했을때 삭제될 쿠키값
                .invalidateHttpSession(true) //설정된 세션의 무효화를 수행하게끔 설정되어 있음
                .and()
                .addFilterBefore(filter, CsrfFilter.class) //문자 인코딩 필터보다 CrsfFilter를 먼저 실행하도록 설정
                .csrf().disable();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .jwkSetUri("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .build());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if ("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }
        if ("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email")
                    .build();
        }
        return null;
    }
}