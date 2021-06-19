package com.exam.exdata.member;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface MemberRepository extends CrudRepository<Member, Long> {
	
}
