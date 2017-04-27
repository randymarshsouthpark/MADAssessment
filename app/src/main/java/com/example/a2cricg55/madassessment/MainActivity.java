package com.example.a2cricg55.madassessment;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    MapView map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().load (this, PreferenceManager.getDefaultSharedPreferences(this));

        map = (MapView)findViewById(R.id.themap);

        map.setBuiltInZoomControls(true);
        map.getController().setZoom(14);
        map.getController().setCenter(new GeoPoint(-67.563836,-68.123800));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.themenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.addpoi) {
            Intent intent = new Intent(this,POI.class);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
