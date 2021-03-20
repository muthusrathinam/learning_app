package com.example.article_app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity {

    Button signOut,btn;
    TextView textLat,textLong;
    FirebaseAuth mauth;
    FirebaseDatabase rootnode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        signOut = findViewById(R.id.signOut);
        mauth = FirebaseAuth.getInstance();
        textLat =findViewById(R.id.textLat);
        textLong =findViewById(R.id.textLong);
        btn = findViewById(R.id.btn);
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("classes");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, retrieveActivity.class);
                startActivity(intent);
            }
        });

        //get all the values
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null)
                                {
                                    double lat = location.getLatitude();
                                    double longi = location.getLongitude();

                                    textLat.setText(String.valueOf(lat));
                                    textLong.setText(String.valueOf(longi));

                                    Log.d("location","success");
                                    LocationHelperClass helperClass = new LocationHelperClass(lat,longi);
                                    reference.setValue(helperClass);

                                }
                            }
                        });
            }else{
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                Toast.makeText(this,"failed",Toast.LENGTH_LONG).show();
            }
        }








        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = mauth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(HomePage.this, MainActivity.class));
            finish();
        }
    }
}