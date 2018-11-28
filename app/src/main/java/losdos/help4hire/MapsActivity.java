package losdos.help4hire;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import static losdos.help4hire.ActiveServicesAdapter.lat;
import static losdos.help4hire.ActiveServicesAdapter.lng;
import static losdos.help4hire.ActiveServicesAdapter.providerFullName;

public class MapsActivity extends Fragment implements OnMapReadyCallback {

    View myView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();

        final double lat = bundle.getDouble("lat");
        final double lng = bundle.getDouble("lng");
        final String providerFullName = bundle.getString("providerFullName");

        myView = inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return myView;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng marker = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions().position(marker).title(providerFullName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
        CameraUpdate zoomed = CameraUpdateFactory.newLatLngZoom(marker, 15);
        mMap.animateCamera(zoomed);
    }
}
