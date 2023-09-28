package com.example.spotitube.spotitubeapp.datasource.dbconnection;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataProperties {

    private Logger logger = Logger.getLogger(getClass().getName());
    private Properties properties;

    public DataProperties() throws Exception {
        properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(getClass()
                    .getClassLoader()
                    .getResourceAsStream("database.properties")));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't access property file database.properties", e);
            throw new Exception();
        }
    }

    public String connectionString() { return properties.getProperty("connectionString"); }

    public String driverString() {
        return properties.getProperty("driver");
    }


}