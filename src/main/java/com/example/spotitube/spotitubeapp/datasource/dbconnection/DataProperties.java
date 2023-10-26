package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DataProperties {

    private Properties properties;

    public DataProperties() throws IOException {
        properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(getClass()
                    .getClassLoader()
                    .getResourceAsStream("database.properties")));
        } catch (IOException e) {
            throw new IOException("Can't access property file database.properties");
        }
    }

    public String getConnectionString() { return properties.getProperty("connectionString"); }

}