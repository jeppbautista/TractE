package com.tracker.jessy.tracke;

public class User
{
    public String username;
    public String UID;
    public boolean isCourier;
    public boolean isDelivered;
    public Location location;
    public String tracking;


    public User (String user, boolean courier, boolean delivered, String track, Location loc)
    {
        this.username = user;
        this.isCourier = courier;
        this.isDelivered = delivered;
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
