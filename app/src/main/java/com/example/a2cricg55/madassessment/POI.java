package com.example.a2cricg55.madassessment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class POI extends Activity implements View.OnClickListener {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pointofinterest);
        Button Add = (Button)findViewById(R.id.AddButton);
        Add.setOnClickListener(this);
    }

    public void onClick(View v){
        EditText Name = (EditText)findViewById(R.id.NameText);
        String name = Name.getText().toString();
        EditText Type = (EditText)findViewById(R.id.TypeText);
        String type = Type.getText().toString();
        EditText Description = (EditText)findViewById(R.id.DescriptionText);
        String description = Description.getText().toString();

        Bundle thebundle = new Bundle();
        thebundle.putString("com.example.NameText", name);
        thebundle.putString("com.example.TypeText", type);
        thebundle.putString("com.example.DescriptionText", description);

        Intent intent = new Intent();
        intent.putExtras(thebundle);
        setResult(RESULT_OK, intent);
        finish();


    }
}
