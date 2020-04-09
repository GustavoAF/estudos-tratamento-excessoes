package com.tratamentoexcessoes.estudos.infrastructure.repository.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tratamentoexcessoes.estudos.infrastructure.repository.applicationproperties.ConfigurationsBd;
import com.zaxxer.hikari.HikariDataSource;

/*
 * Data Source padrão
 */
@Configuration
public class DataSourceConfigurations {
	
	@Autowired
	ConfigurationsBd configurationnBd;

    @Bean
	public DataSource dataSource(DataSourceProperties dataSourceProperties) {
		
		return DataSourceBuilder.create()
					.type(HikariDataSource.class)
					.url(configurationnBd.getDataSourceUrl())
					.username(configurationnBd.getDataSourceUser())
					.password(configurationnBd.getDataSourcePassword())
					.build();
		
	}

}
