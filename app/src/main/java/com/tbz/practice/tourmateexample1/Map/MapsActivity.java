package com.tbz.practice.tourmateexample1.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.tbz.practice.tourmateexample1.R;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng StartPoints;
    LatLng EndPoints;

   public static LatLng currentLatLng;
    Location locationMain;
    private WebServiceAPI webServiceAPI;

    double lat;
    double lng;
    double destinationLat;
    double destinationLng;

    int counter=0;
    int zoomCounter=0;
    String markerName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},1);
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            @Override
            public void onMyLocationChange(Location location) {
                locationMain=location;
                lat=location.getLatitude();
                lng=location.getLongitude();
                currentLatLng =new LatLng(lat,lng);

                if(zoomCounter==0){
                   // Toast.makeText(MapsActivity.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
                    zoomCurrent();
                }

                if(counter==0 && getIntent().getDoubleExtra("lat",0)!=0){

                    destinationLat=getIntent().getDoubleExtra("lat",0);
                    destinationLng=getIntent().getDoubleExtra("lng",0);
                    markerName=getIntent().getStringExtra("name");

                    callAPI();

                }


            }
        });


    }
    public void callAPI(){
        counter+=1;



        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/directions/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceAPI= retrofit.create(WebServiceAPI.class);

        String url=String.format("json?origin=%f,%f&destination=%f,%f&key=AIzaSyDEiRwgH2PdMMqHKtQT_oR1WYfmjMTTi28",lat,lng,destinationLat,destinationLng);

        Call<WebResponse> webResponseCall=webServiceAPI.getAllProvider(url);
        webResponseCall.enqueue(new Callback<WebResponse>() {
            @Override
            public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
                Toast.makeText(MapsActivity.this, "responsed", Toast.LENGTH_SHORT).show();
                WebResponse webResponses=response.body();
               List<WebResponse.Step>steps=webResponses.getRoutes().get(0).getLegs().get(0).getSteps();
               for(int i=0;i<steps.size();i++) {
                    StartPoints = new LatLng(steps.get(i).getStartLocation().getLat(),
                            steps.get(i).getStartLocation().getLng());

                   // StartPointList.add(StartPoints);

                    EndPoints = new LatLng(steps.get(i).getEndLocation().getLat(),
                            steps.get(i).getEndLocation().getLng());

                   // endPointList.add(EndPoints);
                   PolylineOptions lines = new PolylineOptions()
                           .add(StartPoints)
                           .add(EndPoints)
                           .width(10)
                           .color(Color.GREEN);
                   mMap.addPolyline(lines);

                }


                //Toast.makeText(MapsActivity.this, String.valueOf(lat), Toast.LENGTH_SHORT).show();
                mMap.addMarker(new MarkerOptions().position(EndPoints).title(markerName));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,15));

            }

            @Override
            public void onFailure(Call<WebResponse> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void zoomCurrent(){
        zoomCounter+=1;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,16));
    }

}
