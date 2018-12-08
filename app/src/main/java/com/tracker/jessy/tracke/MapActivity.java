package com.tracker.jessy.tracke;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {


    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private GoogleMap mMap;
    private FirebaseAuth mAuth;

    private static final String TAG = "xxx";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCAL_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Context mContext;

    private Boolean mLocationPermissionsGranted = false;
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    final Marker[] marker = {null};
    private FusedLocationProviderClient mFusedLocationProviderClient;

    LinearLayout linearLayout;
    BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(@Nullable Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.map_main);

        linearLayout = (LinearLayout) findViewById(R.id.map_bottom_sheet_id);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);

        mAuth = FirebaseAuth.getInstance();

        getLocationPermission();
//        loginToFirebase();
//        startTrackerService();
        initMap();
        //startLocationUpdates();
    }

    private void getDeviceLocation(){
        Log.d(TAG,"getDeviceLocation: getting the device location ");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        final String tracking = getIntent().getStringExtra("TRACKING");
        final String[] courierID = {""};

        final DatabaseReference DB = FirebaseDatabase.getInstance().getReference();
        DB.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    if (d.child("tracking").getValue().toString().equals(tracking))
                    {
                        courierID[0] = d.getKey().toString();
                        DB.child("users").child(courierID[0]).child("location").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                try{
                                    if(marker[0] == null)
                                    {
                                        MarkerOptions markerOptions = new MarkerOptions().position(new LatLng((double)dataSnapshot.child("latitude").getValue(),(double)dataSnapshot.child("longitude").getValue())).title("Hello Maps");
                                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_tracker));
                                        setMarker(markerOptions);

                                        // Setting Text status
                                        TextView product_name = (TextView)findViewById(R.id.product_name_text);
                                        TextView deliveryStatus = (TextView)findViewById(R.id.delivery_status_text);
                                        product_name.setText("Hello");
                                        deliveryStatus.setText("Nagdedeliver na");
                                    }
                                    else
                                    {
                                        ((Marker)marker[0]).setPosition(new LatLng((double)dataSnapshot.child("latitude").getValue(),(double)dataSnapshot.child("longitude").getValue()));
                                        Log.d("xxx2", dataSnapshot.child("latitude").getValue().toString() + " " + dataSnapshot.child("longitude").getValue().toString());
                                    }

                                    moveCamera(new LatLng((double)dataSnapshot.child("latitude").getValue(),(double)dataSnapshot.child("longitude").getValue()),
                                            DEFAULT_ZOOM);
                                }catch (Exception e)
                                {

                                }

                            } // TODO flag onDelivery

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        try{
//            if(mLocationPermissionsGranted){
//                final Task location = mFusedLocationProviderClient.getLastLocation();
//                location.addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if(task.isSuccessful()){
//                            Log.d(TAG,"onComplete: found Location!");
//                            Location currentLocation = (Location) task.getResult();
//
//                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM);
//                        }else{
//                            Log.d(TAG,"onComplete: current location is null");
//                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        }catch (SecurityException e){
//            Log.e(TAG,"getDeviceLocation: Security Exception " + e.getMessage());
//        }
    }



    private void getLocationPermission(){
        Log.d(TAG,"getLocationPermission: getting location permissions");
        String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext()
                    ,COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED ){
                mLocationPermissionsGranted = true;

                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCAL_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCAL_PERMISSION_REQUEST_CODE);
        }

    }

    private void moveCamera(LatLng latLng, float zoom)
    {
        Log.d(TAG,"moveCamera: moving the camera to: lat:" + latLng.latitude + ", lng:" + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void setMarker(MarkerOptions m)
    {
        marker[0] = mMap.addMarker(m);
    }

    private void initMap()
    {
//        Log.d(TAG,"initMap: Initializing Map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();

        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

        }

    }
}
