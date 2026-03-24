package com.example.studentsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public interface OnDeleteListener { void onDelete(Student student); }

    private List<Student> students = new ArrayList<>();
    private final OnDeleteListener listener;

    public StudentAdapter(OnDeleteListener listener) { this.listener = listener; }

    public void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student s = students.get(position);
        holder.textName.setText(s.getName());
        holder.textGrade.setText("Оценка: " + s.getGrade());
        holder.buttonDelete.setOnClickListener(v -> listener.onDelete(s));
    }

    @Override
    public int getItemCount() { return students.size(); }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textGrade;
        Button buttonDelete;
        ViewHolder(View view) {
            super(view);
            textName = view.findViewById(R.id.textName);
            textGrade = view.findViewById(R.id.textGrade);
            buttonDelete = view.findViewById(R.id.buttonDelete);
        }
    }
}
