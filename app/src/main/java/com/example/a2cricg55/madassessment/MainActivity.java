package com.example.a2cricg55.madassessment;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MapView map;
    ItemizedIconOverlay<OverlayItem> markers;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().load (this, PreferenceManager.getDefaultSharedPreferences(this));

        map = (MapView)findViewById(R.id.themap);

        map.setBuiltInZoomControls(true);
        map.getController().setZoom(14);
        map.getController().setCenter(new GeoPoint(-67.563836,-68.123800));
        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            public boolean onItemLongPress(int i, OverlayItem marker) {
                Toast.makeText(MainActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
            public boolean onItemSingleTapUp(int i, OverlayItem marker) {
                Toast.makeText(MainActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        markers = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem Rothera = new OverlayItem("Rothera", "Research Station", new GeoPoint(-67.563836,-68.123800));
        markers.addItem(Rothera);
        MapView.getOverlays().add(markers);
    }
    protected void onActivityResult (int requestCode, int resultCode,Intent intent) {
        if(requestCode == RESULT_OK){
            if (resultCode == 1) {
                Bundle thebundle = intent.getExtras();
                    String poiName = thebundle.getString("com.example.NameText");
                    String poiType = thebundle.getString("com.example.TypeText");
                    String poiDescription = thebundle.getString("com.example.DescriptionText");
                    double lat =  MapView.getMapCenter().getLatitude();
                    double lon = MapView.getMapCenter().getLongitude();
                    markers = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), markerGestureListener);
                    OverlayItem Marker = new OverlayItem(poiName,poiType, poiDescription, new GeoPoint(lat, lon));
                    markers.addItem(Marker);
                    MapView.getOverlays().add(markers);
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.themenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addpoi) {
            Intent intent = new Intent(this,POI.class);
            startActivityForResult(intent,1);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {


    }
}
