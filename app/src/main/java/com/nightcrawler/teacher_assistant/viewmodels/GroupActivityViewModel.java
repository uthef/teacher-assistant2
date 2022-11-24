package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.adapters.GroupAdapter;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.interfaces.GroupEditListener;

import java.util.Collections;
import java.util.List;

public class GroupActivityViewModel extends AndroidViewModel {
    private final List<Group> groups;
    private final GroupAdapter adapter;
    private final Database dbInstance;

    public GroupEditListener groupEditListener;

    public GroupActivityViewModel(Application application) {
        super(application);
        dbInstance = Database.getInstance();
        groups = dbInstance.listGroups();
        adapter = new GroupAdapter(groups, this::editItemSelected, this::removeGroup);
        adapter.itemClickListener = this::selectGroup;
    }

    public GroupAdapter getAdapter() {
        return adapter;
    }

    private boolean removeGroup(MenuItem item) {
        int id = item.getItemId();
        dbInstance.removeGroup(groups.get(id));
        groups.remove(id);
        adapter.notifyItemRemoved(id);
        return true;
    }

    private boolean editItemSelected(MenuItem item) {
        groupEditListener.onEdit(groups.get(item.getItemId()));
        return true;
    }

    public boolean updateGroup(Group group, EditText editText, String previousName) {
        if (isGroupInvalid(group, editText, previousName)) return false;

        int position = groups.indexOf(group);
        groups.set(position, group);
        Collections.sort(groups, (a, b) -> a.name.compareTo(b.name));
        adapter.notifyItemChanged(position);
        adapter.notifyItemMoved(position, groups.indexOf(group));
        dbInstance.updateGroup(group);

        return true;
    }

    public boolean addGroup(Group group, EditText editText) {
        if (isGroupInvalid(group, editText, null)) return false;

        dbInstance.addGroup(group);
        groups.add(group);
        Collections.sort(groups, (a, b) -> a.name.compareTo(b.name));
        adapter.notifyItemInserted(groups.indexOf(group));

        editText.setError(null);
        return true;
    }

    private boolean isGroupInvalid(Group group, EditText editText, @Nullable String previousName) {
        group.name = group.name.trim();

        if (group.name.length() == 0) {
            editText.setError(editText.getContext().getString(R.string.required_filed));
            return true;
        }

        if (previousName != null
                && previousName.trim().toLowerCase().equals(group.name.toLowerCase())) {
            return false;
        }

        if (dbInstance.hasGroupNamed(group.name))
        {
            editText.setError(editText.getContext().getString(R.string.group_duplicate));
            return true;
        }

        return false;
    }

    private void selectGroup(View view, int position) {

    }
}
