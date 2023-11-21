package com.example.project_mobile.activities.observation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_mobile.R;

import java.text.ParseException;

public class ObservationDetail extends AppCompatActivity {
    private ImageView back_image_ic;
    private String hike_Id;
    private TextView title_txt, name_obs, time_obs, feb_obs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_observation);
        hike_Id = (String) getIntent().getSerializableExtra("hike_id");
        setInit();
        setListeners();
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setInit(){
        back_image_ic = findViewById(R.id.back_ic_obs);
        title_txt = findViewById(R.id.title_acctionbar);
        title_txt.setText("Detail Observation");
        name_obs = findViewById(R.id.name_obs_detail);
        time_obs = findViewById(R.id.time_obs_detail);
        feb_obs = findViewById(R.id.comments_obs_detail);
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
            Intent pur = new Intent(ObservationDetail.this, Observation.class);
            pur.putExtra("hike_id", hike_Id);
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

    public void getAndSetIntentData() throws ParseException {
        if (getIntent().hasExtra("obs_name") &&
                getIntent().hasExtra("obs_time") &&
                getIntent().hasExtra("obs_comment")){
            name_obs.setText((String) getIntent().getSerializableExtra("obs_name"));
            time_obs.setText((String) getIntent().getSerializableExtra("obs_time"));
            feb_obs.setText((String) getIntent().getSerializableExtra("obs_comment"));
        }
    }
}