package com.example.tootsay.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tootsay.databinding.FragmentNotificationsBinding;
import com.example.tootsay.enterscreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotificationsFragment extends Fragment {
    Button b1;

    FirebaseAuth myauth;

    TextView t1, t2, t3, t4;


    DatabaseReference databaseReference;

    FirebaseDatabase firebaseDatabase;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myauth =FirebaseAuth.getInstance();
        FirebaseUser user = myauth.getCurrentUser();
        String userid = user.getUid();

        displayInfo(userid);

        b1 = binding.logout;
        t1 = binding.textView7;
        t2 = binding.textView9;
        t3 = binding.textView11;
        t4 = binding.textView13;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Sign Out");
                builder.setMessage("Do you really want to sign out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myauth.signOut();
                        startActivity(new Intent(getActivity().getApplicationContext(), enterscreen.class));
                        getActivity().finish();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });


        return root;

    }

    public void displayInfo(String userid) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(userid);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String email = snapshot.child("Email Id").getValue(String.class);
                    String username = snapshot.child("Username").getValue(String.class);
                    String mobile = snapshot.child("Mobile Number").getValue(String.class);
                    String password = snapshot.child("Password").getValue(String.class);
                    t4.setText(email);
                    t3.setText(mobile);
                    t2.setText(password);
                    t1.setText(username);
                }
                else {
                    Toast.makeText(getActivity(), "No data Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void signout() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}