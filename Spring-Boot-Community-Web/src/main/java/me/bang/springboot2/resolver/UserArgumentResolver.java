package me.bang.springboot2.resolver;

import me.bang.springboot2.annotation.SocialUser;
import me.bang.springboot2.domain.User;
import me.bang.springboot2.enums.SocialType;
import me.bang.springboot2.repository.UserRepository;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static me.bang.springboot2.enums.SocialType.*;

@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private UserRepository userRepository;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) { //해당 파라미터를 지원할지 여부를 반환한다. true를 반환하면 resolveArgument메서드가 실행
        return parameter.getParameterAnnotation(SocialUser.class) != null &&
                parameter.getParameterType().equals(User.class); //SocialUser 어노테이션이 있고 User인 파라미터만 true를 반환
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
        User user = (User) session.getAttribute("user");
        return getUser(user, session);
    }

    private User getUser(User user, HttpSession session) { //인증된User 객체를 만드는 메인 메서드
        if(user == null) {
            try {
                OAuth2AuthenticationToken authentication = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
                Map<String, Object> map = authentication.getPrincipal().getAttributes();
                User convertUser = convertUser(authentication.getAuthorizedClientRegistrationId(), map);

                user = userRepository.findByEmail(convertUser.getEmail());
                if (user == null) { user = userRepository.save(convertUser); }

                setRoleIfNotSame(user, authentication, map);
                session.setAttribute("user", user);
            } catch (ClassCastException e) {
                return user;
            }
        }
        return user;
    }

    private User convertUser(String authority, Map<String, Object> map) { //사용자의 인증된 소셜 미디어 타입에 따라 빌더를 사용하여 User 객체를 만들어주는 가교 역할
        if(FACEBOOK.isEquals(authority)) return getModernUser(FACEBOOK, map);
        else if(GOOGLE.isEquals(authority)) return getModernUser(GOOGLE, map);
        else if(KAKAO.isEquals(authority)) return getKaKaoUser(map);
        return null;
    }

    private User getModernUser(SocialType socialType, Map<String, Object> map) { //메서드는 페이스북이나 구글과 같이 공통되는 명명규칙을 가진 그룹을 User 객체로 매핑해줍니다.
        return User.builder()
                .name(String.valueOf(map.get("name")))
                .email(String.valueOf(map.get("email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(socialType)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private User getKaKaoUser(Map<String, Object> map) { //메서드는 (키의 네이밍값이 타 소셜 미디어와 다른) 카카오 회원을 위한 메서드.
        Map<String, String> propertyMap = (HashMap<String, String>) map.get("properties");
        return User.builder()
                .name(propertyMap.get("nickname"))
                .email(String.valueOf(map.get("kaccount_email")))
                .principal(String.valueOf(map.get("id")))
                .socialType(KAKAO)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private void setRoleIfNotSame(User user, OAuth2AuthenticationToken authentication, Map<String, Object> map) { //메서드는 인증된 authentication이 권한을 갖고 있는지 체크하는 용도로 쓰임.
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority(user.getSocialType().getRoleType()))) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(map, "N/A", AuthorityUtils.createAuthorityList(user.getSocialType().getRoleType())));
        }
    }
}
