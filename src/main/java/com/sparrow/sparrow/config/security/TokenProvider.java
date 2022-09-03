package com.sparrow.sparrow.config.security;

import com.sparrow.sparrow.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    @Value("${secret.key}")
    private String SECRET_KEY;

    public String create(User user){
        // 기한은 1일로 설정
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(30, ChronoUnit.DAYS)
        );

        return Jwts.builder()
                // header에 들어갈 내용 및 서명을 하기 위한  SECRET_KEY
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload에 들어갈 내용
                .setSubject(user.getUserId().toString()) // sub
                .setIssuer("demo app")
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

        return claims.getSubject();
    }
}
