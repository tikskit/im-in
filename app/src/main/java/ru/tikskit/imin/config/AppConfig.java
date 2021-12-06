package ru.tikskit.imin.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("hereGeodata");
    }

    /**
     * Шифровальщик/дешифровальщик для зашифрованных настроек в application.yml
     *
     * @param password Пароль для шифрования/дешифрования
     */
    @Bean
    public StringEncryptor appPropertiesEncryptor(@Value("${properties.password}") String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        encryptor.setPassword(password);
        encryptor.setIvGenerator(new RandomIvGenerator());
        return encryptor;
    }

    @Bean
    public WKTReader wktReader() {
        return new WKTReader();
    }

}
