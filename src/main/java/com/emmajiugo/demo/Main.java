package com.emmajiugo.demo;

import java.util.List;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import io.javalin.Javalin;

public class Main {

    public record User(int id, String name) {

    }

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());

        List<User> users = jdbi.withHandle(handle -> {
            handle.execute("""
                CREATE TABLE users (id INTEGER PRIMARY KEY, name VARCHAR)""");

            // Inline positional parameters
            handle.execute("INSERT INTO users (id, name) VALUES (?, ?)", 0, "Alice");

            // Positional parameters
            handle.createUpdate("INSERT INTO users (id, name) VALUES (?, ?)")
                    .bind(0, 1) // 0-based parameter indexes
                    .bind(1, "Bob")
                    .execute();

            UserDao userDao = handle.attach(UserDao.class);
            userDao.insert(2, "John");

            return handle.createQuery("""
                SELECT * FROM users ORDER BY name
                """)
                    .map(ConstructorMapper.of(User.class))
                    .list();
        });

        String imageKind = System.getProperty("org.graalvm.nativeimage.kind");
        Javalin app = Javalin.create(config -> {
            config.useVirtualThreads = imageKind == null;
        }).start(7070);

        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/users", ctx -> ctx.json(users));
        app.get("/hello/{name}", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });
    }
}
