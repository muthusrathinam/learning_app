package com.example.article_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class retrieveActivity extends AppCompatActivity {

    TextView l1,l2;
    Button locbtn;
    DatabaseReference reff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        l1 = findViewById(R.id.loc1);
        l2 = findViewById(R.id.loc2);
        locbtn = findViewById(R.id.btnRetrieve);

        locbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reff = FirebaseDatabase.getInstance().getReference().child("classes");
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String rLat = snapshot.child("latitude").getValue().toString();
                        String rLong = snapshot.child("longitude").getValue().toString();
                        l1.setText(rLat);
                        l2.setText(rLong);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("geo:" + rLat + "," +rLong));
                        Intent chooser = Intent.createChooser(intent,"Launch Maps");
                        startActivity(chooser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });


            }
        });


    }
}