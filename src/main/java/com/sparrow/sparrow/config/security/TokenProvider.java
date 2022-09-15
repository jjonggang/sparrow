package com.sparrow.sparrow.config.security;

import com.sparrow.sparrow.domain.user.User;
import com.sparrow.sparrow.domain.user.UserRepository;
import com.sparrow.sparrow.dto.response.ResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Value("${secret.key}")
    private String SECRET_KEY;

    @Value("${secret.key.refresh}")
    private String REFRESH_SECRET_KEY;

    public String createRefreshToken(User user){
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(30, ChronoUnit.DAYS)
        );
//        User user = userRepository.findById(userId).get();
        String refreshToken = Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한  SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, REFRESH_SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(user.getUserId().toString()) // sub
                .setIssuer("sparrow")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate) // exp
                .compact();

        String encodedToken = passwordEncoder.encode(refreshToken);
        user.setRefreshToken(encodedToken);
        userRepository.save(user);
        return refreshToken;
    }

    public String create(User user){
        // 기한은 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한  SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(user.getUserId().toString()) // sub
                .setIssuer("sparrow")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate) // exp
                .compact();
    }
    /*
    * { // header
    *   "alg":"HS512"
    * }.
    * { // payload
    *   "sub":"email",  // 토큰의 주인, unique identifier로 해야함.
    *   "iss":"demo app", // 만든사람
    *   "iat"1595733657", // 토큰 발행 날짜
    *   "exp":1596597657    // 토큰 만료 시간
    * }.
    * NSDfdasf14432da4as5d45sad // signature Issuer가 발행한 서명으로 토큰의 유효성 검사에 사용된다. Issuer가 발행한 것
    *
    *
    *
    * */

    public String validateAndGetUserId(String token){
        // parseClaimsJws 메서드가 Base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴, 위조라면 예외를 날림
        // 그 중 우리는 userId 가 필요하므로 getBody를 부른다.
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
//        String strUserId = claims.getSubject();
//        Long userId = Long.valueOf(strUserId);
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()){
//            if (!claims.getExpiration().before(new Date())) {
//                return claims.getSubject();
//            }
//        }else{
//            throw new RuntimeException("존재하지 않는 유저입니다.");
//        }


        return claims.getSubject();
    }

    public String validateRefreshToken(String refreshToken){
        // refresh 객체에서 refreshToken 추출
//        String refreshToken = refreshTokenObj.getRefreshToken();
        // 검증
        Claims claims = Jwts.parser()
                .setSigningKey(REFRESH_SECRET_KEY)
                .parseClaimsJws(refreshToken)
                .getBody();
        String strUserId = claims.getSubject();
        Long userId = Long.valueOf(strUserId);
        Optional<User> user = userRepository.findById(userId);

        //refresh 토큰의 만료시간이 지나지 않았을 경우, 새로운 access 토큰을 생성합니다.
        if(user.isPresent()){
            // refresh token 비교
            if(passwordEncoder.matches(refreshToken, user.get().getRefreshToken())){
                if (!claims.getExpiration().before(new Date())) {
                    return create(user.get());
                }
            }else{
                throw new RuntimeException("refresh token이 일치하지 않습니다. 새로 발급해주세요.");
            }
        }else{
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }
        return null;
    }
}
