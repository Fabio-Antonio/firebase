package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class entregas extends AppCompatActivity {
    private ListView lview;
    private ArrayList<String> myarray = new ArrayList<>();
    DatabaseReference mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregas);
        lview= (ListView)findViewById(R.id.lview);

        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(entregas.this,android.R.layout.simple_list_item_1,myarray);
        lview.setAdapter(myadapter);
        mref = FirebaseDatabase.getInstance().getReference().child("agenda-list");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("nombre").getValue(String.class);
                String value2 = dataSnapshot.child("celular").getValue(String.class);
                String value3 = dataSnapshot.child("email").getValue(String.class);
                String value4 = dataSnapshot.child("direccion").getValue(String.class);
                String value5 = dataSnapshot.child("pedido").getValue(String.class);
                String value6 = dataSnapshot.child("referencia").getValue(String.class);
                String value7 = dataSnapshot.child("fecha").getValue(String.class);

                myarray.add(value+" ;"+value2+" ;"+value3+" ;"+value4+" ;"+value5+" ;"+value6);
                notification();
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                myadapter.notifyDataSetChanged();
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


    private void notification(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("n","Viversidad", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(this,"n")
                .setContentText("vivercidad")
                .setSmallIcon(R.drawable.viversidad)
                .setAutoCancel(true)
                .setContentText("Tienes un nuevo mensaje");
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999,builder.build());
    }
}
