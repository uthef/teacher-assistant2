package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;

import java.io.Serializable;
import java.util.Date;

public class Lesson implements Serializable {
    @Id
    public NitriteId id;

    public Date startTime, endTime;

    private Lesson() {}

    public Lesson(Date beginning, Date ending) {
        this.startTime = beginning;
        this.endTime = ending;
    }

}
