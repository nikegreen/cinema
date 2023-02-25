/**
 * (с) 2022-2023 Nike Z.
 * @author nikez
 * @version $Id: $Id
 */
package ru.job4j.cinema.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Считывает конфигурацию БД из файла db.properties
 *  Переменные:
 *  jdbc.driver - драйвер БД
 *  jdbc.url - путь и имя БД (имя = 'cinema')
 *  jdbc.username - имя пользователя БД
 *  jdbc.password - пароль пользователя БД
 */
@Configuration
@PropertySource("classpath:db.properties")
public class DataSourceConfiguration {
    @Bean
    public BasicDataSource loadPool(@Value("${jdbc.driver}") String driver,
                               @Value("${jdbc.url}") String url,
                               @Value("${jdbc.username}") String username,
                               @Value("${jdbc.password}") String password) {
        BasicDataSource pool = new BasicDataSource();
        pool.setDriverClassName(driver);
        pool.setUrl(url);
        pool.setUsername(username);
        pool.setPassword(password);
        return pool;
    }
}
