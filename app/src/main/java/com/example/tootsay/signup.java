package com.example.tootsay;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class
signup extends AppCompatActivity {

    EditText u1, m1, p2, e2;

    FirebaseAuth myauth;
    DatabaseReference databaseRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        u1 = findViewById(R.id.u1);
        m1 = findViewById(R.id.m1);
        p2 = findViewById(R.id.p2);
        e2 = findViewById(R.id.e2);

        myauth = FirebaseAuth.getInstance();

        databaseRef = FirebaseDatabase.getInstance().getReference().child("UserInfo");

    }

    public void signup(View view) {
        String s3 = u1.getText().toString();
        String s4 = m1.getText().toString();
        String s5 = p2.getText().toString();
        String s6 = e2.getText().toString();

//        if(s3.isEmpty() || s4.isEmpty() || s5.isEmpty() || s6.isEmpty())
//        {
//            Toast.makeText(this, "Enter All The Information", Toast.LENGTH_SHORT).show();
//        }

        if(TextUtils.isEmpty(s3))
        {
            u1.setError("Enter Username");
        }

        if(TextUtils.isEmpty(s4))
        {
            m1.setError("Enter Mobile Number");
        }

        if(TextUtils.isEmpty(s5))
        {
            p2.setError("Enter Password");
        }

        if(TextUtils.isEmpty(s6))
        {
            e2.setError("Enter Email Id");
        }

        Map<String , Object> userData = new HashMap<>();
        userData.put("Username", s3);
        userData.put("Mobile Number", s4);
        userData.put("Password", s5);
        userData.put("Email Id", s6);

        myauth.createUserWithEmailAndPassword(s6 , s5).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user = myauth.getCurrentUser();
                    if(user != null)
                    {
                        String userid = user.getUid();
                        DatabaseReference dbref = databaseRef.child(userid);
                        dbref.setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), start.class));
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(signup.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                else
                {
                    Toast.makeText(signup.this, "Failed To Create User" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}