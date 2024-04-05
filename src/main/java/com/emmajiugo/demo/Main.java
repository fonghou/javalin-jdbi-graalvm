package com.emmajiugo.demo;

import java.util.List;
import io.javalin.Javalin;
import org.jdbi.v3.core.Jdbi;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");

        List<User> users = jdbi.withHandle(handle -> {
            handle.execute("CREATE TABLE \"user\" (id INTEGER PRIMARY KEY, \"name\" VARCHAR)");

            // Inline positional parameters
            handle.execute("INSERT INTO \"user\" (id, \"name\") VALUES (?, ?)", 0, "Alice");

            // Positional parameters
            handle.createUpdate("INSERT INTO \"user\" (id, \"name\") VALUES (?, ?)")
                    .bind(0, 1) // 0-based parameter indexes
                    .bind(1, "Bob")
                    .execute();

            return handle.createQuery("SELECT * FROM \"user\" ORDER BY \"name\"")
                    .mapToBean(User.class)
                    .list();
        });

        Javalin app = Javalin.create(/*config*/)
                .start(7070);

        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/users", ctx -> ctx.json(users));
        app.get("/hello/{name}", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });
    }
}
