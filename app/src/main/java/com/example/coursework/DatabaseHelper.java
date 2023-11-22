package com.example.coursework;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "coursework";
    private static final String TABLE_NAME = "hikes_table";
    public static final String ID_COLUMN = "hike_id";
    public static final String NAME_COLUMN = "name";
    public static final String LOCATION_COLUMN = "location";
    public static final String DATE_COLUMN = "date";
    public static final String STATUS_COLUMN = "parking_status";
    public static final String LENGTH_COLUMN = "length";
    public static final String LEVEL_COLUMN = "level";
    public static final String DESCRIPTION_COLUMN = "description";


    private static final String OBSERVATION_TABLE = "observation_table";

    public static final String OBSERVATION_ID = "observation_id";

    public static final String OBSERVATION_NAME = "observation_name";

    public static final String OBSERVATION_TIME = "observation_time";

    public static final String OBSERVATION_COMMENT = "observation_comment";

    public static final String OBSERVATION_HIKE = "observation_hike";

    private SQLiteDatabase database;

    private static final String TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT," +
                    "%s TEXT)",
            TABLE_NAME, ID_COLUMN, NAME_COLUMN, LOCATION_COLUMN,DATE_COLUMN, STATUS_COLUMN,LENGTH_COLUMN,LEVEL_COLUMN,DESCRIPTION_COLUMN
    );

    private static final String OBSERVATION_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            OBSERVATION_TABLE, OBSERVATION_ID, OBSERVATION_NAME, OBSERVATION_TIME, OBSERVATION_COMMENT, OBSERVATION_HIKE
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(OBSERVATION_TABLE_CREATE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OBSERVATION_TABLE);

        Log.v(this.getClass().getName(), TABLE_NAME +
                "Database has been update with the newest version: " + newVersion
        );
        onCreate(db);
    }

    public long insertRow(String name, String location, String date, String status, String length, String level, String description) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, name);
        rowValues.put(LOCATION_COLUMN, location);
        rowValues.put(DATE_COLUMN, date);
        rowValues.put(STATUS_COLUMN, status);
        rowValues.put(LENGTH_COLUMN, length);
        rowValues.put(LEVEL_COLUMN, level);
        rowValues.put(DESCRIPTION_COLUMN, description);

        return database.insertOrThrow(TABLE_NAME, null, rowValues);
    }

    public long insertObservationRow(String observation_name,String observation_time, String observation_comment, String observation_hike) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(OBSERVATION_NAME, observation_name);
        rowValues.put(OBSERVATION_TIME, observation_time);
        rowValues.put(OBSERVATION_COMMENT, observation_comment);
        rowValues.put(OBSERVATION_HIKE, observation_hike);

        return database.insertOrThrow(OBSERVATION_TABLE, null, rowValues);
    }

    public ArrayList<Hike> getAll() {

        Cursor results = database.rawQuery("SELECT * FROM hikes_table", null);

        ArrayList<Hike> hikesArray = new ArrayList<Hike>();

        results.moveToFirst();

        while (!results.isAfterLast()) {

            int id = results.getInt(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date = results.getString(3);
            String status = results.getString(4);
            String length = results.getString(5);
            String level = results.getString(6);
            String description = results.getString(7);

            Hike h = new Hike(id,name,location,date,status,length,level,description);
            hikesArray.add(h);

            results.moveToNext();
        }
        results.close();

        return hikesArray;
    }

    public ArrayList<Observation> getAllObservation() {

        Cursor results = database.rawQuery("SELECT * FROM observation_table", null);

        ArrayList<Observation> observationsArray = new ArrayList<Observation>();

        results.moveToFirst();

        while (!results.isAfterLast()) {

            int obs_id = results.getInt(0);
            String obs_name = results.getString(1);
            String obs_time = results.getString(2);
            String obs_comment = results.getString(3);
            String obs_hike = results.getString(4);

            Observation observation = new Observation(obs_id,obs_name,obs_time,obs_comment,obs_hike);
            observationsArray.add(observation);

            results.moveToNext();
        }
        results.close();

        return observationsArray;
    }

    public void updateHike(String id, Hike hike)
    {
        ContentValues updateValues = new ContentValues();
        updateValues.put(NAME_COLUMN, hike.getName());
        updateValues.put(LOCATION_COLUMN, hike.getLocation());
        updateValues.put(DATE_COLUMN, hike.getDate());
        updateValues.put(STATUS_COLUMN, hike.getStatus());
        updateValues.put(LENGTH_COLUMN,hike.getLength());
        updateValues.put(LEVEL_COLUMN,hike.getLevel());
        updateValues.put(DESCRIPTION_COLUMN, hike.getDescription());

        database.update("hikes_table",updateValues,"hike_id = " + id,null);
    }

    public void updateObservation(String id, Observation obs)
    {
        ContentValues updateValues = new ContentValues();
        updateValues.put(OBSERVATION_NAME, obs.getObsName());
        updateValues.put(OBSERVATION_TIME, obs.getObsTime());
        updateValues.put(OBSERVATION_COMMENT, obs.getObsComment());
        updateValues.put(OBSERVATION_HIKE, obs.getObsHikeId());


        database.update("observation_table",updateValues,"observation_id = " + id,null);
    }

    public void deleteHike(String id)
    {
        database.delete("hikes_table", "hike_id = " + id, null);
    }

    public void deleteObservation(String id)
    {
        database.delete("observation_table", "observation_id = " + id, null);
    }

    public void deleteAllHikes()
    {
        database.delete("hikes_table",null,null);
        database.delete("observation_table",null,null);
    }

    public ArrayList<Hike> getSearchedHike(String hikeName)
    {
        Cursor results = database.rawQuery("SELECT * FROM hikes_table WHERE name LIKE ?", new String[] {"%" + hikeName + "%"});

        ArrayList<Hike> selectedHikesArray = new ArrayList<Hike>();

        results.moveToFirst();

        while (!results.isAfterLast()) {

            int id = results.getInt(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date = results.getString(3);
            String status = results.getString(4);
            String length = results.getString(5);
            String level = results.getString(6);
            String description = results.getString(7);

            Hike h = new Hike(id,name,location,date,status,length,level,description);
            selectedHikesArray.add(h);

            results.moveToNext();
        }
        results.close();

        return selectedHikesArray;
    }



}