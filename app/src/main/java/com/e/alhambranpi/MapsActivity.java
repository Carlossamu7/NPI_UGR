package com.e.alhambranpi;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Marker ubicacion;
    double lat = 0.0;
    double lng = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final FloatingActionButton bPuzzle = findViewById(R.id.bPuzzle);
        bPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PuzzleActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton bWar = findViewById(R.id.bWar);
        bWar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), War.class);
                startActivityForResult(intent, 0);
            }
        });

        final FloatingActionButton bStorytelling = findViewById(R.id.bStorytelling);
        bStorytelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), Storytelling.class);
                startActivityForResult(intent, 0);
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        miUbicacion();

        // Add a marker in Sydney and move the camera

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
        else{
            System.out.println("NO ACTUALIZAR UBICACION");
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

    private void miUbicacion() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        } else {
            System.out.println("NO PERMISOS");
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
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
