package com.example.project_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_mobile.R;
import com.example.project_mobile.activities.observation.Observation;

import java.text.ParseException;

public class DetailActivity extends AppCompatActivity {
    private ImageView back_image_ic;
    private Button obs_btn;
    private TextView back_txt, names_detail, locations_detail, dates_detail, parking_availables_detail, lengths_detail, levels_detail, desc_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        setInit();
        setListener();
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        obs_btn.setOnClickListener(v -> {
            String hikeID = (String) getIntent().getSerializableExtra("hike_id");
            Intent pur_obs = new Intent(DetailActivity.this, Observation.class);
            pur_obs.putExtra("hike_id", hikeID);
            pur_obs.putExtra("hike_name", names_detail.getText());
            pur_obs.putExtra("hike_location", locations_detail.getText());
            pur_obs.putExtra("hike_date", dates_detail.getText());
            pur_obs.putExtra("parking_available", parking_availables_detail.getText());
            pur_obs.putExtra("length_hike", lengths_detail.getText());
            pur_obs.putExtra("level_hike", levels_detail.getText());
            pur_obs.putExtra("des_hike", desc_detail.getText());
            startActivity(pur_obs);
        });
    }

    private void setInit(){
        back_image_ic = findViewById(R.id.back_ic);
        back_txt = findViewById(R.id.back_txt);
        obs_btn = findViewById(R.id.see_all_observation);
        names_detail = findViewById(R.id.name_hike_detail);
        locations_detail = findViewById(R.id.location_hike_detail);
        dates_detail = findViewById(R.id.date_hike_detail);
        parking_availables_detail = findViewById(R.id.parking_available_detail);
        lengths_detail = findViewById(R.id.length_hike_detail);
        levels_detail = findViewById(R.id.level_hike_detail);
        desc_detail = findViewById(R.id.des_hike_detail);
    }

    private void setListener(){
        Intent intent = new Intent(DetailActivity.this, HikeActivity.class);
        back_txt.setOnClickListener(v -> {
            startActivity(intent);
        });
        back_image_ic.setOnClickListener(v -> {
            startActivity(intent);
        });
    }

    public void getAndSetIntentData() throws ParseException {
        if (getIntent().hasExtra("hike_name") &&
                getIntent().hasExtra("hike_location") &&
                getIntent().hasExtra("hike_date") &&
                getIntent().hasExtra("parking_available") &&
                getIntent().hasExtra("length_hike")&&
                getIntent().hasExtra("level_hike")&&
                getIntent().hasExtra("des_hike")){
            names_detail.setText((String) getIntent().getSerializableExtra("hike_name"));
            locations_detail.setText((String) getIntent().getSerializableExtra("hike_location"));
            dates_detail.setText((String) getIntent().getSerializableExtra("hike_date"));
            parking_availables_detail.setText((String) getIntent().getSerializableExtra("parking_available"));
            lengths_detail.setText((String) getIntent().getSerializableExtra("length_hike"));
            levels_detail.setText((String) getIntent().getSerializableExtra("level_hike"));
            desc_detail.setText((String) getIntent().getSerializableExtra("des_hike"));
        }
    }
}