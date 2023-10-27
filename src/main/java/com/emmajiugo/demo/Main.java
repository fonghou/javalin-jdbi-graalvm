package com.emmajiugo.demo;

import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        Test t = new Test();
        t.setSomeValue("Hello Javalin native");

        Javalin app = Javalin.create(/*config*/)
                .start(7070);

        app.get("/", ctx -> ctx.result("Hello World"));
        app.get("/test", ctx -> ctx.json(t));
        app.get("/hello/{name}", ctx -> {
            ctx.result("Hello: " + ctx.pathParam("name"));
        });
    }
}
