package com.foxminded.university.dao;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Connector {
    private BasicDataSource dataSource;

    public Connector(String filename) {
        dataSource = new BasicDataSource();
        ResourceBundle bundle = ResourceBundle.getBundle(filename);
        dataSource.setUrl(bundle.getString("url"));
        dataSource.setUsername(bundle.getString("username"));
        dataSource.setPassword(bundle.getString("password"));
        dataSource.setMinIdle(parseIntProperty(bundle, "min.idle"));
        dataSource.setMaxIdle(parseIntProperty(bundle, "max.idle"));
        dataSource.setMaxOpenPreparedStatements(parseIntProperty(bundle, "max.open.statements"));
    }

    private Integer parseIntProperty(ResourceBundle bundle, String key) {
        return Integer.parseInt(bundle.getString(key));
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

