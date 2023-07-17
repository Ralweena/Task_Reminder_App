package com.example.taskreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class categories extends AppCompatActivity implements View.OnClickListener {

    View personal, work, medicine, shopping, party, movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        personal = (View) findViewById(R.id.personalView);
        personal.setOnClickListener(this);
        work = (View) findViewById(R.id.workView);
        work.setOnClickListener(this);
        medicine = (View) findViewById(R.id.medicineView);
        medicine.setOnClickListener(this);
        shopping = (View) findViewById(R.id.shoppingView);
        shopping.setOnClickListener(this);
        party = (View) findViewById(R.id.partyView);
        party.setOnClickListener(this);
        movies = (View) findViewById(R.id.moviesView);
        movies.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(personal)) {
            Intent intent = new Intent(categories.this, PersonalReminder.class);
            startActivity(intent);
        }
        if(v.equals(work)) {
            Intent intent = new Intent(categories.this, WorkReminder.class);
            startActivity(intent);
        }
        if(v.equals(medicine)) {
            Intent intent = new Intent(categories.this, MedicineReminder.class);
            startActivity(intent);
        }
        if(v.equals(shopping)) {
            Intent intent = new Intent(categories.this, ShoppingReminder.class);
            startActivity(intent);
        }
        if(v.equals(party)) {
            Intent intent = new Intent(categories.this, partyReminder.class);
            startActivity(intent);
        }
        if(v.equals(movies)) {
            Intent intent = new Intent(categories.this, MoviesReminder.class);
            startActivity(intent);
        }
    }
}