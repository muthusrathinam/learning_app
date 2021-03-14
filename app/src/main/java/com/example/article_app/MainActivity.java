package com.example.article_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button receive;
    EditText phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receive = findViewById(R.id.receiveOtp);
        phoneNo = findViewById(R.id.phoneno);





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
}