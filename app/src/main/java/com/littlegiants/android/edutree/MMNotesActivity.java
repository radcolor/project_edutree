package com.littlegiants.android.edutree;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MMNotesActivity extends AppCompatActivity implements View.OnClickListener{

    EditText tittle,note;
    DatabaseEduNotes eduNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mmnotes);

        eduNotes = new DatabaseEduNotes(this);

        tittle = findViewById(R.id.note_title);
        note = findViewById(R.id.the_note);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf", true);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        actionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                String tit = tittle.getText().toString();
                String notes = note.getText().toString();

                if(tit.isEmpty() || notes.isEmpty()){

                }else{
                    eduNotes.insertData(tit,notes,null,null);
                    finish();
                }
                return true;

            case R.id.delete_notes:
                finish();
                break;
            case R.id.share_notes:

                String title = tittle.getText().toString();
                String not = note.getText().toString();

                if(title.isEmpty() || not.isEmpty()){

                }else{
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"name@email.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Check out this important note");
                    i.putExtra(Intent.EXTRA_TEXT, not);
                    i.putExtra(Intent.EXTRA_TITLE, new String[]{title});
                    try {
                        startActivity(Intent.createChooser(i, "Share..."));
                    } catch (android.content.ActivityNotFoundException ex) {

                    }
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        String tit = tittle.getText().toString();
        String notes = note.getText().toString();

        if(tit.isEmpty() || notes.isEmpty()){

        }else{
            eduNotes.insertData(tit,notes,null,null);
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        int btnId = v.getId();
        switch (btnId) {

            case R.id.share_notes:

                break;
            case R.id.delete_notes:

                break;

        }
    }

    @Override
    public void onBackPressed() {

        String tit = tittle.getText().toString();
        String notes = note.getText().toString();

        if(tit.isEmpty() || notes.isEmpty()){

        }else{
            eduNotes.insertData(tit,notes,null,null);
        }

        super.onBackPressed();
    }
}
