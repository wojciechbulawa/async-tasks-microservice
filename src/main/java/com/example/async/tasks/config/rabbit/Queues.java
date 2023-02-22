package com.example.async.tasks.config.rabbit;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Queues {

    public static final String MSG = "msg-queue";
    public static final String MSG_ADMIN = "msg-admin-queue";
    public static final String MSG_ROUTING_KEY = "messages.hello";
    public static final String TASKS = "tasks-queue";
    public static final String TASKS_ROUTING_KEY = "tasks";

}
