package com.example.project_mobile.activities.observation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeObservation;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class AddObservation extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Button save_ov, date;
    private EditText name_ov, feb;
    private TimePickerDialog timePD;
    private AlertDialog.Builder notifDl;
    private ImageView back_image_ic;
    private TextView title_obs;
    private String hike_id, hike_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_observation);
        hike_id = (String) getIntent().getStringExtra("hike_id");
        hike_date = (String) getIntent().getStringExtra("hike_date");
        setInit();
        setListener();
        setNavigationView();
    }

    private void setInit(){
        back_image_ic = findViewById(R.id.back_ic_obs);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.add_item);
        title_obs = findViewById(R.id.title_acctionbar);
        title_obs.setText("Add Observation");
        save_ov = findViewById(R.id.save_obs);
        name_ov = findViewById(R.id.name_obs);
        feb = findViewById(R.id.comment_ob);
        notifDl = new AlertDialog.Builder(this);
        date = findViewById(R.id.datePickerButton);
        date.setText(getTime() + " - " + hike_date);
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {
                date.setText((hours >= 10 ? hours : ("0" + String.valueOf(hours))) + ":" + (minutes >= 10 ? hours : ("0" + String.valueOf(minutes))) + " - " + hike_date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int minutes = cal.get(Calendar.MINUTE);
        timePD = new TimePickerDialog(this,timeSetListener,hours,minutes,true);
    }

    private void setListener(){
        back_image_ic.setOnClickListener(v -> {
            Intent pur = new Intent(AddObservation.this, Observation.class);
            pur.putExtra("hike_id", hike_id);
            startActivity(pur);
            overridePendingTransition(0,0);
        });
        save_ov.setOnClickListener(v -> {
            if(isValidAddHikeDetails()){
                notifDl.setTitle("Confirmation")
                        .setMessage("Name: " + name_ov.getText().toString().trim() +"\n"+
                                "Time: "+ date.getText().toString().trim()+"\n"+
                                "Comment: "+ feb.getText().toString().trim())
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                addNewObservation();
                                startActivity(new Intent(AddObservation.this, Observation.class));
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });
    }

    private void addNewObservation(){
        Database myDB = new Database(AddObservation.this);
        HikeObservation obs = new HikeObservation();
        obs.setObs_name(name_ov.getText().toString().trim());
        obs.setObs_comment(feb.getText().toString().trim());
        obs.setHike_id(Integer.valueOf(hike_id));
        obs.setObs_time(date.getText().toString().trim());
        myDB.addNewObservation(obs);
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
                    case R.id.add_item:
                        return true;
                    case R.id.home_page:
                        Intent pur = new Intent(getApplicationContext(), Observation.class);
                        pur.putExtra("hike_id", hike_id);
                        pur.putExtra("hike_date", hike_date);
                        pur.putExtra("hike_name", names);
                        pur.putExtra("hike_location", locations);
                        pur.putExtra("parking_available", parking_availables);
                        pur.putExtra("length_hike", lengths);
                        pur.putExtra("level_hike", levels);
                        pur.putExtra("des_hike", desc);
                        startActivity(pur);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search_item:
                        Intent purs = new Intent(getApplicationContext(), SearchObservation.class);
                        purs.putExtra("hike_id", hike_id);
                        purs.putExtra("hike_date", hike_date);
                        purs.putExtra("hike_name", names);
                        purs.putExtra("hike_location", locations);
                        purs.putExtra("parking_available", parking_availables);
                        purs.putExtra("length_hike", lengths);
                        purs.putExtra("level_hike", levels);
                        purs.putExtra("des_hike", desc);
                        startActivity(purs);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void openTimePiker(View view) {
        timePD.show();
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidAddHikeDetails(){
        if(name_ov.getText().toString().trim().isEmpty()){
            showToast("Name observation");
            return false;
        }else if(feb.getText().toString().trim().isEmpty()){
                showToast("Name hike");
            return false;
        }
        return true;
    }

    private String getTime() {
        Calendar cd = Calendar.getInstance();
        int hour = cd.get(Calendar.HOUR_OF_DAY);
        int minute = cd.get(Calendar.MINUTE);
        return (hour >= 10 ? hour : ("0" + String.valueOf(hour))) + ":" + (minute >= 10 ? minute : ("0" + String.valueOf(minute))) ;
    }
}