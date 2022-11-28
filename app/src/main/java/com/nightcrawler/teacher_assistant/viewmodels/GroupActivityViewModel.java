package com.nightcrawler.teacher_assistant.viewmodels;

import android.app.Application;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.adapters.GroupAdapter;
import com.nightcrawler.teacher_assistant.database.Database;
import com.nightcrawler.teacher_assistant.database.LocalDatabase;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.interfaces.GroupEditListener;
import com.nightcrawler.teacher_assistant.interfaces.GroupSelectionListener;

import java.util.Collections;
import java.util.List;

public class GroupActivityViewModel extends AndroidViewModel {
    private final List<Group> groups;
    private final GroupAdapter adapter;
    private final Database dbInstance;
    private final MutableLiveData<Integer> emptyLabelVisibility = new MutableLiveData<>();

    public GroupEditListener groupEditListener;
    public GroupSelectionListener groupSelectionListener;
    public final LiveData<Integer> emptyLabelState = emptyLabelVisibility;

    public GroupActivityViewModel(Application application) {
        super(application);
        dbInstance = LocalDatabase.getInstance();
        groups = dbInstance.listGroups();
        adapter = new GroupAdapter(groups, this::editItemSelected, this::removeGroup);
        adapter.itemClickListener = this::selectGroup;

        checkListState();
    }

    public GroupAdapter getAdapter() {
        return adapter;
    }

    private boolean removeGroup(MenuItem item) {
        int id = item.getItemId();
        dbInstance.removeGroup(groups.get(id));
        groups.remove(id);
        adapter.notifyItemRemoved(id);
        checkListState();
        return true;
    }

    private boolean editItemSelected(MenuItem item) {
        groupEditListener.onEdit(groups.get(item.getItemId()));
        return true;
    }

    private void checkListState() {
        if (adapter.getItemCount() == 0) {
            emptyLabelVisibility.setValue(View.VISIBLE);
        }
        else {
            emptyLabelVisibility.setValue(View.GONE);
        }
    }

    public boolean updateGroup(Group group, EditText editText, String previousName,
                               RecyclerView.LayoutManager layoutManager) {
        if (isGroupInvalid(group, editText, previousName)) return false;

        int position = groups.indexOf(group);
        groups.set(position, group);
        Collections.sort(groups, (a, b) -> a.name.compareTo(b.name));
        int newPosition = groups.indexOf(group);

        adapter.notifyItemChanged(position);
        adapter.notifyItemMoved(position, newPosition);
        dbInstance.updateGroup(group);
        // layoutManager.scrollToPosition(newPosition);

        return true;
    }

    public boolean addGroup(Group group, EditText editText,
                            RecyclerView.LayoutManager layoutManager) {
        if (isGroupInvalid(group, editText, null)) return false;

        dbInstance.addGroup(group);
        groups.add(group);
        Collections.sort(groups, (a, b) -> a.name.compareTo(b.name));

        int position = groups.indexOf(group);
        adapter.notifyItemInserted(position);
        //layoutManager.scrollToPosition(position);
        editText.setError(null);
        checkListState();
        return true;
    }

    private boolean isGroupInvalid(Group group, EditText editText, @Nullable String previousName) {
        group.name = group.name.trim();

        if (group.name.length() == 0) {
            editText.setError(editText.getContext().getString(R.string.required_filed));
            return true;
        }

        if (previousName != null
                && previousName.trim().equalsIgnoreCase(group.name)) {
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
        groupSelectionListener.invoke(groups.get(position));
    }
}
