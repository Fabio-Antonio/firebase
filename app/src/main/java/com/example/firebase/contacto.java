package com.example.firebase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class contacto extends AppCompatActivity {
    private ListView lview;
    private ArrayList <String> myarray = new ArrayList<>();
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        lview= (ListView)findViewById(R.id.lview);

        final ArrayAdapter<String> myadapter = new ArrayAdapter<String>(contacto.this,android.R.layout.simple_list_item_1,myarray);
        lview.setAdapter(myadapter);




        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(contacto.this, contacto_correo.class);
                intent.putExtra("mensaje", lview.getItemAtPosition(position).toString());

                startActivity(intent);
            }
            //@Override

        });


        mref = FirebaseDatabase.getInstance().getReference().child("users-list");

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.child("nombre").getValue(String.class);
                String value2 = dataSnapshot.child("email").getValue(String.class);
                String value3 = dataSnapshot.child("mensaje").getValue(String.class);
                myarray.add(value+" ;"+value2+" ;"+value3);
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
            NotificationChannel channel = new NotificationChannel("n","Viversidad",NotificationManager.IMPORTANCE_DEFAULT);
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
