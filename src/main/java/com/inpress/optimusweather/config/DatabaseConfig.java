package com.inpress.optimusweather.config;

import com.inpress.optimusweather.security.AuditorAwareImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableTransactionManagement
@EntityScan({"com.inpress"})
@EnableJpaRepositories(basePackages = {"com.inpress.optimusweather.repository"})
public class DatabaseConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory em) {
        return new JpaTransactionManager(em);
    }

}
