package com.example.coursework;

import java.util.ArrayList;

public class Hike {
    private int hike_id;
    private String hike_name;
    private String hike_location;
    private String hike_date;
    private String hike_status;
    private String hike_length;
    private String hike_level;
    private String hike_description;

    public Hike(int id, String name, String location, String date, String status, String length, String level, String description)
    {
        this.hike_id = id;
        this.hike_name = name;
        this.hike_location = location;
        this.hike_date = date;
        this.hike_status = status;
        this.hike_length = length;
        this.hike_level = level;
        this.hike_description = description;
    }

    public void setId(int id)
    {
        this.hike_id = id;
    }
    public int getId()
    {
        return this.hike_id;
    }

    public void setName(String name)
    {
        this.hike_name = name;
    }
    public String getName()
    {
        return this.hike_name;
    }

    public void setLocation(String location)
    {
        this.hike_location = location;
    }
    public String getLocation()
    {
        return this.hike_location;
    }

    public void setDate(String date)
    {
        this.hike_date = date;
    }
    public String getDate()
    {
        return this.hike_date;
    }

    public void setStatus(String status)
    {
        this.hike_status = status;
    }
    public String getStatus()
    {
        return this.hike_status;
    }

    public void setLength(String length)
    {
        this.hike_length = length;
    }
    public String getLength()
    {
        return this.hike_length;
    }

    public void setLevel(String level)
    {
        this.hike_level = level;
    }
    public String getLevel()
    {
        return this.hike_level;
    }

    public void setDescription(String description)
    {
        this.hike_description = description;
    }
    public String getDescription()
    {
        return this.hike_description;
    }




}
