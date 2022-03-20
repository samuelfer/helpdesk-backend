package com.marhasoft.helpdesk.config;

import com.marhasoft.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String value;

    @Bean//Toda vez que o perfil estiver ativo esse metodo sobe de forma automatica
    public boolean instanciaDB() {
        if (value.equals("create")) {
            this.dbService.instanciaDB();
        }
        return false;
    }
}
