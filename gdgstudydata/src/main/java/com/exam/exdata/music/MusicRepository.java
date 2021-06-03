package com.exam.exdata.music;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.format.annotation.DateTimeFormat;

//@Repository
//@RepositoryRestResource(path = "music")
public interface MusicRepository extends PagingAndSortingRepository<Music, Long> {
	//http://localhost:8080/musics/search/findByTitleContaining?title=제목
	List<Music> findByTitleContaining(String title);
	
	//http://localhost:8080/musics/search/findByArtist?artist=아티스트
	@Query("SELECT * FROM music WHERE artist LIKE '%'||:artist||'%'")
	List<Music> findByArtist(String artist);
	
	//http://localhost:8080/musics/search/musicsOn?regdate=2018-08-27
	List<Music> musicsOn(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate regdate);
	
	@Query(name = "searchMusicOn") //네임드 쿼리 이름 지정 작동하지 않음
	List<Music> sampleNamedQuery(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate regdate);
	
	
	List<Music> findAllByUrl(String url);
}
