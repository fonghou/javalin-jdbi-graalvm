package com.emmajiugo.demo;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {

    @SqlUpdate("INSERT INTO users (id, name) VALUES (?, ?)")
    void insert(long id, String name);

}
