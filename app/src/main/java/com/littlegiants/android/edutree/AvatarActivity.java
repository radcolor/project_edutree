package com.littlegiants.android.edutree;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AvatarActivity extends AppCompatActivity implements View.OnClickListener{

    DatabaseHelper myDb;
    EditText mName,mClass,mSchool;
    private DatabaseReference myRef;
    private FirebaseDatabase mDataBase;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);

        mDataBase = FirebaseDatabase.getInstance();
        myRef = mDataBase.getReference();

        myDb = new DatabaseHelper(this);

        button = findViewById(R.id.btn_fbsq);
        button.setOnClickListener(this);

        mName = findViewById(R.id.fbsq_name);
        mClass = findViewById(R.id.fbsq_class);
        mSchool = findViewById(R.id.fbsq_school);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "truenolt.otf",true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.done_pro){

            Cursor res = myDb.getAllData();
            if(res.getCount() == 0){
                showMessage("Error", "Nothing Found");
                return true;
            }

            StringBuffer buffer = new StringBuffer();
            while (res.moveToNext()){

                buffer.append("Data_id : "+ res.getString(0)+"\n");
                buffer.append("Name : "+ res.getString(1)+"\n");
                buffer.append("Email : "+ res.getString(2)+"\n");
                buffer.append("Class : "+ res.getString(3)+"\n");
                buffer.append("School-Name : "+ res.getString(4)+"\n\n");

            }
            showMessage("Data", buffer.toString());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int btnId = view.getId();
        switch (btnId) {

            case R.id.btn_fbsq:
                String name = mName.getText().toString();
                String mclass = mClass.getText().toString();
                String school = mSchool.getText().toString();

                if (name.isEmpty() || mclass.isEmpty() || school.isEmpty()) {
                    Toast.makeText(AvatarActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {

                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                    String user_name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString();
                    String user_email = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();

                    myDb.updateData(user_id, user_name, user_email, mclass, school);
                    UserInformation information = new UserInformation(user_id, user_name, user_email, mclass, school);
                    myRef.child("users").child(user_id).setValue(information);
                    Toast.makeText(AvatarActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

                    break;

                }
        }
    }

    public void showMessage(String Tittle, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(Tittle);
        builder.setMessage(Message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.done_avatar, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AvatarActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
