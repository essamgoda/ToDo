package com.essam.todo.ui.viewadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.essam.todo.R;
import com.essam.todo.models.ToDoItem;
import com.essam.todo.models.ToDoItemFilter;
import com.essam.todo.ui.views.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by essam on 07/02/17.
 */

/**
 * The ToDoAdapter class is a todo Adapterter.
 */
public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> implements Filterable {
    public List<ToDoItem> ToDos;
    protected Context context;
    ToDoItemFilter toDoFilter;

    public ToDoAdapter(Context context, List<ToDoItem> allToDo) {
        this.ToDos = allToDo;
        this.context = context;
    }

    @Override
    public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoViewHolder holder, final int position) {
        final ToDoItem todo = ToDos.get(position);

        int count = position + 1;
        holder.textCounter.setText(count + ". ");

        holder.textToDos.setText(todo.getTodo());

        if (todo.getFinished())
            holder.finished.setChecked(true);
        else
            holder.finished.setChecked(false);


        holder.finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!todo.getFinished()) {
                    todo.setFinished(true);

                } else {
                    todo.setFinished(false);

                }

                ((MainActivity) context).databaseReference.child(todo.getKey()).setValue(todo);


                if (!todo.getFinished())
                    Toast.makeText(context, todo.getTodo() + " is Finished", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, todo.getTodo() + " is unFinished", Toast.LENGTH_SHORT).show();
                ToDos.remove(position);
            }
        });

    }

    @Override
    public Filter getFilter() {

        if (toDoFilter == null)
            toDoFilter = new ToDoItemFilter(this, ToDos);
        return toDoFilter;
    }

    @Override
    public int getItemCount() {
        return ToDos.size();
    }

    public static class ToDoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.count)
        TextView textCounter;
        @BindView(R.id.text)
        TextView textToDos;
        @BindView(R.id.finished)
        CheckBox finished;


        public ToDoViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
