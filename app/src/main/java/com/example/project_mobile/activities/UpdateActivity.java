package com.example.project_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_mobile.R;
import com.example.project_mobile.database.Database;
import com.example.project_mobile.models.HikeModel;

import java.text.ParseException;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    private DatePickerDialog date_pd;
    private Button date_btn, update;
    private Spinner spinner;
    private EditText names, locations, desc, lengths;
    private RadioGroup btn_gr;
    private RadioButton btn_yes, btn_no;
    private AlertDialog.Builder notif_dl;
    private ImageView back_img_ic;
    private TextView back_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_activity);
        setInit();
        try {
            getAndSetIntentData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initDatePiker();
        setListener();
    }

    private void updateHike(){
        Database myDB = new Database(UpdateActivity.this);
        HikeModel update = new HikeModel();
        int requireGroup = btn_gr.getCheckedRadioButtonId();
        RadioButton radioGroup = findViewById(requireGroup);
        update.setHike_id(Integer.parseInt(getIntent().getStringExtra("hike_id")));
        update.setHike_name(names.getText().toString().trim());
        update.setLocation_hike(locations.getText().toString().trim());
        update.setParking_available(radioGroup.getText().toString().trim());
        update.setDate_hike(date_btn.getText().toString().trim());
        update.setHike_length(lengths.getText().toString().trim());
        update.setHike_level(spinner.getSelectedItem().toString().trim());
        update.setHike_description(desc.getText().toString().trim());
        myDB.updateHike(update);
    }

    private Boolean isValidAddHikeDetails(){
        if(names.getText().toString().trim().isEmpty()){
            showToast("Please enter the name");
            return false;
        }else if(locations.getText().toString().trim().isEmpty()){
            showToast("Please enter the location");
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

    private void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setInit(){
        date_btn = findViewById(R.id.datePickerButton);
        names = findViewById(R.id.name_hike_detail);
        locations = findViewById(R.id.location_hike);
        desc = findViewById(R.id.des_hike);
        lengths = findViewById(R.id.length_hike);
        update = findViewById(R.id.update_hike);
        btn_gr = findViewById(R.id.radioGroup);
        btn_yes = findViewById(R.id.radio_yes);
        btn_no = findViewById(R.id.radio_no);
        spinner = findViewById(R.id.spinner_level_hike);
        notif_dl = new AlertDialog.Builder(this);
        back_img_ic = findViewById(R.id.back_ic);
        back_txt = findViewById(R.id.back_txt);
    }

    private void setListener(){
        back_txt.setOnClickListener(v -> {
            startActivity(new Intent(UpdateActivity.this, HikeActivity.class));
        });
        back_img_ic.setOnClickListener(v -> {
            startActivity(new Intent(UpdateActivity.this, HikeActivity.class));
        });
        update.setOnClickListener(v -> {
            if(isValidAddHikeDetails()){
                int requireGroup = btn_gr.getCheckedRadioButtonId();
                RadioButton radioGroup = findViewById(requireGroup);
                notif_dl.setTitle("Confirmation to Update")
                        .setMessage("Name: " + names.getText().toString().trim() +"\n"+
                                "Location: "+ locations.getText().toString().trim()+"\n"+
                                "Date: "+ date_btn.getText().toString().trim()+"\n"+
                                "Parking available: "+ radioGroup.getText().toString().trim()+"\n"+
                                "Length: "+ lengths.getText().toString().trim()+"\n"+
                                "Level: "+ spinner.getSelectedItem().toString().trim()+"\n"+
                                "Description: "+ desc.getText().toString().trim())
                        .setCancelable(true)
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                updateHike();
                                startActivity(new Intent(UpdateActivity.this, HikeActivity.class));
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
        if (getIntent().hasExtra("hike_name") &&
                getIntent().hasExtra("hike_location") &&
                getIntent().hasExtra("hike_date") &&
                getIntent().hasExtra("parking_available") &&
                getIntent().hasExtra("length_hike")&&
                getIntent().hasExtra("level_hike")&&
                getIntent().hasExtra("des_hike")){
            names.setText((String) getIntent().getSerializableExtra("hike_name"));
            locations.setText((String) getIntent().getSerializableExtra("hike_location"));
            date_btn.setText((String) getIntent().getSerializableExtra("hike_date"));
            String radio = getIntent().getStringExtra("parking_available");
            if (radio.equals("Yes"))
            {
                btn_yes.setChecked(true);
                btn_no.setChecked(false);
            }
            else {
                btn_yes.setChecked(false);
                btn_no.setChecked(true);
            }
            lengths.setText((String) getIntent().getSerializableExtra("length_hike"));
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.level_hike, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            String selection = (String) getIntent().getSerializableExtra("level_hike");
            int spinnerPosition = adapter.getPosition(selection);
            spinner.setSelection(spinnerPosition);
            desc.setText((String) getIntent().getSerializableExtra("des_hike"));
        }
    }
    private void initDatePiker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = dateToString(day, month, year);
                date_btn.setText(date);
            }
        };

        Calendar cd = Calendar.getInstance();
        int year = cd.get(Calendar.YEAR);
        int month = cd.get(Calendar.MONTH);
        int day = cd.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        date_pd = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }
    private String dateToString(int day, int month, int year){
        if(day<10){
            return month + "/0" + day + "/"+ year;
        }else if(month <10){
            return "0" + month+ "/" + day + "/"+ year;
        }else if(day<10 && month <10){
            return "0" + month +"/0" + day + "/"+ year;
        }
        return month + "/" + day + "/"+ year;
    }

    public void openDatePiker(View view) {
        date_pd.show();
    }
}