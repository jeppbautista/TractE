package com.tracker.jessy.tracke;

public class User
{
    public String username;
    public String UID;
    public boolean isCourier;
    public Location location;
    public String tracking;


    public User (String user, boolean courier, String track, Location loc)
    {
        this.username = user;
        this.isCourier = courier;
        this.tracking = track;
        this.location = loc;
    }


    public String getUsername() {
        return username;
    }


    public boolean isCourier() {
        return isCourier;
    }


}
