package com.impat.green_bill.config;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@Configuration
public class LiquibaseMongoConfig {

    @Value("${liquibase.changelog}")
    private String changelogFile;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;


    @Bean
    public Liquibase liquibase() {
        try {
            Database database = DatabaseFactory.getInstance().openDatabase(
                    mongoUri, null, null, null, new ClassLoaderResourceAccessor()
            );

            Liquibase liquibase = new Liquibase(changelogFile, new ClassLoaderResourceAccessor(), database);
            liquibase.update("");

            return liquibase;
        } catch (LiquibaseException e) {
            throw new RuntimeException("Error configuring Liquibase with MongoDB", e);
        }
    }
}
