package com.emmajiugo.demo;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface UserDao {

    record User(int id, String name) {}

    @SqlUpdate("INSERT INTO users (id, name) VALUES (?, ?)")
    void insert(long id, String name);

}
