package com.sparrow.sparrow.controller.user;


import com.sparrow.sparrow.config.security.TokenProvider;
import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.dto.user.UserBasicResponseDto;
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
        return ResponseEntity.ok().body(new UserBasicResponseDto(user));
    }

    @GetMapping("/user/others")
    public ResponseEntity<?> getUserNoLogin(@RequestParam Long userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok().body(new UserBasicResponseDto(user));
    }


}
