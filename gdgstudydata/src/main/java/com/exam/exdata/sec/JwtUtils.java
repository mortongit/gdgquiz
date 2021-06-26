package com.exam.exdata.sec;

import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//@RequiredArgsConstructor
//@Component
public class JwtUtils {

    private static String secretKey = Base64.getEncoder().encodeToString("MY_SECRET_KEY".getBytes());
    private static long tokenValidTime = 30 * 60 * 1000L; // 토큰 유효시간 30분
//    private final UserDetailsService userDetailsService;

//    @PostConstruct // 객체 초기화
//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString("MY_SECRET_KEY".getBytes()); //secretKey를 Base64로 인코딩
//    }

    // JWT 토큰 생성
    public static String createToken(Authentication auth) {
        Claims claims = Jwts.claims().setSubject(auth.getName()); // JWT payload에 저장할 클레임 생성
        List<String> roles = auth.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList());
        claims.put("roles", roles); // 사용자의 역할(ROLE)을 저장
        Date now = new Date();
        return Jwts.builder()
              .setClaims(claims) // 정보 저장
              .setIssuedAt(now) // 토큰 발행 시간 설정
              .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 만료 시간 설정
              .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
              .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public static Authentication getAuthentication(String token) {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
    		JwtParser parser = Jwts.parser().setSigningKey(secretKey);
    		Jws<Claims> claims = parser.parseClaimsJws(token);
    		String username = claims.getBody().getSubject();
    		List<GrantedAuthority> roles = (List<GrantedAuthority>) claims.getBody().get("roles",List.class).stream().map(r-> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
    		return new UsernamePasswordAuthenticationToken(username, "", roles);
    }

    // 토큰에서 회원 정보 추출
//    public String getUserPk(String token) {
//        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
//    }

    // 요청 헤더에서 토큰 값을 가져오기 "Authorization : Bearer 토큰"
    public static String resolveToken(HttpServletRequest request) {
    	String header = request.getHeader("Authorization");
       if (header == null || !header.startsWith("Bearer ")) return null;
       return header.substring("Bearer ".length()).trim();
    }

    // 토큰의 유효성 + 만료일자 확인
    public static boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}