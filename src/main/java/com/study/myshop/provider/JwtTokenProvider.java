package com.study.myshop.provider;

import com.study.myshop.authentication.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final String secretKey = "my-secret-key-my-secret-key-my-secret-key"; //실제 프로젝트에선 환경 변수로 관리, 256bit 이상 길이 필요
    private final long validityInMilliseconds = 3600000; //1시간 유효
    private final Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    /* jwt 생성 */
    public String createToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) //발급 시간
                .setExpiration(validity) //만료 시간
                .signWith(key, SignatureAlgorithm.HS256) //서명
                .compact();
    }

    /* jwt 검증 */
    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        String role = claims.get("role", String.class); //role 정보 추출

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

        //jwt에서 추출한 정보를 기반으로 CustomUserDetails객체 생성
        UserDetails userDetails = new CustomUserDetails(username, authorities);

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

    }

}
