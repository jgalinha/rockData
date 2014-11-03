package pt.ipbeja.estig.labsi2.rockdata;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.content.Context;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import android.location.*;
import com.google.android.gms.common.*;
import android.widget.TextView;
import android.widget.Toast;



public class MapsActivity extends FragmentActivity implements LocationListener {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private LatLng user_location;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private String provider;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);
       // this.user_location =  new LatLng(location.getLatitude(), location.getLongitude());
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
        setUpMapIfNeeded();
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.lat = location.getLatitude();
        this.lng = location.getLongitude();
        //this.user_location =  new LatLng(lat, lng);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {


        Marker hamburg = mMap.addMarker(new MarkerOptions().position(HAMBURG)
                .title("Hamburg"));
        Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Lat " + this.lat)
                .snippet("Lng " + this.lng)
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));
        //Marker user = mMap.addMarker(new MarkerOptions().position(new LatLng(this.lat, this.lng)));

        // Move the camera instantly to hamburg with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KIEL, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }


}
