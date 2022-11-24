package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.filters.Filters;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectFilter;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

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
        nitrite = Nitrite.builder()
                .filePath(file)
                .openOrCreate();
    }

    public List<Group> listGroups() {
        ObjectRepository<Group> repo = getGroupRepo();
        List<Group> groups = repo.find(FindOptions.sort("name", SortOrder.Ascending)).toList();
        repo.close();
        return groups;
    }

    public void addGroup(Group group) {
        ObjectRepository<Group> repo = getGroupRepo();
        repo.insert(group);
        repo.close();
    }

    public void removeGroup(Group group) {
        ObjectRepository<Group> repo = getGroupRepo();
        repo.remove(group);
        repo.close();
    }

    public void updateGroup(Group group) {
        ObjectRepository<Group> repo = getGroupRepo();
        repo.update(group);
        repo.close();
    }

    public boolean hasGroupNamed(String name) {
        ObjectRepository<Group> repo = getGroupRepo();
        String regex = String.format("(?i)^%s$", name);
        Cursor<Group> cursor = repo.find(ObjectFilters.regex("name", regex),
                FindOptions.limit(0, 1));
        repo.close();
        return cursor.totalCount() > 0;
    }

    private ObjectRepository<Group> getGroupRepo() {
        return nitrite.getRepository(Group.class);
    }

    private ObjectRepository<Student> getStudentRepo() {
        return nitrite.getRepository(Student.class);
    }
}
