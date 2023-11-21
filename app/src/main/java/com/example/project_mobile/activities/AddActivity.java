package com.example.project_mobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private BottomNavigationView button_view;
    private DatePickerDialog date_pd;
    private Button dates, saves;
    private Spinner Spinner;
    private EditText names, locations, desc, lengths;
    private RadioGroup btn_gr;
    private RadioButton btn_yes, btn_no;
    private AlertDialog.Builder notif_dl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        setInit();
        setListener();
        setNavigationView();
    }

    private void setInit(){
        saves = findViewById(R.id.save_hike);
        names = findViewById(R.id.name_hike_detail);
        locations = findViewById(R.id.location_hike);
        desc = findViewById(R.id.des_hike);
        lengths = findViewById(R.id.length_hike);

        btn_gr = findViewById(R.id.radioGroup);
        btn_yes = findViewById(R.id.radio_yes);
        btn_no = findViewById(R.id.radio_no);

        button_view = findViewById(R.id.bottom_nav);
        button_view.setSelectedItemId(R.id.add_item);
        dates = findViewById(R.id.datePickerButton);
        dates = findViewById(R.id.datePickerButton);
        dates.setText(getDate());

        notif_dl = new AlertDialog.Builder(this);

        Spinner = findViewById(R.id.spinner_level_hike);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.level_hike, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        Spinner.setAdapter(adapter);
    }

    private void setListener(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String dateStr = dateToString(day,month,year);
                dates.setText(dateStr);
            }
        };
        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        int day = cd.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        date_pd = new DatePickerDialog(this, style,dateSetListener,year,month,day);

        saves.setOnClickListener(v -> {
            if(isValidAddHikeDetails()){
                int requireGroup = btn_gr.getCheckedRadioButtonId();
                RadioButton radioGroup = findViewById(requireGroup);
                notif_dl.setTitle("Confirmation")
                        .setMessage("Name: " + names.getText().toString().trim() +"\n"+
                                "Location: "+ locations.getText().toString().trim()+"\n"+
                                "Date: "+ dates.getText().toString().trim()+"\n"+
                                "Parking available: "+ radioGroup.getText().toString().trim()+"\n"+
                                "Length: "+ lengths.getText().toString().trim()+"\n"+
                                "Level: "+ Spinner.getSelectedItem().toString().trim()+"\n"+
                                "Description: "+ desc.getText().toString().trim())
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addNewHike();
                        startActivity(new Intent(AddActivity.this, HikeActivity.class));
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

    private void addNewHike(){
        Database myDB = new Database(AddActivity.this);
        HikeModel New_HikeModel = new HikeModel();
        int requireGroup = btn_gr.getCheckedRadioButtonId();
        RadioButton radioGroup = findViewById(requireGroup);
        New_HikeModel.setHike_name(names.getText().toString().trim());
        New_HikeModel.setLocation_hike(locations.getText().toString().trim());
        New_HikeModel.setParking_available(radioGroup.getText().toString().trim());
        New_HikeModel.setDate_hike(dates.getText().toString().trim());
        New_HikeModel.setHike_length(lengths.getText().toString().trim());
        New_HikeModel.setHike_level(Spinner.getSelectedItem().toString().trim());
        New_HikeModel.setHike_description(desc.getText().toString().trim());
        myDB.addNewHike(New_HikeModel);
    }

    private void setNavigationView(){
        button_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_item:
                        return true;
                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), HikeActivity.class));
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

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private String dateToString(int day, int month, int year){
        if(day<10){
            return month + "/0" + day + "/"+ year;
        }else if(month <10){
            return "0" + month +"/"+ day + "/"+ year;
        }else if(day<10 && month <10){
            return "0" + month +"/0" + day + "/"+ year;
        }
        return month + "/" + day + "/"+ year;
    }
    private String getDate() {
        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        month = month + 1;
        int day = cd.get(Calendar.DAY_OF_MONTH);
        return dateToString(day,month,year);
    }

    public void openDatePiker(View view) {
        date_pd.show();
    }

    private Boolean isValidAddHikeDetails(){
        if(names.getText().toString().trim().isEmpty()){
            showToast("Please enter the name");
            return false;
        }else if(locations.getText().toString().trim().isEmpty()){
            showToast("Please enter the location ");
            return false;
        }
        else if(btn_gr.getCheckedRadioButtonId() == -1){
            showToast("Please tick parking available");
            return false;
        }
        else if(lengths.getText().toString().trim().isEmpty()){
            showToast("Please enter the length");
            return false;
        }
        else if(desc.getText().toString().trim().isEmpty()){
            showToast("Please enter the description");
            return false;
        }
        return true;
    }

}