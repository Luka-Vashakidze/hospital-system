package com.solvd.hospital.persistence.mybatis;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public final class MyBatisConfig {

    private static final SqlSessionFactory SQL_SESSION_FACTORY;

    static {
        try (Reader reader = Resources.getResourceAsReader("mybatis-config.xml")) {
            SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Failed to load MyBatis configuration: " + e.getMessage());
        }
    }

    private MyBatisConfig() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return SQL_SESSION_FACTORY;
    }
}
