package com.example.project_mobile.activities.observation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.adapters.ObservationAdapter;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.example.project_mobile.models.HikeObservation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchObservation extends AppCompatActivity {
    private ImageView back_image_ic;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView view_rc;
    private Database myDB;
    private List<HikeObservation> obs = new ArrayList<>();
    private ObservationAdapter obs_adapter;
    private String hike_id, hike_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_observation);
        setInit();
        setNavigationView();
        hike_id = (String) getIntent().getSerializableExtra("hike_id");
        hike_date = (String) getIntent().getSerializableExtra("hike_date");
        myDB = new Database(SearchObservation.this);
        displayObservation(hike_id);
        String names = (String) getIntent().getSerializableExtra("hike_name");
        String locations = (String) getIntent().getSerializableExtra("hike_location");
        String dates = (String) getIntent().getSerializableExtra("hike_date");
        String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
        String lengths = (String) getIntent().getSerializableExtra("length_hike");
        String levels = (String) getIntent().getSerializableExtra("level_hike");
        String desc = (String) getIntent().getSerializableExtra("des_hike");
        HikeModel hike = new HikeModel(Integer.parseInt(hike_id), names, locations, hike_date, parking_availables, lengths, levels, desc);
        obs_adapter = new ObservationAdapter(SearchObservation.this,this, obs, hike);
        view_rc.setAdapter(obs_adapter);
        view_rc.setLayoutManager(new LinearLayoutManager(SearchObservation.this));
    }

    private void setInit(){
        back_image_ic = findViewById(R.id.back_ic_obs);
        view_rc = findViewById(R.id.recyclerView);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.search_item);
    }

    public void displayObservation(String hikeID){
        Cursor point = myDB.readAllObservation(hikeID);
        if(point.getCount() == 0){
            Toast.makeText(SearchObservation.this, "No data here", Toast.LENGTH_SHORT).show();
        }else {
            while (point.moveToNext()){
                obs.add(new HikeObservation(Integer.parseInt(point.getString(0)),
                        Integer.parseInt(point.getString(1)),
                        point.getString(2),
                        point.getString(3),
                        point.getString(4)));
            }
        }
    }

    private void setNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                String names = (String) getIntent().getSerializableExtra("hike_name");
                String locations = (String) getIntent().getSerializableExtra("hike_location");
                String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
                String lengths = (String) getIntent().getSerializableExtra("length_hike");
                String levels = (String) getIntent().getSerializableExtra("level_hike");
                String desc = (String) getIntent().getSerializableExtra("des_hike");
                switch (item.getItemId()){
                    case R.id.search_item:
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
                    case R.id.home_page:

                        Intent pur2 = new Intent(getApplicationContext(), Observation.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager search_ad = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_action).getActionView();
        searchView.setSearchableInfo(search_ad.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                obs_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                obs_adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}