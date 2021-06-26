package com.exam.exdata.sec;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configurable //공식 가이드 예제에는 붙어있는데, 안 붙여도 잘 된다
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
//	@Autowired
//	private JwtTokenProvider jwtTokenProvider;
//	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	public void configure(WebSecurity web) throws Exception {
		// 정적 자원에 대해서는 스프링 시큐리티를 적용하지 않도록 설정
		web.ignoring().antMatchers("/css/**", "/js/**", "image/**", "/fonts/**", "/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/home.html").permitAll() //로그인 여부에 상관없이 회원가입 경로는 접근가능 
//			.antMatchers("/join", "/error/**").permitAll() //로그인 여부에 상관없이 회원가입 경로는 접근가능 
//			.antMatchers(HttpMethod.POST, "/members").permitAll() 
//			.antMatchers("/admin/**").hasRole("ADMIN") //스프링 시큐리티에서 사용자역할이 ROLE_이 없는 경우 자동으로 앞에 붙여준다
			.antMatchers("/**").authenticated(); //나머지 모든 경로들은 로그인해야 접근 가능

		http.formLogin() //폼 로그인 사용
//			.loginPage("/login")
//			.loginProcessingUrl("/login")
//			.defaultSuccessUrl("/")
//			.failureUrl("/login?error=1")
//    .usernameParameter("username")
//    .passwordParameter("password")
    .successHandler(jwtAuthenticationHandler())
    .failureHandler(jwtAuthenticationHandler())
    ;
		
		// 로그아웃 관련 설정
//		http.logout() //로그아웃 설정
//			.logoutUrl("/logout")
//			.logoutSuccessUrl("/")
//			.invalidateHttpSession(true)
		;

		//csrf 사용유무 설정 (모든 요청에 csrf 토큰을 함께 전달)
//		http.csrf(); //디폴트로 활성화 되는 듯
  
		// 로그인 프로세스 처리할 provider
//		http.authenticationProvider(authProvider);
		
		// UsernamePasswordAuthenticationFilter 앞에 jwtAuthenticationFilter 추가
		//  SessionCreationPolicy.ALWAYS : 스프링시큐리티가 항상 세션을 생성
		//  SessionCreationPolicy.IF_REQUIRED : 스프링시큐리티가 필요시 생성(기본) 
		//  SessionCreationPolicy.NEVER : 스프링시큐리티가 생성하지않지만, 기존에 존재하면 사용
		//  SessionCreationPolicy.STATELESS : 스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음 (JWT 같은토큰방식을 쓸때 사용하는 설정)
		http.csrf().disable(); //ajax 로그인을 위해서 비활성화
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public JwtAuthenticationHandler jwtAuthenticationHandler() {
		return new JwtAuthenticationHandler();
	}
	
	//스프링 시큐리티에서 비밀번호 암호화에 사용할 인코더 등록
	@Bean
	public PasswordEncoder passwordEncoder() {
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

	//스프링 시큐리티에서 사용할 UserDetailsService와 PasswordEncoder 설정 (굳이 안 해도 되는 듯)
//	@Autowired
//	private MemberService memberService;
	
	@Autowired
	private DataSource ds;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		provider.setUserDetailsService(new JdbcUserDetailsManager(ds));
//		provider.setPasswordEncoder(passwordEncoder());
//		auth.authenticationProvider(provider);
		auth.userDetailsService(new JdbcUserDetailsManager(ds)).passwordEncoder(passwordEncoder());
	}
	
}
