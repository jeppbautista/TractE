package com.tracker.jessy.tracke;

public class User
{
    public String username;
    public String password;
    public boolean isCourier;

    public User()
    {

    }

    public User (String user, String pass, boolean courier)
    {
        this.username = user;
        this.password = pass;
        this.isCourier = courier;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isCourier() {
        return isCourier;
    }


}
