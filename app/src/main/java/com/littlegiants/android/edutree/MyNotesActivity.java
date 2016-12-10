package com.littlegiants.android.edutree;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;


import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MyNotesActivity extends AppCompatActivity{

    RecyclerAdapter recyclerAdapter;
    List<NotesDataModel> datamodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotes);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        final DatabaseEduNotes databaseEduNotes = new DatabaseEduNotes(this);

        datamodel =  databaseEduNotes.getdata();
        recyclerAdapter = new RecyclerAdapter(datamodel);
        RecyclerView recyclerView = findViewById(R.id.mynotes_recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(recyclerAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),manager.getOrientation());
        recyclerView.addItemDecoration(decoration);

        FloatingActionButton button = findViewById(R.id.FAB1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MyNotesActivity.this, MMNotesActivity.class));
            }
        });

        FloatingActionButton actionButton = findViewById(R.id.FAB2);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = databaseEduNotes.getAllData();
                if(res.getCount() == 0){
                    showMessage("Error", "Nothing Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    buffer.append("Data_id : "+ res.getString(0)+"\n");
                    buffer.append("Tittle : "+ res.getString(1)+"\n");
                    buffer.append("Notes : "+ res.getString(2)+"\n");
                    buffer.append("Date : "+ res.getString(3)+"\n");
                    buffer.append("Time : "+ res.getString(4)+"\n\n");
                }
                showMessage("Data", buffer.toString());
            }
        });

    }

    public void showMessage(String Tittle, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Tittle);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recyclerAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}