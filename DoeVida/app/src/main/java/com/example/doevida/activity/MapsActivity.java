package com.example.doevida.activity;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.example.doevida.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String local = ( String )getIntent().getExtras().get( "local" );
        location = local;

        searchView = findViewById(R.id.sv_local);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchView.setQuery(local, true);
                String location = searchView.getQuery().toString();
                List<Address> enderecos = null;

                if(local != null || local.equals("")){
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        enderecos = geocoder.getFromLocationName(local, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(!enderecos.isEmpty()){
                        Address end = enderecos.get(0);
                        com.google.android.gms.maps.model.LatLng latLng = new LatLng(end.getLatitude(), end.getLongitude());
                        if(latLng != null){
                            map.addMarker(new MarkerOptions().position(latLng).title(location));
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));

                        }
                    }
                    else{
                        Toast.makeText(MapsActivity.this, "Sem bons resultados", Toast.LENGTH_LONG).show();
                    }


                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> enderecos = null;


        try{
            enderecos = geocoder.getFromLocationName(location, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address end = enderecos.get(0);
        com.google.android.gms.maps.model.LatLng latLng = new LatLng(end.getLatitude(), end.getLongitude());

//        // Add a marker no centro e mover a câmera
//        LatLng centro = new LatLng(-34, 151);
         map.addMarker(new MarkerOptions()
            .position(latLng)
                .title("Centro do Hemoce"));
         map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }
}
//    private void initializeMap() {
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.google_map);
//        if (map == null) {
//        }
//    }

