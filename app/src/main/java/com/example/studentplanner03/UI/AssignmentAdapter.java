package com.example.studentplanner03.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentplanner03.R;
import com.example.studentplanner03.entities.Assignment;

import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> mAssignments;
    private final Context context;
    private final LayoutInflater mInflater;


    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assignmentItemView;
        private final TextView assignmentItemView2;

        private AssignmentViewHolder(View itemView) {
            super(itemView);
            assignmentItemView = itemView.findViewById(R.id.textView2);
            assignmentItemView2 = itemView.findViewById(R.id.textView3);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assignment current = mAssignments.get(position);
                    Intent intent = new Intent(context, AssignmentDetails.class);
                    intent.putExtra("assignmID", current.getAssignmentID());
                    intent.putExtra("assignmName", current.getAssignmentName());
                    intent.putExtra("assignmDD", current.getAssignmentDueDate());
                    intent.putExtra("assignmDesc", current.getAssignmentDescription());
                    intent.putExtra("crseID", current.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    public AssignmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssignmentAdapter.AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assignment_list_item,parent,false);
        return new AssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentAdapter.AssignmentViewHolder holder, int position) {
        if (mAssignments != null) {
            Assignment current = mAssignments.get(position);
            String assignmName = current.getAssignmentName();
            int crseID = current.getCourseID();
            holder.assignmentItemView.setText(assignmName);
            holder.assignmentItemView2.setText(Integer.toString(crseID));
        }
        else {
            holder.assignmentItemView.setText("No assignment name");
            holder.assignmentItemView2.setText("No course id");
        }
    }

    public void setAssignments(List<Assignment> assignments) {
        mAssignments = assignments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mAssignments != null) return mAssignments.size();
        else return 0;
    }

}
