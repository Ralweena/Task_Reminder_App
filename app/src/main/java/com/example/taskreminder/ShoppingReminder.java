package com.example.taskreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ShoppingReminder extends AppCompatActivity {

    FloatingActionButton createReminder;
    RecyclerView recyclerview;
    //Array list to add reminders and display in recyclerview
    ArrayList<Model> dataholder = new ArrayList<Model>();
    myAdapter adapter;
    Bundle category = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_reminder);

        recyclerview = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //Floating action button to change activity
        createReminder = (FloatingActionButton) findViewById(R.id.addView);
        createReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReminderActivity.class);
                category.putString("category","Shopping");
                intent.putExtras(category);
                //Starts the new activity to add Reminders
                startActivity(intent);
            }
        });

        //Cursor To Load data From the database
        Cursor cursor = new dbManager(getApplicationContext()).readShoppingReminders();
        while (cursor.moveToNext()) {
            Model model = new Model(cursor.getString(1), cursor.getString(2), cursor.getString(3));
            dataholder.add(model);
        }

        //Binds the adapter with recyclerview
        adapter = new myAdapter(dataholder);
        recyclerview.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        //Makes the user to exit form the app
        finish();
        super.onBackPressed();

    }
}