package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Indices({
        @Index(value = "name", type = IndexType.Unique),
})
public class Group implements Serializable {
    @Id
    private NitriteId id;

    @NotNull
    public String name;

    private Group() {}

    public Group(@NotNull String name) {
        this.name = name;
    }

    public NitriteId getId() {
        return id;
    }
}
