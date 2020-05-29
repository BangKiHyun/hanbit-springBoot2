package me.bang.springbootweb.oauth;

import me.bang.springbootweb.domain.enums.SocialType;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import java.util.List;
import java.util.Map;

public class ClientResource {

    @NestedConfigurationProperty // 해당 필드가 단일값이 아닌 중복으로 바인딩된다고 표시하는 어노테이션
    private AuthorizationCodeResourceDetails client = // 설정한 소셜의 프로퍼티값 중 'client'를 기준으로 하위의 키/값을 매핑해주는 대상 객체
            new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }

    public static class UserTokenService extends UserInfoTokenServices {
        public UserTokenService(ClientResource resource, SocialType socialType) {
            super(resource.getResource().getUserInfoUri(), resource.getClient().getClientId());
            setAuthoritiesExtractor(new OAuth2AuthoritiesExtractor(socialType));
        }

        public static class OAuth2AuthoritiesExtractor implements AuthoritiesExtractor {

            private String socialType;

            public OAuth2AuthoritiesExtractor(SocialType socialType) {
                this.socialType = socialType.getRoleType();
            }

            @Override
            public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
                return AuthorityUtils.createAuthorityList(this.socialType);
            }
        }
    }
}
