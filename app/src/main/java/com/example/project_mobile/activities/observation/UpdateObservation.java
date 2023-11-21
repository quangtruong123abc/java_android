package com.example.project_mobile.activities.observation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.text.ParseException;
import java.util.Calendar;

public class UpdateObservation extends AppCompatActivity {
    private ImageView back_ic;
    private TextView title_ac;
    private Button u_ob;
    private EditText name_obs, comments_obs;
    private Button time_obs;
    private AlertDialog.Builder alertDialog;
    private String hike_Id, hike_dates;
    private TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_observation);
        hike_Id = (String) getIntent().getSerializableExtra("hike_id");
        hike_dates = (String) getIntent().getSerializableExtra("hike_date");
        setInit();
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        setListeners();
    }

    private void updateObservation(){
        Database myDB = new Database(UpdateObservation.this);
        HikeObservation obs = new HikeObservation();
        obs.setHike_id(Integer.valueOf(hike_Id));
        obs.setObs_id(Integer.valueOf((String) getIntent().getSerializableExtra("obs_id")));
        obs.setObs_name(name_obs.getText().toString().trim());
        obs.setObs_time(time_obs.getText().toString().trim());
        obs.setObs_comment(comments_obs.getText().toString().trim());
        myDB.updateObservation(obs);
    }

    private void setInit(){
        back_ic = findViewById(R.id.back_ic_obs);
        title_ac = findViewById(R.id.title_acctionbar);
        title_ac.setText("Update Observation");
        name_obs = findViewById(R.id.name_obs_update);
        comments_obs = findViewById(R.id.comments_obs_update);
        u_ob = findViewById(R.id.update_obs);
        time_obs = findViewById(R.id.datePickerButton);
        alertDialog = new AlertDialog.Builder(this);
    }

    private void setListeners(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                time_obs.setText((hour >= 10 ? hour : ("0" + String.valueOf(hour))) + ":" + (minute >= 10 ? minute : ("0" + String.valueOf(minute))) + " - " + hike_dates);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(this,timeSetListener,hour,minute,true);

        String names = (String) getIntent().getSerializableExtra("hike_name");
        String locations = (String) getIntent().getSerializableExtra("hike_location");
        String dates = (String) getIntent().getSerializableExtra("hike_date");
        String parking_availables = (String) getIntent().getSerializableExtra("parking_available");
        String lengths = (String) getIntent().getSerializableExtra("length_hike");
        String levels = (String) getIntent().getSerializableExtra("level_hike");
        String desc = (String) getIntent().getSerializableExtra("des_hike");
        back_ic.setOnClickListener(v -> {
            Intent pur = new Intent(UpdateObservation.this, Observation.class);
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
        u_ob.setOnClickListener(v -> {
            if(isValidAddHikeDetails()){
                alertDialog.setTitle("Confirmation to Update")
                        .setMessage("Name: " + name_obs.getText().toString().trim() +"\n"+
                                "Time: "+ time_obs.getText().toString().trim() +"\n"+
                                "Comments: "+ comments_obs.getText().toString().trim())
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateObservation();
                                startActivity(new Intent(UpdateObservation.this, Observation.class));
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

    public void getAndSetIntentData() throws ParseException {
        if (getIntent().hasExtra("obs_name") &&
                getIntent().hasExtra("obs_time") &&
                getIntent().hasExtra("obs_comment")){
            name_obs.setText((String) getIntent().getSerializableExtra("obs_name"));
            time_obs.setText((String) getIntent().getSerializableExtra("obs_time"));
            comments_obs.setText((String) getIntent().getSerializableExtra("obs_comment"));
        }
    }

    private Boolean isValidAddHikeDetails(){
        if(name_obs.getText().toString().trim().isEmpty()){
            showToast("Please enter the  name of observation");
            return false;
        }else if(comments_obs.getText().toString().trim().isEmpty()){
            showToast("Please enter the comments of observation");
            return false;
        }
        return true;
    }

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void openTimePiker(View view) {
        timePickerDialog.show();
    }

}