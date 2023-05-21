package com.grhprogramming.bplogv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.grhprogramming.bplogv2.data.BP;
import com.grhprogramming.bplogv2.data.CustomAdapter;
import com.grhprogramming.bplogv2.data.DbHandler;
import com.grhprogramming.bplogv2.data.NewBpActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<BP> dataModels;
    ListView listView;
    private CustomAdapter adapter;

    FloatingActionButton btnAddBp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lstBps);
        btnAddBp = findViewById(R.id.fab);
        dataModels = new ArrayList<>();
        DbHandler db = new DbHandler(MainActivity.this);

        dataModels = db.getAll();

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);

        btnAddBp.setOnClickListener(view -> {
            Intent intentAdd = new Intent(this, NewBpActivity.class);
            startActivity(intentAdd);
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }
}