package com.study.myshop.security;

import com.study.myshop.authentication.CustomUserDetails;
import com.study.myshop.domain.member.Member;
import com.study.myshop.domain.member.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidity = 1000L * 60 * 30; //30분
    private final long refreshTokenValidity = 1000L * 60 * 60 * 24 * 7; //7일

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        System.out.println("jwt.secret = " + secretKey);
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /* jwt 토큰 생성 */
    /**
     *  access token 생성
     */
    public String createAccessToken(Long userId, Role role) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidity)) //30분 유효
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *  refresh token 생성
     */
    public String createRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidity)) //7일 유효
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
//        String subject = Jwts.parser().setSigningKey(key).parseClaimsJws(token)
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
                .getBody().getSubject();
        return Long.parseLong(subject);
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

    /* access 토큰 추출 */
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }



    public Authentication getAuthentication(String token, Member member) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(member.getRole().name()));

        CustomUserDetails userDetails = new CustomUserDetails(member);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

}
