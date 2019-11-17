package com.e.alhambranpi;

import androidx.annotation.NonNull;
<<<<<<< HEAD
=======
import androidx.core.app.ActivityCompat;
>>>>>>> d582d933c3194de9866f8365fa9b18695e07c3fd
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> d582d933c3194de9866f8365fa9b18695e07c3fd

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    private Marker ubicacion;
    double lat = 0.0;
    double lng = 0.0;

<<<<<<< HEAD
    private static final int PERMISSION_CODE = 2000;
=======
    private static final int REQUEST_GPS = 1000;
>>>>>>> d582d933c3194de9866f8365fa9b18695e07c3fd


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, REQUEST_GPS);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        final FloatingActionButton bPuzzle = findViewById(R.id.bPuzzle);
        bPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PuzzleActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton bWar = findViewById(R.id.bWar);
        bWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), War.class);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton bStorytelling = findViewById(R.id.bStorytelling);
        bStorytelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Storytelling.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();

        LatLng alhambra = new LatLng(37.1760552, -3.5880802);
        LatLng puertaJusticia = new LatLng(37.176198, -3.590251);
        LatLng palacioCarlosV = new LatLng(37.176865, -3.589958);
        /*mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marcador_round))
                .anchor(0.0f, 1.0f).position(puertaJusticia).title("Puerta de la Justicia: Storytelling"));
        */

        mMap.addMarker(new MarkerOptions().position(palacioCarlosV).title("Palacio de Carlos V: Storytelling"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alhambra, 16));
    }

    private void agregarMarcador(double lat, double lng) {
        LatLng coordenadas = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (ubicacion != null)
            ubicacion.remove();
        /*
        ubicacion = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Posición actual " + lat + "," + lng)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        */

        ubicacion = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Posición actual " + lat + "," + lng));
        mMap.animateCamera(miUbicacion);
    }

    private void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            actualizarUbicacion(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_GPS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                miUbicacion();
            }
        }
    }

    private void miUbicacion() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED ||
                checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_DENIED) {
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, PERMISSION_CODE);
        } else {
            poner_ubicacion();
        }
    }

    private void poner_ubicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
<<<<<<< HEAD
=======

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
>>>>>>> d582d933c3194de9866f8365fa9b18695e07c3fd
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    poner_ubicacion();
                }
                else{
                    Toast.makeText(this, "Permiso denegado...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    protected void puzzle(View view) {
        // Start puzzle in response to button
        Intent i = new Intent(MapsActivity.this, PuzzleActivity.class);
        startActivity(i);
    }

    protected void war(View view) {
        // Start puzzle in response to button
        Intent i = new Intent(MapsActivity.this, War.class);
        startActivity(i);
    }

    protected void storytelling(View view) {
        // Start puzzle in response to button
        Intent i = new Intent(MapsActivity.this, Storytelling.class);
        startActivity(i);
    }

}
