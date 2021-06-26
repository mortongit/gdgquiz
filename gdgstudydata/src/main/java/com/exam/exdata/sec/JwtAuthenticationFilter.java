package com.exam.exdata.sec;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

//요청 헤더의 JWT를 읽어서, 사용자 정보를 시큐리티컨텍스트에 저장
public class JwtAuthenticationFilter extends GenericFilterBean {
//	@Autowired
//	private JwtTokenProvider JwtTokenProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("JwtAuthenticationFilter!");
		String token = JwtUtils.resolveToken((HttpServletRequest) request); // 헤더에서 JWT 가져오기
		if (token != null && JwtUtils.validateToken(token)) { // 유효한 토큰인지 확인
			Authentication authentication = JwtUtils.getAuthentication(token); // 토큰으로부터 사용자 정보(Authentication) 가져오기
			SecurityContextHolder.getContext().setAuthentication(authentication); // SecurityContext에 사용자 정보(Authentication) 저장
		}
		chain.doFilter(request, response);
	}
}
