package ru.tikskit.imin.kladrimport.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.tikskit.imin.kladrimport.model.tar.Country;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {
    @Bean
    @ConfigurationProperties("app.target.datasource")
    DataSourceProperties pgDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    DataSource pgDataSource(DataSourceProperties pgDatasourceProperties) {
        return pgDatasourceProperties
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    LocalContainerEntityManagerFactoryBean pgEntityMangerFactory(EntityManagerFactoryBuilder builder,
                                                                 DataSource pgDataSource) {
        return builder
                .dataSource(pgDataSource)
                .packages(Country.class)
                .build();
    }
}
