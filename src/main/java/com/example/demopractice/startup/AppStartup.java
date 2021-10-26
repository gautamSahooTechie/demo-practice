package com.example.demopractice.startup;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
public class AppStartup {

    @Autowired
    private DataSource dataSource;

    @Value("classpath:day0-script.sql")
    private Resource resource;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        log.info("Starting to run the day0 script");
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, resource);
        } catch (SQLException sqlException) {
            log.error("SQLException Occurred", sqlException);
        }
        log.info("Successfully run the day0 script");
    }
}
