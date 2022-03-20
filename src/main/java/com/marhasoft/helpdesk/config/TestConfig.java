package com.marhasoft.helpdesk.config;

import com.marhasoft.helpdesk.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService dbService;

    @Bean//Toda vez que o perfil estiver ativo esse metodo sobe de forma automatica
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }
}
