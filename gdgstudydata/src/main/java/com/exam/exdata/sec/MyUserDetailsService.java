package com.exam.exdata.sec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// username 에 해당하는 사용자가 존재하면, 해당 사용자 정보를 담은 UserDetails 객체를 반환
		// username 에 해당하는 사용자가 존재하지 않거나, 존재하지만 어떤 권한도 갖고 있지 않은 경우
		// UsernameNotFoundException 예외 발생
		
//		return new User(username, password, authorities);
		// authorities는 SimpleGrantedAuthority 객체들의 리스트 
		// User 객체의 지정하지 않은 속성값들은 모두 true 가 디폴트
		return null;
	}

}
