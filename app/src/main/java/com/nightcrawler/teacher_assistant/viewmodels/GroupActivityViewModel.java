package com.nightcrawler.teacher_assistant.viewmodels;

import android.view.MenuItem;
import androidx.lifecycle.ViewModel;
import com.nightcrawler.teacher_assistant.adapters.GroupAdapter;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.Group;
import java.util.List;
import java.util.UUID;

public class GroupActivityViewModel extends ViewModel {
    private final List<Group> groups;
    private final GroupAdapter adapter;
    private final Database dbInstance;

    public GroupActivityViewModel() {
        dbInstance = Database.getInstance();
        groups = dbInstance.listGroups();
        adapter = new GroupAdapter(groups, this::editGroup, this::removeGroup);
    }

    public GroupAdapter getAdapter() {
        return adapter;
    }

    public void addGroup() {
        Group group = new Group(UUID.randomUUID().toString());

        dbInstance.addGroup(group);
        groups.add(group);
        adapter.notifyItemInserted(groups.size() - 1);
    }

    private boolean removeGroup(MenuItem item) {
        int id = item.getItemId();
        dbInstance.removeGroup(groups.get(id));
        groups.remove(id);
        adapter.notifyItemRemoved(id);
        return true;
    }

    private boolean editGroup(MenuItem item) {
        int id = item.getItemId();
        return true;
    }
}
