package com.essam.todo.ui.views;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.essam.todo.R;
import com.essam.todo.models.ToDoItem;
import com.essam.todo.ui.viewadapters.ToDoAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The MainActivity class
 */

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, View.OnClickListener {
    /**
     * adapter and firebase reference and list public to access from adapter class.
     */
    public ToDoAdapter ToDoAdapter, ToDoFinishedAdapter;
    public DatabaseReference databaseReference;
    public List<ToDoItem> listOfToDo, listOfFinishedToDo;
    /**
     * views that in main activity.
     */
    @BindView(R.id.todo_text)
    EditText ToDoText;
    @BindView(R.id.add_todo)
    ImageView addToDo;
    @BindView(R.id.recycler_todo)
    RecyclerView recyclerToDo;
    @BindView(R.id.recycler_finished)
    RecyclerView recyclerFinishedToDo;
    /**
     * searchView and searchMenuItem for search query.
     */
    SearchView searchView;
    MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

/**
 * initializing.
 */
        databaseReference = FirebaseDatabase.getInstance().getReference();
        addToDo.setOnClickListener(this);
        listOfToDo = new ArrayList<>();
        listOfFinishedToDo = new ArrayList<>();

        setupRecycler();


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getAllToDo(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //if changed show all


                getAllToDo(dataSnapshot);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    /**
     * setupRecycler.
     */
    private void setupRecycler() {

        recyclerToDo.setHasFixedSize(true);
        recyclerFinishedToDo.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerToDo.setLayoutManager(layoutManager);

        final LinearLayoutManager layoutManagerFinishedToDo = new LinearLayoutManager(this);
        layoutManagerFinishedToDo.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerFinishedToDo.setLayoutManager(layoutManagerFinishedToDo);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        ToDoAdapter.getFilter().filter(newText);
        recyclerToDo.scrollToPosition(0);


        ToDoFinishedAdapter.getFilter().filter(newText);
        recyclerFinishedToDo.scrollToPosition(0);

        return true;
    }

    @Override
    public void onClick(View view) {
        if (ToDoText.getText() == null || ToDoText.getText().toString().equals("") || ToDoText.getText().toString().equals(" ")) {
            Toast.makeText(MainActivity.this, "Entry not saved, missing Todo", Toast.LENGTH_SHORT).show();
        } else {
            ToDoItem item = new ToDoItem();
            item.setTodo(ToDoText.getText().toString());
            item.setFinished(false);
            databaseReference.push().setValue(item);
            ToDoText.setText("");
        }
    }

    /**
     * get all data from database.
     */
    private void getAllToDo(DataSnapshot dataSnapshot) {

        ToDoItem item = dataSnapshot.getValue(ToDoItem.class);
        item.setKey(dataSnapshot.getKey());
        if (!item.getFinished()) {
            listOfToDo.add(item);

        } else {
            listOfFinishedToDo.add(item);

        }
        ToDoAdapter = new ToDoAdapter(MainActivity.this, listOfToDo);
        recyclerToDo.setAdapter(ToDoAdapter);
        ToDoFinishedAdapter = new ToDoAdapter(MainActivity.this, listOfFinishedToDo);
        recyclerFinishedToDo.setAdapter(ToDoFinishedAdapter);

    }


}
