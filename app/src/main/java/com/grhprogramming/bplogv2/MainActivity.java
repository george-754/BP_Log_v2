package com.grhprogramming.bplogv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

        // Set long press to invoke remove bp
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            BP dataModel = dataModels.get(i);

            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this);
            builder.setTitle("Delte BP!");
            builder.setMessage("Are you sure you want to delete this blood pressure?");

            builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                db.deleteBp(dataModel.getId());
                refreshHome();
            });

            builder.setNegativeButton("No", null);
            builder.show();

            return false;
        });

    }

    @Override
    public void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void refreshHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }
}

// TODO: Change styles, Change list row style, Change style of add screen
// TODO: Add screen.  Want to group elements together by vitals, time, date.  Maybe make time and date collapsable.