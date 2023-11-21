package com.example.project_mobile.models;

public class HikeObservation {
    private Integer obs_id;
    private Integer hike_id;
    private String obs_name;
    public String obs_time;
    public String obs_comment;

    public HikeObservation() {
    }

    public HikeObservation(Integer obs_id, Integer hike_id, String obs_name, String obs_time, String obs_comment) {
        this.obs_id = obs_id;
        this.hike_id = hike_id;
        this.obs_name = obs_name;
        this.obs_time = obs_time;
        this.obs_comment = obs_comment;
    }

    public Integer getObs_id() {
        return obs_id;
    }

    public void setObs_id(Integer obs_id) {
        this.obs_id = obs_id;
    }

    public String getObs_name() {
        return obs_name;
    }

    public void setObs_name(String obs_name) {
        this.obs_name = obs_name;
    }

    public String getObs_time() {
        return obs_time;
    }

    public void setObs_time(String obs_time) {
        this.obs_time = obs_time;
    }

    public String getObs_comment() {
        return obs_comment;
    }

    public void setObs_comment(String obs_comment) {
        this.obs_comment = obs_comment;
    }

    public Integer getHike_id() {
        return hike_id;
    }

    public void setHike_id(Integer hike_id) {
        this.hike_id = hike_id;
    }
}
