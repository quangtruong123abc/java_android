package com.example.project_mobile.activities;

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
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.adapters.HikeAdapter;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private BottomNavigationView view_bn;
    private RecyclerView view_rc;
    private Database database;
    private List<HikeModel> list_hike = new ArrayList<>();
    private HikeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setInit();
        setNavigationView();
        displayHike();
    }

    public void displayHike(){
        Cursor deg = database.readAllHikes();
        if(deg.getCount() == 0){
            Toast.makeText(SearchActivity.this, "No data here", Toast.LENGTH_SHORT).show();
        }else {
            while (deg.moveToNext()){
                list_hike.add(new HikeModel(Integer.parseInt(deg.getString(0)),
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
        view_bn.setSelectedItemId(R.id.search_item);
        adapter = new HikeAdapter(SearchActivity.this,this, list_hike);
        view_rc.setAdapter(adapter);
        view_rc.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        database = new Database(SearchActivity.this);
    }

    private void setNavigationView(){
        view_bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search_item:
                        return true;
                    case R.id.add_item:
                        startActivity(new Intent(getApplicationContext(), AddActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HikeActivity.class));
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
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}