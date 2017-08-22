package config;

import dao.TwinAPIJdbcDao;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by sergeybp on 20.07.17.
 */
public class JdbcDaoContextConfiguration {

    public TwinAPIJdbcDao twinAPIJdbcDao() {
        return new TwinAPIJdbcDao(this.dataSource());
    }

    private DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:twinapi.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        return dataSource;
    }
}