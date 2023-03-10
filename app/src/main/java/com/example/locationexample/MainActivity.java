package com.example.locationexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    private Button gps_bt;
    private EditText lat_val, lon_val;
    private LocationManager locationManager_;
    double latitude_,logitude_;

    Handler hdl = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gps_bt = findViewById(R.id.gps_btn);
        lat_val = findViewById(R.id.lat_val);
        lon_val = findViewById(R.id.lon_val);

        gps_bt.setOnClickListener(this);
        locationManager_ = (LocationManager) getSystemService(LOCATION_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager_.removeUpdates(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gps_btn:
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager_.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                break;
        }
    }

    private class Locations implements Runnable{
        private double p_lat;
        private double p_long;

        public Locations(double lat,double longi){
            p_lat = lat;
            p_long = longi;
        }

        @Override
        public void run() {
            String a = new Double(p_lat).toString();
            String b = new Double(p_long).toString();
            lat_val.setText(a);
            lon_val.setText(b);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
         latitude_ = location.getLatitude();
         logitude_ = location.getLongitude();
         Locations mylocation = new Locations(latitude_,logitude_);
//         String lat = new Double(latitude_).toString();
//         String longi = new Double(logitude_).toString();
//         lat_val.setText(lat);
//        lat_val.setText(longi);
        hdl.post(mylocation);



    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}