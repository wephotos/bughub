package com.github.wephotos.bughub.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 全局配置
 * @author Dell-Aaron
 *
 */
@Configuration
public class AppConfig {

	/**
	 * 系统数据源
	 * @return
	 */
	@Bean(name = "bughubDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.bughub")
	public DataSource bughubDataSource() {
		return DataSourceBuilder.create().build();
	}

}
