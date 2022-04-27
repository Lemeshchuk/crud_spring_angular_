package com.nix.lemeshuk.config;

import com.nix.lemeshuk.dao.RoleRepository;
import com.nix.lemeshuk.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@RequiredArgsConstructor
@EnableJpaRepositories(basePackageClasses = {UserRepository.class, RoleRepository.class}, enableDefaultTransactions = false)
public class JpaConfiguration {

    public final String DB_URL_KEY = "db.url";
    public final String DB_USER_KEY = "db.user";
    public final String DB_PASSWORD_KEY = "db.password";
    public final String DB_DRIVER_KEY = "db.driver";
    public final String HIBERNATE_DIALECT_KEY = "hibernate.dialect";
    public final String HIBERNATE_HBM2DDL_AUTO_KEY = "hibernate.hbm2ddl.auto";
    public final String HIBERNATE_HBM2DDL_IMPORT_FILES_KEY = "hibernate.hbm2ddl.import_files";

    private final Environment environment;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource);
        String PACKAGE_TO_SCAN = "com.nix.lemeshuk";
        entityManagerFactoryBean.setPackagesToScan(PACKAGE_TO_SCAN);

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        Properties hibernateProperties = new Properties();
        hibernateProperties.put(HIBERNATE_DIALECT_KEY, environment.getProperty(HIBERNATE_DIALECT_KEY));
        hibernateProperties.put(HIBERNATE_HBM2DDL_AUTO_KEY, environment.getProperty(HIBERNATE_HBM2DDL_AUTO_KEY));
        hibernateProperties.put(HIBERNATE_HBM2DDL_IMPORT_FILES_KEY, environment.getProperty(HIBERNATE_HBM2DDL_IMPORT_FILES_KEY));

        entityManagerFactoryBean.setJpaProperties(hibernateProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(environment.getProperty(DB_DRIVER_KEY));
        dataSource.setUrl(environment.getProperty(DB_URL_KEY));
        dataSource.setUsername(environment.getProperty(DB_USER_KEY));
        dataSource.setPassword(environment.getProperty(DB_PASSWORD_KEY));

        return dataSource;
    }
}