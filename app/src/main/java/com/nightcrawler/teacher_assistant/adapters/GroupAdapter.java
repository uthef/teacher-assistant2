package com.nightcrawler.teacher_assistant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
    private List<Group> groups;

    public GroupAdapter(List<Group> groups) {
        this.groups = groups;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_list_layout, parent, false);

        return new ViewHolder(view);
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
    {
        public final TextView groupNameTextView;
        public ViewHolder(View view)
        {
            super(view);
            groupNameTextView = view.findViewById(R.id.group_name);
        }
    }
}
