package com.nightcrawler.teacher_assistant.database;

import org.dizitart.no2.FindOptions;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteId;
import org.dizitart.no2.SortOrder;
import org.dizitart.no2.objects.Cursor;
import org.dizitart.no2.objects.ObjectRepository;
import org.dizitart.no2.objects.filters.ObjectFilters;

import java.io.File;
import java.util.List;

public class LocalDatabase implements Database {
    private final Nitrite nitrite;
    private static volatile LocalDatabase instance = null;
    public static void initialize(File file) {
        if (instance == null) {
            instance = new LocalDatabase(file);
        }
    }

    public static LocalDatabase getInstance() {
        return instance;
    }

    private LocalDatabase(File file) {
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
        ObjectRepository<Group> groupRepo = getGroupRepo();
        ObjectRepository<Student> studentRepo = getStudentRepo();
        studentRepo.remove(ObjectFilters.eq("groupId", group.getId().getIdValue()));
        groupRepo.remove(group);
        studentRepo.close();
        groupRepo.close();
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

    public List<Student> listStudents(Object id) {
        ObjectRepository<Student> students = getStudentRepo();
        NitriteId nitriteId = (NitriteId) id;
        Cursor<Student> cursor = students.find(
                ObjectFilters.eq("groupId", nitriteId.getIdValue()),
                FindOptions.sort("lastName", SortOrder.Ascending)
        );
        students.close();
        return cursor.toList();
    }

    public void updateStudent(Student student) {
        ObjectRepository<Student> students = getStudentRepo();
        students.update(student);
        students.close();
    }

    public void addStudent(Student student) {
        ObjectRepository<Student> students = getStudentRepo();
        students.insert(student);
        students.close();
    }

    public void removeStudent(Student student) {
        ObjectRepository<Student> students = getStudentRepo();
        students.remove(student);
        students.close();
    }

    private ObjectRepository<Group> getGroupRepo() {
        return nitrite.getRepository(Group.class);
    }

    private ObjectRepository<Student> getStudentRepo() {
        return nitrite.getRepository(Student.class);
    }
}
