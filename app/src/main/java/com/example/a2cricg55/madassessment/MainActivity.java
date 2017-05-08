package com.example.a2cricg55.madassessment;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Intent;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MapView map;
    ItemizedIconOverlay<OverlayItem> markers;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        map = (MapView) findViewById(R.id.themap);

        map.setBuiltInZoomControls(true);
        map.getController().setZoom(5);
        map.getController().setCenter(new GeoPoint(-67.563836, -68.123800));
        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            public boolean onItemLongPress(int i, OverlayItem marker) {
                Toast.makeText(MainActivity.this, marker.getTitle() + " " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }

            public boolean onItemSingleTapUp(int i, OverlayItem marker) {
                Toast.makeText(MainActivity.this, marker.getTitle() + " " + marker.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        markers = new ItemizedIconOverlay<>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem Rothera = new OverlayItem("Rothera", "Research Station", new GeoPoint(-67.563836, -68.123800));
        markers.addItem(Rothera);
        map.getOverlays().add(markers);
        Button SaveToFile = (Button) findViewById(R.id.thesavebtn);
        SaveToFile.setOnClickListener(this);
        Button LoadToFile = (Button) findViewById(R.id.theloadbtn);
        LoadToFile.setOnClickListener(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bundle bundle = intent.getExtras();
                String poiName = bundle.getString("com.example.NameText");
                String poiType = bundle.getString("com.example.TypeText");
                String poiDescription = bundle.getString("com.example.DescriptionText");
                double lat = map.getMapCenter().getLatitude();
                double lon = map.getMapCenter().getLongitude();
                OverlayItem Marker = new OverlayItem(poiName, poiDescription, new GeoPoint(lat, lon));
                markers.addItem(Marker);

                map.invalidate();
            }
        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.themenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addpoi) {
            Intent intent = new Intent(this, POI.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.thesavebtn) {
            try {
                PrintWriter bufferedwriting = new PrintWriter(new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/thecsv2.txt")));
                for (int i = 0; i < markers.size(); i++) {
                    OverlayItem marker = markers.getItem(i);
                    bufferedwriting.println(marker.getTitle() + "," + marker.getSnippet() + "," + marker.getPoint().getLatitude() + "," + marker.getPoint().getLongitude() + " ");

                }
                bufferedwriting.close();

            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();
            }
        } else if (v.getId() == R.id.theloadbtn) {
            try {
                Log.d("MAD", "you selected load");
                BufferedReader thereader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/thecsv2.txt"));
                String newLine;
                while ((newLine = thereader.readLine()) != null) {
                    Log.d("MAD", newLine);
                    String[] components = newLine.split(",");
                    if (components.length == 4) {
                        Log.d("MAD", "name=" + components[0] + " type=" + components[1] + " lat=" + components[2] + " lon=" + components[3]);
                        OverlayItem currentMarker = new OverlayItem(components[0], components[1], new GeoPoint(Double.parseDouble(components[2]), Double.parseDouble(components[3])));
                        markers.addItem(currentMarker);
                    }

                }
                map.invalidate();
            } catch (IOException e) {
                new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();


            }
        }
    }
    public void onDestroy(){
        super.onDestroy();
        try {
            PrintWriter bufferedwriting = new PrintWriter(new BufferedWriter(new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath() + "/thecsv2.txt")));
            for (int i = 0; i < markers.size(); i++) {
                OverlayItem marker = markers.getItem(i);
                bufferedwriting.println(marker.getTitle() + "," + marker.getSnippet() + "," + marker.getPoint().getLatitude() + "," + marker.getPoint().getLongitude() + " ");

            }
            bufferedwriting.close();

        } catch (IOException e) {
            new AlertDialog.Builder(this).setMessage("ERROR: " + e).show();
        }
        finish();
    }
}
