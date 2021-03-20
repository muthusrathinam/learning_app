package com.example.article_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button receive;
    EditText phoneNo;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receive = findViewById(R.id.receiveOtp);
        phoneNo = findViewById(R.id.phoneno);
        auth = FirebaseAuth.getInstance();




        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneNo.getText().toString();

                Intent intent = new Intent(getApplicationContext(),verifyActivity.class);
                intent.putExtra("phoneNo",phone);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            sendtoMain();
        }
    }
    private void sendtoMain(){
        Intent mainIntent = new Intent(MainActivity.this, HomePage.class);
        startActivity(mainIntent);
        finish();
    }
}