package com.essam.todo.models;

import android.util.Log;
import android.widget.Filter;

import com.essam.todo.ui.viewadapters.ToDoAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by essam on 07/02/17.
 */

/**
 * The ToDoItemFilter class is a todo filter.
 */
public class ToDoItemFilter extends Filter {

    private final ToDoAdapter adapter;

    private final List<ToDoItem> originalList;

    private final List<ToDoItem> filteredList;

    public ToDoItemFilter(ToDoAdapter adapter, List<ToDoItem> originalList) {
        super();
        this.adapter = adapter;
        this.originalList = new LinkedList<>(originalList);
        this.filteredList = new ArrayList<>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        filteredList.clear();
        final FilterResults results = new FilterResults();

        if (constraint.length() == 0) {
            filteredList.addAll(originalList);
        } else {
            final String filterPattern = constraint.toString().toLowerCase().trim();

            for (final ToDoItem item : originalList) {
                if (item.getTodo().contains(filterPattern)) {
                    filteredList.add(item);
                    Log.e("s", filterPattern);
                }
            }
        }
        results.values = filteredList;
        results.count = filteredList.size();
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.ToDos.clear();
        adapter.ToDos.addAll((List<ToDoItem>) results.values);
        adapter.notifyDataSetChanged();
    }
}
