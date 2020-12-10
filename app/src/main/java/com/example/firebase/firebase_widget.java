package com.example.firebase;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class firebase_widget {
    DatabaseReference mref;
    String []myarrays ={"","","","",""} ;
    private ArrayList<String> myarray = new ArrayList<>();
    firebase_widget(){
        mref = FirebaseDatabase.getInstance().getReference().child("users-list");
       mref.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String value = dataSnapshot.child("nombre").getValue(String.class);
               String value2 = dataSnapshot.child("email").getValue(String.class);
               String value3 = dataSnapshot.child("mensaje").getValue(String.class);
               myarray.add(value+" ;"+value2+" ;"+value3);
              myarray.toArray(myarrays);

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
