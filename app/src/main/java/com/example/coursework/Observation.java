package com.example.coursework;

public class Observation {

    private int obs_id;
    private String obs_name;
    private String obs_time;
    private String obs_comment;

    private String obs_hike;

    public Observation(int id,String name,String time,String comment, String hike_id)
    {
        this.obs_id = id;
        this.obs_name = name;
        this.obs_time = time;
        this.obs_comment = comment;
        this.obs_hike = hike_id;
    }

    public int getObsId()
    {
        return this.obs_id;
    }

    public String getObsName()
    {
        return this.obs_name;
    }

    public String getObsTime()
    {
        return this.obs_time;
    }

    public String getObsComment()
    {
        return this.obs_comment;
    }

    public String getObsHikeId(){
        return this.obs_hike;
    }
}
