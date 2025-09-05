package com.tcomplex.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    private static final BasicDataSource ds = new BasicDataSource();

    static {
        try {
            Properties p = new Properties();
            InputStream in = DB.class.getClassLoader().getResourceAsStream("db.properties");
            if (in != null) p.load(in);

            String url  = p.getProperty("db.url", "jdbc:mysql://localhost:3306/tcomplex?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true");
            String user = p.getProperty("db.user", "root");
            String pass = p.getProperty("db.password", "");

            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl(url);
            ds.setUsername(user);
            ds.setPassword(pass);
            ds.setInitialSize(3);
            ds.setMaxTotal(20);
        } catch (Exception e) {
            throw new RuntimeException("Cannot init datasource", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
