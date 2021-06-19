package com.exam.exdata.member;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.mapping.FetchType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@Entity
public class Member implements UserDetails {
	
	@Id
	private Long id;
	
	private String memId;
	private String memPass;
	private String memName;
//	private List<MemberRole> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return this.roles;
		return null;
	}

	@Override
	public String getPassword() {
		return this.memPass;
	}

	@Override
	public String getUsername() {
		return this.memId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
