package com.nightcrawler.teacher_assistant.adapters;

import android.content.Context;
import android.util.Log;
import android.view.*;

import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private final List<Group> groups;
    private final MenuItem.OnMenuItemClickListener editItemListener, removalItemListener;

    public GroupAdapter(List<Group> groups, MenuItem.OnMenuItemClickListener editItemListener,
                        MenuItem.OnMenuItemClickListener removalItemListener) {
        this.groups = groups;
        this.editItemListener = editItemListener;
        this.removalItemListener = removalItemListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_layout, parent, false);

        return new ViewHolder(view, editItemListener, removalItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.groupNameTextView.setText(groups.get(position).name);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
    {
        private final MenuItem.OnMenuItemClickListener editItemListener, removalItemListener;
        public final TextView groupNameTextView;
        public ViewHolder(View view, MenuItem.OnMenuItemClickListener editItemListener,
                          MenuItem.OnMenuItemClickListener removalItemListener)
        {
            super(view);
            this.editItemListener = editItemListener;
            this.removalItemListener = removalItemListener;
            groupNameTextView = view.findViewById(R.id.group_name);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Context context = v.getContext();

            menu.add(0, getAdapterPosition(), 0, context.getString(R.string.remove)).
                    setOnMenuItemClickListener(removalItemListener);
            menu.add(0, getAdapterPosition(), 0, context.getString(R.string.edit))
                    .setOnMenuItemClickListener(editItemListener);
        }

    }
}
