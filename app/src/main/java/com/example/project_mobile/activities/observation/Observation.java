package com.example.project_mobile.activities.observation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.activities.DetailActivity;
import com.example.project_mobile.adapters.ObservationAdapter;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.example.project_mobile.models.HikeObservation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Observation extends AppCompatActivity {
    private ImageView back_image_ic;
    private BottomNavigationView view_bn;
    private RecyclerView view_rc;
    private Database myDB;
    private List<HikeObservation> obs_list = new ArrayList<>();
    private ObservationAdapter obs_adapter;
    private String hike_id, hike_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation);
        setInit();
        setNavigationView();
        hike_id = (String) getIntent().getSerializableExtra("hike_id");
        hike_date = (String) getIntent().getSerializableExtra("hike_date");
        myDB = new Database(Observation.this);
        displayObservation(hike_id);
        String names = (String) getIntent().getSerializableExtra("hike_name");
        String locations = (String) getIntent().getSerializableExtra("hike_location");
        String date = (String) getIntent().getSerializableExtra("hike_date");
        String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
        String lengths = (String) getIntent().getSerializableExtra("length_hike");
        String levels = (String) getIntent().getSerializableExtra("level_hike");
        String desc = (String) getIntent().getSerializableExtra("des_hike");
        HikeModel hike = new HikeModel(Integer.parseInt(hike_id), names, locations, hike_date, parking_availables, lengths, levels, desc);
        obs_adapter = new ObservationAdapter(Observation.this,this, obs_list, hike);
        view_rc.setAdapter(obs_adapter);
        view_rc.setLayoutManager(new LinearLayoutManager(Observation.this));
        setListeners();
    }

    private void setInit(){
        back_image_ic = findViewById(R.id.back_ic_obs);
        view_rc = findViewById(R.id.recyclerView);
        view_bn = findViewById(R.id.bottom_nav);
        view_bn.setSelectedItemId(R.id.home_page);
    }

    private void setListeners(){
        String names = (String) getIntent().getSerializableExtra("hike_name");
        String locations = (String) getIntent().getSerializableExtra("hike_location");
        String dates = (String) getIntent().getSerializableExtra("hike_date");
        String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
        String lengths = (String) getIntent().getSerializableExtra("length_hike");
        String levels = (String) getIntent().getSerializableExtra("level_hike");
        String desc = (String) getIntent().getSerializableExtra("des_hike");
        back_image_ic.setOnClickListener(v -> {
            Intent pur = new Intent(Observation.this, DetailActivity.class);
            pur.putExtra("hike_id", hike_id);
            pur.putExtra("hike_name", names);
            pur.putExtra("hike_location", locations);
            pur.putExtra("hike_date", dates);
            pur.putExtra("parking_available", parking_availables);
            pur.putExtra("length_hike", lengths);
            pur.putExtra("level_hike", levels);
            pur.putExtra("des_hike", desc);
            startActivity(pur);
        });
    }

    public void displayObservation(String hikeID){
        Cursor point = myDB.readAllObservation(hikeID);
        if(point.getCount() == 0){
            Toast.makeText(Observation.this, "No data here", Toast.LENGTH_SHORT).show();
        }else {
            while (point.moveToNext()){
                obs_list.add(new HikeObservation(Integer.parseInt(point.getString(0)),
                        Integer.parseInt(point.getString(1)),
                        point.getString(2),
                        point.getString(3),
                        point.getString(4)));
            }
        }
    }

    private void setNavigationView(){
        view_bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String names = (String) getIntent().getSerializableExtra("hike_name");
                String locations = (String) getIntent().getSerializableExtra("hike_location");
                String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
                String lengths = (String) getIntent().getSerializableExtra("length_hike");
                String levels = (String) getIntent().getSerializableExtra("level_hike");
                String desc = (String) getIntent().getSerializableExtra("des_hike");
                switch (item.getItemId()){
                    case R.id.home_page:
                        return true;
                    case R.id.add_item:
                        Intent pur1 = new Intent(getApplicationContext(), AddObservation.class);
                        pur1.putExtra("hike_id", hike_id);
                        pur1.putExtra("hike_date", hike_date);
                        pur1.putExtra("hike_name", names);
                        pur1.putExtra("hike_location", locations);
                        pur1.putExtra("parking_available", parking_availables);
                        pur1.putExtra("length_hike", lengths);
                        pur1.putExtra("level_hike", levels);
                        pur1.putExtra("des_hike", desc);
                        startActivity(pur1);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search_item:
                        Intent pur2 = new Intent(getApplicationContext(), SearchObservation.class);
                        pur2.putExtra("hike_id", hike_id);
                        pur2.putExtra("hike_date", hike_date);
                        pur2.putExtra("hike_name", names);
                        pur2.putExtra("hike_location", locations);
                        pur2.putExtra("parking_available", parking_availables);
                        pur2.putExtra("length_hike", lengths);
                        pur2.putExtra("level_hike", levels);
                        pur2.putExtra("des_hike", desc);
                        startActivity(pur2);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}