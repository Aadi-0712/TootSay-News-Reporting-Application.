package com.example.tootsay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class enterscreen extends AppCompatActivity {

    FirebaseAuth myauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enterscreen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        myauth = FirebaseAuth.getInstance();

    }

    public void singin1(View view) {
        startActivity(new Intent(getApplicationContext(), signin.class));
        finish();
    }

    public void signup1(View view) {
        startActivity(new Intent(getApplicationContext(), signup.class));
        finish();
    }
}
