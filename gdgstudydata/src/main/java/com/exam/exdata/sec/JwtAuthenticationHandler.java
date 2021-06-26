package com.exam.exdata.sec;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class JwtAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		String token = JwtUtils.createToken(authentication); //인증정보를 담은 JWT 생성
		
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		out.write( mapper.writeValueAsString( Map.of("token", token) ) );
//		out.flush();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.error("onAuthenticationFailure");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		out.write( mapper.writeValueAsString( Map.of("msg", "UNAUTHORIZED") ) );
	}

}
