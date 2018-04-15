package com.example.admin.myapplication;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbStudent db;
    ListView listViewStudent;
    ArrayList<Student> studentArrayList;
    StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewStudent = findViewById(R.id.ListStudent);
        studentArrayList = new ArrayList<Student>();
        studentAdapter = new StudentAdapter(this,R.layout.row_student,studentArrayList);
        listViewStudent.setAdapter(studentAdapter);

        registerForContextMenu(listViewStudent);

        db = new DbStudent(this,"Student.SQLite",null,1);
        db.QueryData("CREATE TABLE IF NOT EXISTS STUDENT(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR(200),CODE VARCHAR(20))");
//        db.QueryData("INSERT INTO STUDENT VALUES(NULL,'RINA YSHIHARA','SHS-123')");
//        db.QueryData("INSERT INTO STUDENT VALUES(NULL,'JUNA AIZAWA','DRG-599')");

        getdataStudent();

    }

    private void getdataStudent() {
        Cursor dataSt = db.getData("SELECT * FROM STUDENT");
        studentArrayList.clear();
        while (dataSt.moveToNext())
        {
            studentArrayList.add(new Student(dataSt.getInt(0),dataSt.getString(1),dataSt.getString(2)));
        }
        studentAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menuAdd)
        {
            dialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_edit_delete,menu);
    }

    private void dialogAdd() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.cancel();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextName = dialog.findViewById(R.id.edtAddName);
                EditText editTextCode = dialog.findViewById(R.id.edtAddCode);
                if(editTextName.getText().toString().equals("")||editTextCode.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    db.QueryData("INSERT INTO STUDENT VALUES(NULL,'"+editTextName.getText().toString()+"','"+editTextCode.getText().toString()+"')");
                    Toast.makeText(MainActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
                    getdataStudent();
                    dialog.cancel();
                }
            }
        });

        dialog.show();

    }
}
