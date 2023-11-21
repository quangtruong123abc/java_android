package com.example.project_mobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.adapters.HikeAdapter;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HikeActivity extends AppCompatActivity {
    private BottomNavigationView view_bn;
    private RecyclerView view_rc;
    private Database database;
    private List<HikeModel> hikes = new ArrayList<>();
    private HikeAdapter Adapter;
    private Button clear_data;
    private AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setInit();
        setNavigationView();
        database = new Database(HikeActivity.this);
        displayHike();
        Adapter = new HikeAdapter(HikeActivity.this,this, hikes);
        view_rc.setAdapter(Adapter);
        view_rc.setLayoutManager(new LinearLayoutManager(HikeActivity.this));
        alert = new AlertDialog.Builder(this);
        clear_data.setOnClickListener(v -> {
            alert.setTitle("Delete all data")
                    .setMessage("Are you sure delete all item")
                    .setCancelable(true)
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            database.deleteAll();
                            startActivity(new Intent(HikeActivity.this, HikeActivity.class));
                            finish();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).show();
        });
    }

    public void displayHike(){
        Cursor deg = database.readAllHikes();
        if(deg.getCount() == 0){
            Toast.makeText(HikeActivity.this, "No data here", Toast.LENGTH_SHORT).show();
        }else {
            while (deg.moveToNext()){
                hikes.add(new HikeModel(Integer.parseInt(deg.getString(0)),
                        deg.getString(1),
                        deg.getString(2),
                        deg.getString(3),
                        deg.getString(4),
                        deg.getString(5),
                        deg.getString(6),
                        deg.getString(7)));
            }
        }
    }

    private void setInit(){
        view_rc = findViewById(R.id.recyclerView);
        view_bn = findViewById(R.id.bottom_nav);
        view_bn.setSelectedItemId(R.id.home_page);
        clear_data = findViewById(R.id.btn_remove);
    }

    private void setNavigationView(){
        view_bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_page:
                        return true;
                    case R.id.add_item:
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search_item:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}