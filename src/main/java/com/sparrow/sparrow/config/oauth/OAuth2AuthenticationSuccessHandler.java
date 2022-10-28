package com.sparrow.sparrow.config.oauth;

import com.sparrow.sparrow.config.security.TokenProvider;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.domain.user.UserRepository;
import com.sparrow.sparrow.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//    @Autowired
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

//        login 성공한 사용자 목록.
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        UserDto userDto = userRequestMapper.toDto(oAuth2User);
//        Map<String, Object> kakao_account = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
//        String email = (String) kakao_account.get("email");
//        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
//        String nickname = (String) properties.get("nickname");



        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        String jwt = null;
        String jwtRefresh = null;
        if(user.isPresent()){
            jwt = tokenProvider.create(user.get());
            jwtRefresh = tokenProvider.createRefreshToken(user.get());
        }else{
            log.info("유저가 존재하지 않습니다.");
            throw new RuntimeException("success handler에서의 user not exists 에러.");
        }

        String url = makeRedirectUrl(jwt, jwtRefresh);
        System.out.println("url: " + url);

        if (response.isCommitted()) {
            logger.debug("응답이 이미 커밋된 상태입니다. " + url + "로 리다이렉트하도록 바꿀 수 없습니다.");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, url);
    }

    private String makeRedirectUrl(String token, String tokenRefresh) {
//        return UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2/redirect?access="+token+"&refresh="+tokenRefresh)
//                .build().toUriString();
        return UriComponentsBuilder.fromUriString("https://hibklhpldnfjhjbhdendooahknhcajgo.chromiumapp.org/oauth2?access="+token+"&refresh="+tokenRefresh)
                .build().toUriString();
    }
}