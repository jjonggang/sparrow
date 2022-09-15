package com.sparrow.sparrow.controller.user;


import com.sparrow.sparrow.config.security.TokenProvider;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class UserApiController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    @Value("${secret.key.refresh.recreate}")
    private String SECRET_KEY;
    // refresh token을 생성한다.
//    @GetMapping("/user/create-refresh")
//    public ResponseEntity<?> createRefreshToken(@AuthenticationPrincipal String strUserId,
//                                   @RequestParam("refreshPassword")String refreshPassword){
//        Long userId = Long.parseLong(strUserId);
//        String refreshToken = null;
//        if(refreshPassword.equals(SECRET_KEY)){
//            refreshToken = tokenProvider.createRefreshToken(userId);
//        }else{
//            throw new RuntimeException("refreshPassword가 잘못됐습니다.");
//        }
//        return ResponseEntity.ok().body(refreshToken);
//    }

    @GetMapping("/no-login/user/create-access")
    public ResponseEntity<?> createAccessToken(@RequestParam("refreshToken")String refreshToken,
                                               @RequestParam("refreshPassword")String refreshPassword){
        String accessToken = null;
        if(refreshPassword.equals(SECRET_KEY)){
            accessToken = tokenProvider.validateRefreshToken(refreshToken);
        }
        return ResponseEntity.ok().body(accessToken);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal String strUserId){
        Long userId = Long.parseLong(strUserId);
        User user = userService.getUser(userId);
        return ResponseEntity.ok().body(userId);
    }


}
