package losdos.help4hire;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import static losdos.help4hire.ActiveServicesAdapter.lat;
import static losdos.help4hire.ActiveServicesAdapter.lng;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    View myView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
//        if (bundle != null) {
//        final String loc = bundle.getString("loc");
//        final String title = bundle.getString("title");
        final double lat = bundle.getDouble("lat");
        final double lng = bundle.getDouble("lng");
        Log.d("help4hire", "LAT FROM BUNDLE: " + lat);
        Log.d("help4hire", "LNG FROM BUNDLE: " + lng);
//        }
        myView = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return myView;
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

        // Add a marker in Sydney and move the camera
//        GeoPoint loc = new GeoPoint(33, 12);
        LatLng atlanta = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(atlanta).title("Marker in Atanta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(atlanta));
    }
}
