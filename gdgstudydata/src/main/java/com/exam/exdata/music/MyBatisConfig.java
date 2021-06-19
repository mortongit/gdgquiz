package com.exam.exdata.music;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.data.jdbc.repository.config.MyBatisJdbcConfiguration;

//@Configuration
//@EnableJdbcRepositories
//@Import(MyBatisJdbcConfiguration.class)
public class MyBatisConfig { }
//왜 이 파일이 있어야만 MyBatis 퀴리문이 실행되는 것일까?