package com.nightcrawler.teacher_assistant.adapters;

import android.content.Context;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Group;
import com.nightcrawler.teacher_assistant.interfaces.ItemClickListener;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private final List<Group> groups;
    private final MenuItem.OnMenuItemClickListener editItemListener, removalItemListener;
    public ItemClickListener itemClickListener;

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

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.groupNameTextView.setText(groups.get(position).name);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener
    {
        private final GroupAdapter adapter;
        private final MenuItem.OnMenuItemClickListener editItemListener, removalItemListener;
        public final TextView groupNameTextView;

        public ViewHolder(View view, GroupAdapter adapter)
        {
            super(view);

            this.adapter = adapter;
            this.editItemListener = adapter.editItemListener;
            this.removalItemListener = adapter.removalItemListener;

            groupNameTextView = view.findViewById(R.id.group_name);
            view.setOnCreateContextMenuListener(this);
            view.setOnClickListener((v) -> adapter.itemClickListener.onClick(v, getAdapterPosition()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            Context context = v.getContext();

            menu.setHeaderTitle(adapter.groups.get(getAdapterPosition()).name);

            menu.add(0, getAdapterPosition(), 0, context.getString(R.string.remove)).
                    setOnMenuItemClickListener(removalItemListener);
            menu.add(0, getAdapterPosition(), 0, context.getString(R.string.edit))
                    .setOnMenuItemClickListener(editItemListener);
        }
    }
}
