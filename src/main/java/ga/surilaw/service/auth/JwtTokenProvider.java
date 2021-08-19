package ga.surilaw.service.auth;

import ga.surilaw.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 *JWT 관리 클래스 (생성, 검증, 등등)
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;

    private long tokenValidTime = 1000L * 60 * 30;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getId()); //claims 생성 후 subject 입력
        claims.put("email", member.getEmail()); //member의 아이디가 되는 이메일도 데이터 저장

        List<String> roles = new ArrayList<>(); //권한을 넣을 리스트

        for (GrantedAuthority authority : member.getAuthorities()) { //권한을 문자열 형태로 추출해 저장
            roles.add(authority.getAuthority());
        }

        claims.put("roles", roles); //권한 저장

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) //앞선 데이터 저장
                .setIssuedAt(now) //발행시간 저장
                .setExpiration(new Date(now.getTime()+tokenValidTime)) //만료시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) //사인 방식 저장
                .compact();
    }

    //인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    //토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //요청의 헤더에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("TOKEN");
    }

    //토큰 유효성 확인 메소드
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
