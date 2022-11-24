package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.objects.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Group implements Serializable {
    @Id
    @NotNull
    public String name;
    private Group() {}

    public Group(@NotNull String name) {
        this.name = name;
    }
}
