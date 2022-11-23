package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import java.io.File;
import java.util.List;

public class Database {
    private final Nitrite nitrite;
    private static volatile Database instance = null;
    public static void initialize(File file) {
        if (instance == null) {
            instance = new Database(file);
        }
    }

    public static Database getInstance() {
        return instance;
    }

    private Database(File file) {
        nitrite = Nitrite.builder().filePath(file).openOrCreate();
    }

    public List<Group> listGroups() {
        ObjectRepository<Group> groupRepo = nitrite.getRepository(Group.class);
        return groupRepo.find().toList();
    }
}
