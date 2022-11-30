package com.nightcrawler.teacher_assistant.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nightcrawler.teacher_assistant.R;
import com.nightcrawler.teacher_assistant.database.Student;
import com.nightcrawler.teacher_assistant.listeners.StudentEditListener;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private final List<Student> students;
    public StudentEditListener editListener;
    public MenuItem.OnMenuItemClickListener itemRemovalListener;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_layout, parent, false);

        return new ViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context ctx = holder.itemView.getContext();
        Student student = students.get(position);

        holder.nameText.setText(student.getFullName());
        holder.infoText.setText(
                student.getInfo(ctx.getString(R.string.subgroup_label),
                        ctx.getString(R.string.variant_label),
                        ctx.getString(R.string.kp_label)
                )
        );
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnCreateContextMenuListener {
        public final TextView nameText, infoText;
        private final StudentAdapter adapter;

        public ViewHolder(@NonNull View itemView, StudentAdapter adapter) {
            super(itemView);

            this.adapter = adapter;
            nameText = itemView.findViewById(R.id.student_name);
            infoText = itemView.findViewById(R.id.student_label);

            itemView.setOnClickListener((view) -> adapter.editListener.onEdit(
                    adapter.students.get(getAdapterPosition()),
                    getAdapterPosition()
            ));
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu,
                                        View view,
                                        ContextMenu.ContextMenuInfo contextMenuInfo) {
            Context context = view.getContext();


            menu.add(0, getAdapterPosition(), 0, context.getString(R.string.remove)).
                    setOnMenuItemClickListener(adapter.itemRemovalListener);
        }
    }
}
