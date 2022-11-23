package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.objects.Id;

import java.io.Serializable;

public class Group implements Serializable {
    @Id
    public String name;

    public Group() {

    }

    public Group(String name) {
        this.name = name;
    }
}
