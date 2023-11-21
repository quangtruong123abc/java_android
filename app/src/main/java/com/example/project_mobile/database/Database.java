package com.example.project_mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.project_mobile.models.HikeModel;
import com.example.project_mobile.models.HikeObservation;

public class Database extends SQLiteOpenHelper {
    private Context cir;
    private static final String DATABASE_NAME = "Coursework.db";
    private static final String TABLE_HIKE = "hikes";
    private static final String TABLE_OBSERVATION = "observations";
    public Database(@Nullable Context cir) {
        super(cir, DATABASE_NAME, null, 1);
        this.cir = cir;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_HIKE + "(hike_id INTEGER primary key autoincrement, " +
                "hike_name TEXT NOT NULL, " +
                "hike_location TEXT NOT NULL, " +
                "parking_available TEXT NOT NULL, " +
                "hike_length INTEGER NOT NULL," +
                "hike_level TEXT NOT NULL," +
                "hike_description TEXT NOT NULL)");
        db.execSQL("CREATE TABLE " + TABLE_OBSERVATION + "(observation_id INTEGER primary key autoincrement," +
                "hike_id INTEGER NOT NULL," +
                "observation_name TEXT NOT NULL," +
                "date_time TEXT NOT NULL," +
                "comments TEXT NOT NULL," +
                "foreign key (hike_id) references " + TABLE_HIKE + "(hike_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_HIKE);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_OBSERVATION);
        onCreate(db);
    }

    public void addNewHike(HikeModel hike){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues DataValues = new ContentValues();
        DataValues.put("hike_name", hike.getHike_name());
        DataValues.put("hike_location", hike.getLocation_hike());
        DataValues.put("date_hike",hike.getDate_hike());
        DataValues.put("parking_available",hike.getParking_available());
        DataValues.put("hike_length",hike.getHike_length());
        DataValues.put("hike_level",hike.getHike_level());
        DataValues.put("hike_description",hike.getHike_description());
        long result = db.insert(TABLE_HIKE,null,DataValues);
        if(result == -1){
            Toast.makeText(cir, "Added to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllHikes(){
        String query ="SELECT hike_id, hike_name, hike_location, date_hike, parking_available, hike_length, hike_level, hike_description FROM "+ TABLE_HIKE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateHike(HikeModel hike){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues DataValues = new ContentValues();
            DataValues.put("hike_name", hike.getHike_name());
            DataValues.put("hike_location", hike.getLocation_hike());
            DataValues.put("date_hike",hike.getDate_hike());
            DataValues.put("parking_available",hike.getParking_available());
            DataValues.put("hike_length",hike.getHike_length());
            DataValues.put("hike_level",hike.getHike_level());
            DataValues.put("hike_description",hike.getHike_description());
        long result = db.update(TABLE_HIKE, DataValues, "hike_id=?",new String[]{String.valueOf(hike.getHike_id())});
        if(result == -1){
            Toast.makeText(cir, "Update to erol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, " Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_HIKE, "hike_id=?",new String[]{row_id});
        if(result == -1){
            Toast.makeText(cir, "Delete to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_HIKE, null,null);
        if(result == -1){
            Toast.makeText(cir, "Clear to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Clear all  Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addNewObservation(HikeObservation observation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues DataValues = new ContentValues();
        DataValues.put("observation_name", observation.getObs_name());
        DataValues.put("date_time", observation.getObs_time());
        DataValues.put("comments",observation.getObs_comment());
        DataValues.put("hike_id",observation.getHike_id());
        long result = db.insert(TABLE_OBSERVATION,null,DataValues);
        if(result == -1){
            Toast.makeText(cir, "Add to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Added Successfully ", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllObservation(String hikeId){
        String query ="SELECT observation_id, hike_id, observation_name, date_time, comments  FROM "+ TABLE_OBSERVATION + " WHERE hike_id = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, new String[]{hikeId});
        }
        return cursor;
    }

    public void updateObservation(HikeObservation observation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues DataValues = new ContentValues();
        DataValues.put("observation_name", observation.getObs_name());
        DataValues.put("date_time", observation.getObs_time());
        DataValues.put("comments",observation.getObs_comment());
        long result = db.update(TABLE_OBSERVATION, DataValues, "hike_id=? and observation_id=?",new String[]{String.valueOf(observation.getHike_id()), String.valueOf(observation.getObs_id())});
        if(result == -1){
            Toast.makeText(cir, "Update to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneRowObs(String ob_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_OBSERVATION, "hike_id=?",new String[]{ob_id});
        if(result == -1){
            Toast.makeText(cir, "Delete to errol", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(cir, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
