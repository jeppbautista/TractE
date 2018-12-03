package com.tracker.jessy.tracke;

public class Location
{

    private float longitude;
    private float latitude;

    public Location()
    {
        setLatitude(-1);
        setLongitude(-1);
    }

    public Location(float lat, float longi)
    {
        setLatitude(lat);
        setLongitude(longi);
    }

    public float getLongitude()
    {
        return longitude;
    }

    public void setLongitude(float longitude)
    {
        this.longitude = longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    public void setLatitude(float latitude)
    {
        this.latitude = latitude;
    }


}
