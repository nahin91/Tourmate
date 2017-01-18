package com.tbz.practice.tourmateexample1.NearBy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tbz.practice.tourmateexample1.Map.MapsActivity;
import com.tbz.practice.tourmateexample1.R;
import com.tbz.practice.tourmateexample1.Weather.LocationClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearByActivity extends AppCompatActivity {

    Places places;
    private ArrayList<Places>placesList=new ArrayList<>();
    ListView listView;

    double latitude;
    double longitude;
    String apiKey="AIzaSyDt_WjaOFfZ9YFCt5KkkZGCWW8RxsFXMKA";
    String category;
    int radius;
    NearbyServiceAPI nearbyServiceAPI;

    private Spinner categorySP;
    private Spinner radiusSP;
    private String[] categoryArray={"Hospital","Restaurant","ATM","Mosque","Police Station","Fire Station"};
    private Integer[] radiusArray={200,500,1000,1200,1500,2000,2500,3000,4000,5000};

    private PlaceAdapter placeAdapter;

    @Override
    protected void onPause() {
        super.onPause();
        LocationClient.googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocationClient.googleApiClient.connect();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nearby Places");
        setSupportActionBar(toolbar);

        listView= (ListView) findViewById(R.id.place_list_view);

        placeAdapter= new PlaceAdapter(this,placesList);

        categorySP=(Spinner)findViewById(R.id.category_spinner);
        radiusSP=(Spinner)findViewById(R.id.radius_spinner);
        ArrayAdapter<String>spinnerCatAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,categoryArray);
        ArrayAdapter<Integer>spinnerRadAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,radiusArray);

        spinnerRadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        categorySP.setAdapter(spinnerCatAdapter);
        radiusSP.setAdapter(spinnerRadAdapter);

        LocationClient locationClient=new LocationClient(this);
        nearByApiInitializer();
    }

    private void nearByApiInitializer() {

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
       nearbyServiceAPI=retrofit.create(NearbyServiceAPI.class);
    }

    public void find(View view) {

        latitude= LocationClient.lat;
        longitude=LocationClient.lon;
        category=categorySP.getSelectedItem().toString().toLowerCase().replace(" ","_");
        radius=Integer.parseInt(radiusSP.getSelectedItem().toString());



        String urlString=String.format("json?location=%f,%f&radius=%d&type=%s&key=%s",latitude,longitude,radius,category,apiKey);
        Call<NearByResponse>nearbyResponseCall=nearbyServiceAPI.getAllResponses(urlString);
        nearbyResponseCall.enqueue(new Callback<NearByResponse>() {
            @Override
            public void onResponse(Call<NearByResponse> call, Response<NearByResponse> response) {
                NearByResponse nearbyResponses=response.body();
                final List<NearByResponse.Result> responses=nearbyResponses.getResults();
                placesList.clear();
                for(int i=0;i<responses.size();i++){
                    String name= responses.get(i).getName().toString();
                    String address=responses.get(i).getVicinity().toString();
//                    boolean openingHour= responses.get(i).getOpeningHours().getOpenNow();
                    Double ratingStar= responses.get(i).getRating();
                    places=new Places();
                    places.setName(name);
                    places.setAddress(address);
                   // places.setOpeningHour(openingHour);
                    places.setRating(ratingStar);
                    placesList.add(places);
                }
                listView.setAdapter(placeAdapter);
                placeAdapter.notifyDataSetChanged();
                listView.invalidateViews();
                listView.refreshDrawableState();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        double lat=responses.get(position).getGeometry().getLocation().getLat();
                        double lng=responses.get(position).getGeometry().getLocation().getLng();
                        String name= responses.get(position).getName().toString();

                        Intent intent = new Intent(NearByActivity.this,MapsActivity.class);
                        intent.putExtra("lat",lat);
                        intent.putExtra("lng",lng);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                });
            }



            @Override
            public void onFailure(Call<NearByResponse> call, Throwable t) {
                Toast.makeText(NearByActivity.this, "somthing", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
