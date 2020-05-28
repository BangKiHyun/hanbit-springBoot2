package me.bang.springbootweb.ouath;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

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
}
